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
package org.jclouds.cloudsigma2.compute.functions;

import com.google.common.base.Function;
import org.jclouds.cloudsigma2.CloudSigma2Api;
import org.jclouds.cloudsigma2.domain.FirewallPolicy;
import org.jclouds.cloudsigma2.domain.Tag;
import org.jclouds.compute.domain.SecurityGroup;
import org.jclouds.compute.domain.SecurityGroupBuilder;
import org.jclouds.compute.functions.GroupNamingConvention;
import org.jclouds.net.domain.IpPermission;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Iterables.transform;

@Singleton
public class FirewallPolicyToSecurityGroup implements Function<FirewallPolicy, SecurityGroup> {

   private final CloudSigma2Api api;
   private final GroupNamingConvention groupNamingConventionWithPrefix;
   private final FirewallRuleToIpPermission firewallRuleToIpPermission;

   @Inject
   public FirewallPolicyToSecurityGroup(CloudSigma2Api api,
                                        GroupNamingConvention.Factory groupNamingConventionWithPrefix,
                                        FirewallRuleToIpPermission firewallRuleToIpPermission) {
      this.api = checkNotNull(api, "api");
      this.groupNamingConventionWithPrefix = checkNotNull(groupNamingConventionWithPrefix,
            "groupNamingConventionWithPrefix").create();
      this.firewallRuleToIpPermission = checkNotNull(firewallRuleToIpPermission, "firewallRuleToIpPermission");

   }

   @Override
   public SecurityGroup apply(FirewallPolicy input) {
      return new SecurityGroupBuilder()
            .ids(input.getUuid())
            .name(input.getName())
            .uri(input.getResourceUri())
            .userMetadata(input.getMeta())
            .ipPermissions(readIpPermissions(input))
            .tags(readTags(input))
            .build();
   }

   private Iterable<IpPermission> readIpPermissions(FirewallPolicy firewallPolicy) {
      return transform(firewallPolicy.getRules(), firewallRuleToIpPermission);
   }

   private Iterable<String> readTags(FirewallPolicy firewallPolicy) {
      return transform(firewallPolicy.getTags(), new Function<Tag, String>() {
         @Override
         public String apply(Tag input) {
            Tag tag = api.getTagInfo(input.getUuid());
            if (tag.getName() == null) {
               return input.getUuid();
            }
            String tagWithoutPrefix = groupNamingConventionWithPrefix.groupInSharedNameOrNull(tag.getName());
            return tagWithoutPrefix != null ? tagWithoutPrefix : tag.getName();
         }
      });
   }
}
