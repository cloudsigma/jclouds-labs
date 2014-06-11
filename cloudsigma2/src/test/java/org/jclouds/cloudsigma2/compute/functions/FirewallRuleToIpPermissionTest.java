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

import com.google.common.collect.ImmutableList;
import org.jclouds.cloudsigma2.domain.FirewallAction;
import org.jclouds.cloudsigma2.domain.FirewallDirection;
import org.jclouds.cloudsigma2.domain.FirewallIpProtocol;
import org.jclouds.cloudsigma2.domain.FirewallRule;
import org.jclouds.net.domain.IpPermission;
import org.jclouds.net.domain.IpProtocol;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.jclouds.cloudsigma2.compute.config.CloudSigma2ComputeServiceContextModule.firewallIpProtocolToIpProtocol;
import static org.testng.Assert.assertEquals;

@Test(groups = "unit", testName = "FirewallPolicyToSecurityGroupTest")
public class FirewallRuleToIpPermissionTest {

   private List<FirewallRule> input;
   private List<IpPermission> expected;

   @BeforeMethod
   public void setUp() throws Exception {
      input = ImmutableList.of(
            new FirewallRule.Builder()
                  .action(FirewallAction.DROP)
                  .comment("Drop traffic from the VM to IP address 23.0.0.0/32")
                  .direction(FirewallDirection.OUT)
                  .destinationIp("23.0.0.0/32")
                  .build(),
            new FirewallRule.Builder()
                  .action(FirewallAction.ACCEPT)
                  .comment("Allow SSH traffic to the VM from our office in Dubai")
                  .direction(FirewallDirection.IN)
                  .destinationPort("22")
                  .ipProtocol(FirewallIpProtocol.TCP)
                  .sourceIp("172.66.32.0/24")
                  .build(),
            new FirewallRule.Builder()
                  .action(FirewallAction.DROP)
                  .comment("Allow traffic from 10-233 ports to the VM")
                  .direction(FirewallDirection.IN)
                  .destinationPort("10:233")
                  .ipProtocol(FirewallIpProtocol.TCP)
                  .build(),
            new FirewallRule.Builder()
                  .action(FirewallAction.DROP)
                  .comment("Drop all UDP traffic to the VM, not originating from 172.66.32.55")
                  .direction(FirewallDirection.IN)
                  .ipProtocol(FirewallIpProtocol.UDP)
                  .sourceIp("!172.66.32.55/32")
                  .build(),
            new FirewallRule.Builder()
                  .action(FirewallAction.DROP)
                  .comment("Drop any traffic, to the VM with destination port not between 1-1024")
                  .direction(FirewallDirection.IN)
                  .destinationPort("!1:1024")
                  .ipProtocol(FirewallIpProtocol.TCP)
                  .build());
      expected = ImmutableList.of(
            new IpPermission.Builder()
                  .ipProtocol(IpProtocol.UNRECOGNIZED)
                  .cidrBlock("0.0.0.0/0")
                  .build(),
            new IpPermission.Builder()
                  .ipProtocol(IpProtocol.TCP)
                  .cidrBlock("172.66.32.0/24")
                  .fromPort(22)
                  .toPort(22)
                  .build(),
            new IpPermission.Builder()
                  .ipProtocol(IpProtocol.TCP)
                  .cidrBlock("0.0.0.0/0")
                  .fromPort(10)
                  .toPort(233)
                  .build(),
            new IpPermission.Builder()
                  .ipProtocol(IpProtocol.UDP)
                  .cidrBlock("0.0.0.0/0")
                  .build(),
            new IpPermission.Builder()
                  .ipProtocol(IpProtocol.TCP)
                  .cidrBlock("0.0.0.0/0")
                  .fromPort(1)
                  .toPort(1024)
                  .build());
   }

   public void testConvertFirewallRules() {
      FirewallRuleToIpPermission function = new FirewallRuleToIpPermission(firewallIpProtocolToIpProtocol);
      for (int i = 0; i < input.size() - 1; i++) {
         assertEquals(function.apply(input.get(i)), expected.get(i));
      }
   }
}
