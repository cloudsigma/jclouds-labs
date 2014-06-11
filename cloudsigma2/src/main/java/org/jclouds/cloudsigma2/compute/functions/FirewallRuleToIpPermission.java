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
import org.jclouds.cloudsigma2.domain.FirewallIpProtocol;
import org.jclouds.cloudsigma2.domain.FirewallRule;
import org.jclouds.net.domain.IpPermission;
import org.jclouds.net.domain.IpProtocol;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

@Singleton
public class FirewallRuleToIpPermission implements Function<FirewallRule, IpPermission> {

   private final Map<FirewallIpProtocol, IpProtocol> firewallIpProtocolToIpProtocol;

   @Inject
   public FirewallRuleToIpPermission(Map<FirewallIpProtocol, IpProtocol> firewallIpProtocolToIpProtocol) {
      this.firewallIpProtocolToIpProtocol = checkNotNull(firewallIpProtocolToIpProtocol,
            "firewallIpProtocolToIpProtocol");
   }

   @Override
   public IpPermission apply(FirewallRule input) {
      IpPermission.Builder permissionBuilder = new IpPermission.Builder();
      String destinationPort = input.getDestinationPort();
      if (destinationPort != null) {
         if (destinationPort.contains("!")) {
            destinationPort = destinationPort.substring(destinationPort.indexOf("!") + 1,
                  destinationPort.length());
         }
         if (destinationPort.contains(":")) {
            int[] ports = parsePort(destinationPort);
            permissionBuilder.fromPort(ports[0]);
            permissionBuilder.toPort(ports[1]);
         } else {
            permissionBuilder.fromPort(Integer.parseInt(destinationPort));
            permissionBuilder.toPort(Integer.parseInt(destinationPort));
         }
      }
      permissionBuilder.ipProtocol(input.getIpProtocol() != null ? firewallIpProtocolToIpProtocol.get(input
            .getIpProtocol()) : IpProtocol.UNRECOGNIZED);
      if (input.getSourceIp() != null) {
         permissionBuilder.cidrBlock(input.getSourceIp().contains("!") ? "0.0.0.0/0" : input.getSourceIp());
      } else {
         permissionBuilder.cidrBlock("0.0.0.0/0");
      }
      return permissionBuilder.build();
   }

   private int[] parsePort(String portRange) {
      int[] ports = new int[2];
      String[] portStringsArray = portRange.split(":");
      ports[0] = Integer.parseInt(portStringsArray[0]);
      ports[1] = Integer.parseInt(portStringsArray[1]);
      return ports;
   }
}
