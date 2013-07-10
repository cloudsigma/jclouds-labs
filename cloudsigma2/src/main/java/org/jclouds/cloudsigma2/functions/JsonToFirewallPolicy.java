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

import com.google.common.base.Function;
import org.jclouds.cloudsigma2.beans.RawFirewallPolicy;
import org.jclouds.cloudsigma2.beans.RawServer;
import org.jclouds.cloudsigma2.domain.*;
import org.jclouds.javax.annotation.Nullable;
import org.jclouds.net.domain.IpProtocol;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Shevchenko
 */
@Singleton
public class JsonToFirewallPolicy implements Function<RawFirewallPolicy, FirewallPolicy> {

    private final JsonToOwner jsonToOwner;
    private final JsonToServer jsonToServer;

    @Inject
    public JsonToFirewallPolicy(JsonToOwner jsonToOwner, JsonToServer jsonToServer) {
        this.jsonToOwner = jsonToOwner;
        this.jsonToServer = jsonToServer;
    }

    @Override
    public FirewallPolicy apply(@Nullable RawFirewallPolicy input) {
        FirewallPolicy.Builder firewallPolicyBuilder = new FirewallPolicy.Builder();

        if(input.meta != null){
            firewallPolicyBuilder.meta(input.meta);
        }

        if(input.name != null){
            firewallPolicyBuilder.name(input.name);
        }

        if(input.owner != null){
            firewallPolicyBuilder.owner(jsonToOwner.apply(input.owner));
        }

        if(input.resource_uri != null){
            try {
                firewallPolicyBuilder.resourceUri(new URI(input.resource_uri));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        if(input.servers != null){
            List<Server> serverList = new ArrayList<Server>();
            for(RawServer rawServer : input.servers){
                serverList.add(jsonToServer.apply(rawServer));
            }
            firewallPolicyBuilder.servers(serverList);
        }

        if(input.uuid != null){
            firewallPolicyBuilder.uuid(input.uuid);
        }

        if(input.rules != null){
            List<FirewallRule> firewallRules = new ArrayList<FirewallRule>();
            
            for(RawFirewallPolicy.RawFirewallRule rawFirewallRule : input.rules){
                FirewallRule.Builder firewallRuleBuilder = new FirewallRule.Builder();
                if(rawFirewallRule.action != null){
                    firewallRuleBuilder.action(FirewallAction.fromValue(rawFirewallRule.action));
                }

                if(rawFirewallRule.action != null){
                    firewallRuleBuilder.action(FirewallAction.fromValue(rawFirewallRule.action));
                }

                if(rawFirewallRule.comment != null){
                    firewallRuleBuilder.comment(rawFirewallRule.comment);
                }

                if(rawFirewallRule.direction != null){
                    firewallRuleBuilder.direction(FirewallDirection.fromValue(rawFirewallRule.direction));
                }

                if(rawFirewallRule.dst_ip != null){
                    firewallRuleBuilder.destinationIp(rawFirewallRule.dst_ip);
                }

                if(rawFirewallRule.dst_port != null){
                    firewallRuleBuilder.destinationPort(rawFirewallRule.dst_port);
                }

                if(rawFirewallRule.ip_proto != null){
                    firewallRuleBuilder.ipProtocol(FirewallIpProtocol.fromValue(rawFirewallRule.ip_proto));
                }

                if(rawFirewallRule.src_ip != null){
                    firewallRuleBuilder.sourceIp(rawFirewallRule.src_ip);
                }

                if(rawFirewallRule.src_port != null){
                    firewallRuleBuilder.sourcePort(rawFirewallRule.src_port);
                }

                firewallRules.add(firewallRuleBuilder.build());
            }
            
            firewallPolicyBuilder.rules(firewallRules);
        }

        return firewallPolicyBuilder.build();
    }
}
