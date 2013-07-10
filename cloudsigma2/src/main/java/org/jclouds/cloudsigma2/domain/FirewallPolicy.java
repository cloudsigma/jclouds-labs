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
package org.jclouds.cloudsigma2.domain;

import com.google.common.collect.ImmutableList;

import java.net.URI;
import java.util.List;

/**
 * @author Vladimir Shevchenko
 */
public class FirewallPolicy extends Item{

    public static class Builder extends Item.Builder{
        protected Object meta;
        protected Owner owner;
        protected List<FirewallRule> rules;
        protected List<Server> servers;

        /**
         * @param meta User assigned meta information for this policy
         * @return
         */
        public Builder meta(Object meta){
            this.meta = meta;
            return this;
        }

        /**
         * @param owner Owner of the policy
         * @return
         */
        public Builder owner(Owner owner){
            this.owner = owner;
            return this;
        }

        /**
         * @param rules List of rules to be applied for this policy
         * @return
         */
        public Builder rules(List<FirewallRule> rules){
            this.rules = ImmutableList.copyOf(rules);
            return this;
        }

        /**
         * @param servers Servers which have nics with this policy applied
         * @return
         */
        public Builder servers(List<Server> servers){
            this.servers = ImmutableList.copyOf(servers);
            return this;
        }

        /**
         * @param resourceUri Resource URI
         * @return
         */
        @Override
        public Builder resourceUri(URI resourceUri){
            this.resourceUri = resourceUri;
            return this;
        }

        /**
         * @param name Human readable name of the firewall policy
         * @return
         */
        @Override
        public Builder name(String name){
            this.name = name;
            return this;
        }

        /**
         * @param uuid UUID of the policy
         * @return
         */
        @Override
        public Builder uuid(String uuid){
            this.uuid = uuid;
            return this;
        }

        public FirewallPolicy build(){
            return new FirewallPolicy(meta, name, owner, resourceUri, rules, servers, uuid);
        }
    }

    protected final Object meta;
    protected final Owner owner;
    protected final List<FirewallRule> rules;
    protected final List<Server> servers;

    public FirewallPolicy(Object meta, String name, Owner owner, URI resourceUri, List<FirewallRule> rules
            , List<Server> servers, String uuid) {
        super(uuid, name, resourceUri);
        this.meta = meta;
        this.owner = owner;
        this.rules = rules;
        this.servers = servers;
    }

    /**
     * @return User assigned meta information for this policy
     */
    public Object getMeta() {
        return meta;
    }

    /**
     * @return Human readable name of the firewall policy
     */
    public String getName() {
        return name;
    }

    /**
     * @return Owner of the policy
     */
    public Owner getOwner() {
        return owner;
    }

    /**
     * @return resource uri
     */
    public URI getResourceUri() {
        return resourceUri;
    }

    /**
     * @return List of rules to be applied for this policy
     */
    public List<FirewallRule> getRules() {
        return rules;
    }

    /**
     * @return Servers which have nics with this policy applied
     */
    public List<Server> getServers() {
        return servers;
    }

    /**
     * @return UUID of the policy
     */
    public String getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FirewallPolicy)) return false;
        if (!super.equals(o)) return false;

        FirewallPolicy that = (FirewallPolicy) o;

        if (meta != null ? !meta.equals(that.meta) : that.meta != null) return false;
        if (owner != null ? !owner.equals(that.owner) : that.owner != null) return false;
        if (rules != null ? !rules.equals(that.rules) : that.rules != null) return false;
        if (servers != null ? !servers.equals(that.servers) : that.servers != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (meta != null ? meta.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (rules != null ? rules.hashCode() : 0);
        result = 31 * result + (servers != null ? servers.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "[" +
                "meta=" + meta +
                ", name='" + name + '\'' +
                ", owner=" + owner +
                ", resourceUri='" + resourceUri + '\'' +
                ", rules=" + rules +
                ", servers=" + servers +
                ", uuid='" + uuid + '\'' +
                "]";
    }

    public NIC toNIC(){
        return new NIC.Builder().firewallPolicy(this).build();
    }
}
