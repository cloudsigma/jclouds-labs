/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jclouds.cloudsigma2.compute.extensions;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import org.jclouds.cloudsigma2.CloudSigma2Api;
import org.jclouds.cloudsigma2.domain.FirewallAction;
import org.jclouds.cloudsigma2.domain.FirewallDirection;
import org.jclouds.cloudsigma2.domain.FirewallIpProtocol;
import org.jclouds.cloudsigma2.domain.FirewallPolicy;
import org.jclouds.cloudsigma2.domain.FirewallRule;
import org.jclouds.cloudsigma2.domain.NIC;
import org.jclouds.cloudsigma2.domain.Server;
import org.jclouds.cloudsigma2.domain.ServerInfo;
import org.jclouds.cloudsigma2.domain.ServerStatus;
import org.jclouds.compute.domain.SecurityGroup;
import org.jclouds.compute.extensions.SecurityGroupExtension;
import org.jclouds.domain.Location;
import org.jclouds.net.domain.IpPermission;
import org.jclouds.net.domain.IpProtocol;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Iterables.any;
import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.isEmpty;
import static com.google.common.collect.Iterables.transform;

public class CloudSigma2SecurityGroupExtension implements SecurityGroupExtension {

   private final CloudSigma2Api api;
   private final Function<FirewallPolicy, SecurityGroup> firewallPolicyToSecurityGroup;
   private final Map<IpProtocol, FirewallIpProtocol> ipProtocolToFirewallIpProtocol;

   @Inject
   public CloudSigma2SecurityGroupExtension(CloudSigma2Api api,
                                            Function<FirewallPolicy, SecurityGroup> firewallPolicyToSecurityGroup,
                                            Map<IpProtocol, FirewallIpProtocol> ipProtocolToFirewallIpProtocol) {
      this.api = checkNotNull(api, "api");
      this.firewallPolicyToSecurityGroup = checkNotNull(firewallPolicyToSecurityGroup, "firewallPolicyToSecurityGroup");
      this.ipProtocolToFirewallIpProtocol = checkNotNull(ipProtocolToFirewallIpProtocol,
            "ipProtocolToFirewallIpProtocol");
   }

   @Override
   public Set<SecurityGroup> listSecurityGroups() {
      return ImmutableSet.copyOf(transform(api.listFirewallPoliciesInfo().concat(), firewallPolicyToSecurityGroup));
   }

   @Override
   public Set<SecurityGroup> listSecurityGroupsInLocation(Location location) {
      return listSecurityGroups();
   }

   @Override
   public Set<SecurityGroup> listSecurityGroupsForNode(String id) {
      ServerInfo serverInfo = api.getServerInfo(id);

      Iterable<NIC> nicsWithPolicies = filter(serverInfo.getNics(), new Predicate<NIC>() {
         @Override
         public boolean apply(NIC input) {
            return input.getFirewallPolicy() != null;
         }
      });

      return ImmutableSet.copyOf(transform(nicsWithPolicies, new Function<NIC, SecurityGroup>() {
         @Override
         public SecurityGroup apply(NIC input) {
            return firewallPolicyToSecurityGroup.apply(api.getFirewallPolicy(input.getFirewallPolicy().getUuid()));
         }
      }));
   }

   @Override
   public SecurityGroup getSecurityGroupById(String id) {
      return firewallPolicyToSecurityGroup.apply(api.getFirewallPolicy(id));
   }

   @Override
   public SecurityGroup createSecurityGroup(String name, Location location) {
      FirewallPolicy firewallPolicy = api.createFirewallPolicy(new FirewallPolicy.Builder().name(name).build());
      return firewallPolicyToSecurityGroup.apply(firewallPolicy);
   }

   @Override
   public boolean removeSecurityGroup(String id) {
      FirewallPolicy firewallPolicy = api.getFirewallPolicy(id);
      if (firewallPolicy == null) {
         throw new IllegalArgumentException("There is no SecurityGroup with " + id + " id");
      }

      if (any(firewallPolicy.getServers(), new Predicate<Server>() {
         @Override
         public boolean apply(Server server) {
            ServerInfo serverInfo = api.getServerInfo(server.getUuid());
            return !ServerStatus.STOPPED.equals(serverInfo.getStatus());
         }
      })) {
         throw new IllegalStateException("Can't delete SecurityGroup while there are nodes running in them");
      }

      api.deleteFirewallPolicy(id);
      return !api.listFirewallPolicies().concat().contains(firewallPolicy);
   }

