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
package org.jclouds.cloudsigma2.functions;

import com.google.common.collect.ImmutableList;
import com.google.inject.Guice;
import org.jclouds.cloudsigma2.beans.RawFirewallPolicy;
import org.jclouds.cloudsigma2.beans.RawOwner;
import org.jclouds.cloudsigma2.beans.RawServer;
import org.jclouds.cloudsigma2.domain.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Vladimir Shevchenko
 */
@Test(groups = "unit")
public class JsonToFirewallPolicyTest {

    private static final JsonToFirewallPolicy JSON_TO_FIREWALL_POLICY = Guice.createInjector().getInstance(JsonToFirewallPolicy.class);

    private RawFirewallPolicy input;
    private FirewallPolicy expected;

    @BeforeMethod
    public void setUp() throws Exception {
        Owner owner = new Owner.Builder()
                .resourceUri(new URI("/api/2.0/user/5b4a69a3-8e78-4c45-a8ba-8b13f0895e23/"))
                .uuid("5b4a69a3-8e78-4c45-a8ba-8b13f0895e23")
                .build();

        Map<String, String> meta = new HashMap<String, String>();
        meta.put("description", "test firewall policy");

        expected = new FirewallPolicy.Builder()
                .meta(meta)
                .name("My awesome policy")
                .owner(owner)
                .resourceUri(new URI("/api/2.0/fwpolicies/cf8479b4-c98b-46c8-ab9c-108bb00c8218/"))
                .rules(ImmutableList.of(
                        new FirewallRule.Builder()
                                .action(FirewallAction.DROP)
                                .comment("Drop traffic from the VM to IP address 23.0.0.0/32")
                                .direction(FirewallDirection.OUT)
                                .destinationIp("23.0.0.0/32")
                                .build()
                        , new FirewallRule.Builder()
                        .action(FirewallAction.ACCEPT)
                        .comment("Allow SSH traffic to the VM from our office in Dubai")
                        .direction(FirewallDirection.IN)
                        .destinationPort("22")
                        .ipProtocol(FirewallIpProtocol.TCP)
                        .sourceIp("172.66.32.0/24")
                        .build()
                        , new FirewallRule.Builder()
                        .action(FirewallAction.DROP)
                        .comment("Drop all other SSH traffic to the VM")
                        .direction(FirewallDirection.IN)
                        .destinationPort("22")
                        .ipProtocol(FirewallIpProtocol.TCP)
                        .build()
                        , new FirewallRule.Builder()
                        .action(FirewallAction.DROP)
                        .comment("Drop all UDP traffic to the VM, not originating from 172.66.32.55")
                        .direction(FirewallDirection.IN)
                        .ipProtocol(FirewallIpProtocol.UDP)
                        .sourceIp("!172.66.32.55/32")
                        .build()
                        , new FirewallRule.Builder()
                        .action(FirewallAction.DROP)
                        .comment("Drop any traffic, to the VM with destination port not between 1-1024")
                        .direction(FirewallDirection.IN)
                        .destinationPort("!1:1024")
                        .ipProtocol(FirewallIpProtocol.TCP)
                        .build()
                ))
                .servers(ImmutableList.of(
                        new Server.Builder()
                                .uuid("81f911f9-5152-4328-8671-02543bafbd0e")
                                .resourceUri(new URI("/api/2.0/servers/81f911f9-5152-4328-8671-02543bafbd0e/"))
                                .build()
                ))
                .uuid("cf8479b4-c98b-46c8-ab9c-108bb00c8218")
                .build();

        input = new RawFirewallPolicy();

        RawOwner rawOwner = new RawOwner();
        rawOwner.resource_uri = "/api/2.0/user/5b4a69a3-8e78-4c45-a8ba-8b13f0895e23/";
        rawOwner.uuid = "5b4a69a3-8e78-4c45-a8ba-8b13f0895e23";

        RawFirewallPolicy.RawFirewallRule firewallRule1 = input.new RawFirewallRule();
        firewallRule1.action = "drop";
        firewallRule1.comment = "Drop traffic from the VM to IP address 23.0.0.0/32";
        firewallRule1.direction = "out";
        firewallRule1.dst_ip = "23.0.0.0/32";
        RawFirewallPolicy.RawFirewallRule firewallRule2 = input.new RawFirewallRule();
        firewallRule2.action = "accept";
        firewallRule2.comment = "Allow SSH traffic to the VM from our office in Dubai";
        firewallRule2.direction = "in";
        firewallRule2.dst_port = "22";
        firewallRule2.ip_proto = "tcp";
        firewallRule2.src_ip = "172.66.32.0/24";
        RawFirewallPolicy.RawFirewallRule firewallRule3 = input.new RawFirewallRule();
        firewallRule3.action = "drop";
        firewallRule3.comment = "Drop all other SSH traffic to the VM";
        firewallRule3.direction = "in";
        firewallRule3.dst_port = "22";
        firewallRule3.ip_proto = "tcp";
        RawFirewallPolicy.RawFirewallRule firewallRule4 = input.new RawFirewallRule();
        firewallRule4.action = "drop";
        firewallRule4.comment = "Drop all UDP traffic to the VM, not originating from 172.66.32.55";
        firewallRule4.direction = "in";
        firewallRule4.ip_proto = "udp";
        firewallRule4.src_ip = "!172.66.32.55/32";
        RawFirewallPolicy.RawFirewallRule firewallRule5 = input.new RawFirewallRule();
        firewallRule5.action = "drop";
        firewallRule5.comment = "Drop any traffic, to the VM with destination port not between 1-1024";
        firewallRule5.direction = "in";
        firewallRule5.dst_port = "!1:1024";
        firewallRule5.ip_proto = "tcp";

        RawServer rawServer = new RawServer();
        rawServer.resource_uri = "/api/2.0/servers/81f911f9-5152-4328-8671-02543bafbd0e/";
        rawServer.uuid = "81f911f9-5152-4328-8671-02543bafbd0e";

        input.meta = meta;
        input.name = "My awesome policy";
        input.owner = rawOwner;
        input.resource_uri = "/api/2.0/fwpolicies/cf8479b4-c98b-46c8-ab9c-108bb00c8218/";
        input.rules = ImmutableList.of(firewallRule1, firewallRule2, firewallRule3, firewallRule4, firewallRule5);
        input.servers = ImmutableList.of(rawServer);
        input.uuid = "cf8479b4-c98b-46c8-ab9c-108bb00c8218";
    }

    public void test(){
        Assert.assertEquals(JSON_TO_FIREWALL_POLICY.apply(input), expected);
    }

}
