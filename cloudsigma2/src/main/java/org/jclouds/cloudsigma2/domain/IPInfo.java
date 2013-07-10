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
import com.google.common.collect.ImmutableSet;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Vladimir Shevchenko
 */
public class IPInfo extends IP {
    public static class Builder extends IP.Builder {
        protected Object meta;
        protected Subscription subscription;
        protected List<Tag> tags;
        protected List<String> nameservers;
        protected int netmask;
        protected String gateway;

        /**
         * @param meta User defined meta information
         * @return IPInfo Builder
         */
        public Builder meta(Object meta) {
            this.meta = meta;
            return this;
        }

        /**
         * @param subscription Subscription related to this VLAN
         * @return IPInfo Builder
         */
        public Builder subscription(Subscription subscription) {
            this.subscription = subscription;
            return this;
        }

        /**
         * @param tags Tags added to this VLAN
         * @return IPInfo Builder
         */
        public Builder tags(List<Tag> tags) {
            this.tags = ImmutableList.copyOf(tags);
            return this;
        }

        /**
         * @param nameservers Servers added to this IP
         * @return IPInfo Builder
         */
        public Builder nameservers(List<String> nameservers) {
            this.nameservers = ImmutableList.copyOf(nameservers);
            return this;
        }

        /**
         * @param netmask IP netmask
         * @return IPInfo Builder
         */
        public Builder netmask(int netmask) {
            this.netmask = netmask;
            return this;
        }

        /**
         * @param gateway getaway
         * @return IPInfo Builder
         */
        public Builder gateway(String gateway) {
            this.gateway = gateway;
            return this;
        }

        /**
         * {@inheritDoc}
         * @return IPInfo Builder
         */
        @Override
        public Builder uuid(String uuid) {
            return Builder.class.cast(super.uuid(uuid));
        }

        /**
         * {@inheritDoc}
         * @return IPInfo Builder
         */
        @Override
        public Builder owner(Owner owner) {
            return Builder.class.cast(super.owner(owner));
        }

        /**
         * {@inheritDoc}
         * @return IPInfo Builder
         */
        @Override
        public Builder server(Server server) {
            return Builder.class.cast(super.server(server));
        }

        /**
         * {@inheritDoc}
         * @return IPInfo Builder
         */
        @Override
        public Builder resourceUri(URI resourceUri) {
            return Builder.class.cast(super.resourceUri(resourceUri));
        }

        public IPInfo build() {
            return new IPInfo(uuid, owner, server, resourceUri, meta, subscription, tags, nameservers, netmask, gateway);
        }

        public Builder fromIP(IP ip){
            return new Builder().uuid(ip.getUuid()).owner(ip.getOwner()).server(ip.getServer()).resourceUri(ip.getResourceUri());
        }
    }

    protected final Object meta;
    protected final Subscription subscription;
    protected final List<Tag> tags;
    protected final List<String> nameservers;
    protected final int netmask;
    protected final String gateway;

    public IPInfo(String uuid, Owner owner, Server server, URI resourceUri, Object meta, Subscription subscription
            , List<Tag> tags, List<String> nameservers, int netmask, String gateway) {
        super(uuid, owner, server, resourceUri);
        this.meta = meta;
        this.subscription = subscription;
        this.tags = tags;
        this.nameservers = nameservers;
        this.netmask = netmask;
        this.gateway = gateway;
    }

    /**
     * @return User defined meta information
     */
    public Object getMeta() {
        return meta;
    }

    /**
     * @return Subscription related to this VLAN
     */
    public Subscription getSubscription() {
        return subscription;
    }

    /**
     * @return Tags added to this VLAN
     */
    public List<Tag> getTags() {
        return tags;
    }

    /**
     * @return Servers added to this IP
     */
    public List<String> getNameservers() {
        return nameservers;
    }

    /**
     * @return IP netmask
     */
    public int getNetmask() {
        return netmask;
    }

    /**
     * @return getaway
     */
    public String getGateway() {
        return gateway;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IPInfo)) return false;
        if (!super.equals(o)) return false;

        IPInfo ipInfo = (IPInfo) o;

        if (netmask != ipInfo.netmask) return false;
        if (gateway != null ? !gateway.equals(ipInfo.gateway) : ipInfo.gateway != null) return false;
        if (meta != null ? !meta.equals(ipInfo.meta) : ipInfo.meta != null) return false;
        if (nameservers != null ? !nameservers.equals(ipInfo.nameservers) : ipInfo.nameservers != null) return false;
        if (subscription != null ? !subscription.equals(ipInfo.subscription) : ipInfo.subscription != null)
            return false;
        if (tags != null ? !tags.equals(ipInfo.tags) : ipInfo.tags != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (meta != null ? meta.hashCode() : 0);
        result = 31 * result + (subscription != null ? subscription.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        result = 31 * result + (nameservers != null ? nameservers.hashCode() : 0);
        result = 31 * result + netmask;
        result = 31 * result + (gateway != null ? gateway.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "[" +
                "owner=" + owner +
                ", uuid='" + uuid + '\'' +
                ", server=" + server +
                ", resourceUri=" + resourceUri +
                ", meta=" + meta +
                ", subscription=" + subscription +
                ", tags=" + tags +
                ", nameservers=" + nameservers +
                ", netmask=" + netmask +
                ", gateway='" + gateway + '\'' +
                "]";
    }
}