   @Override
   public SecurityGroup addIpPermission(IpPermission ipPermission, SecurityGroup group) {
      return addIpPermission(ipPermission.getIpProtocol(), ipPermission.getFromPort(), ipPermission.getToPort(),
            ImmutableMultimap.<String, String>of(), ImmutableSet.<String>of(), ImmutableSet.<String>of(), group);
   }

   @Override
   public SecurityGroup removeIpPermission(IpPermission ipPermission, SecurityGroup group) {
      return removeIpPermission(ipPermission.getIpProtocol(), ipPermission.getFromPort(), ipPermission.getToPort(),
            ImmutableMultimap.<String, String>of(), ImmutableSet.<String>of(), ImmutableSet.<String>of(), group);
   }

   @Override
   public SecurityGroup addIpPermission(IpProtocol protocol, int startPort, int endPort,
                                        Multimap<String, String> tenantIdGroupNamePairs, Iterable<String> ipRanges,
                                        Iterable<String> groupIds, SecurityGroup group) {
      FirewallPolicy firewallPolicy = api.getFirewallPolicy(group.getId());

      List<FirewallRule> firewallRules = firewallPolicy.getRules() != null ? Lists.newArrayList(firewallPolicy
            .getRules()) : Lists.<FirewallRule>newArrayList();

      String firewallPortRange;
      if (startPort > endPort) {
         throw new IllegalArgumentException("Invalid port range '" + startPort + ":" + endPort +"'. startPort should " +
               "be smaller than endPort");
      } else if (startPort == endPort) {
         firewallPortRange = "" + startPort;
      } else {
         firewallPortRange = startPort + ":" + endPort;
      }

      if (!isEmpty(ipRanges)) {
         for (String ip : ipRanges) {
            firewallRules.add(new FirewallRule.Builder()
                  .direction(FirewallDirection.IN)
                  .action(FirewallAction.ACCEPT)
                  .ipProtocol(ipProtocolToFirewallIpProtocol.get(protocol))
                  .sourceIp(ip)
                  .destinationPort(firewallPortRange)
                  .build());
         }
      } else {
         FirewallRule.Builder firewallRuleBuilder = new FirewallRule.Builder()
               .direction(FirewallDirection.IN)
               .action(FirewallAction.ACCEPT)
               .destinationPort(firewallPortRange);
         if (protocol != null) {
            firewallRuleBuilder.ipProtocol(ipProtocolToFirewallIpProtocol.get(protocol));
         }
         firewallRules.add(firewallRuleBuilder.build());
      }

      firewallPolicy = api.editFirewallPolicy(firewallPolicy.getUuid(),
            FirewallPolicy.Builder.fromFirewallPolicy(firewallPolicy).rules(firewallRules).build());

      return firewallPolicyToSecurityGroup.apply(firewallPolicy);
   }

   @Override
   public SecurityGroup removeIpPermission(final IpProtocol protocol, final int startPort, final int endPort,
                                           Multimap<String, String> tenantIdGroupNamePairs, Iterable<String> ipRanges,
                                           Iterable<String> groupIds, SecurityGroup group) {
      final Set<String> ipSet = ipRanges != null ? ImmutableSet.copyOf(ipRanges) : Sets.<String>newHashSet();

      FirewallPolicy firewallPolicy = api.getFirewallPolicy(group.getId());

      firewallPolicy = api.editFirewallPolicy(
            firewallPolicy.getUuid(),
            FirewallPolicy.Builder.fromFirewallPolicy(firewallPolicy)
                  .rules(ImmutableList.copyOf(filter(firewallPolicy.getRules(), new Predicate<FirewallRule>() {
                     @Override
                     public boolean apply(FirewallRule input) {
                        return !input.getIpProtocol().equals(ipProtocolToFirewallIpProtocol.get(protocol))
                              && !input.getDestinationPort().equals(startPort + ":" + endPort)
                              && (!ipSet.contains(input.getSourceIp()));
                     }
                  }))).build());

      return firewallPolicyToSecurityGroup.apply(firewallPolicy);
   }

   @Override
   public boolean supportsTenantIdGroupNamePairs() {
      return false;
   }

   @Override
   public boolean supportsTenantIdGroupIdPairs() {
      return false;
   }

   @Override
   public boolean supportsGroupIds() {
      return false;
   }

   @Override
   public boolean supportsPortRangesForGroups() {
      return false;
   }
}
