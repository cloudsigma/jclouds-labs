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
import org.jclouds.javax.annotation.Nullable;

import java.net.URI;
import java.util.List;

/**
 * @author Vladimir Shevchenko
 */
public class Tag extends Item{

    public static class Builder extends Item.Builder{
        protected Object meta;
        protected Owner owner;
        protected List<TagResource> resources;

        public Builder meta(Object meta) {
            this.meta = meta;
            return this;
        }

        public Builder owner(Owner owner) {
            this.owner = owner;
            return this;
        }

        public Builder resources(List<TagResource> resources) {
            this.resources = ImmutableList.copyOf(resources);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Builder uuid(String uuid) {
            return Builder.class.cast(super.uuid(uuid));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Builder name(String name) {
            return Builder.class.cast(super.name(name));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Builder resourceUri(URI resourceUri) {
            return Builder.class.cast(super.resourceUri(resourceUri));
        }

        public Tag build(){
            return new Tag(uuid, name, resourceUri, meta, owner, resources);
        }
    }

    protected final Object meta;
    protected final Owner owner;
    protected final List<TagResource> resources;

    public Tag(@Nullable String uuid, String name, @Nullable URI resourceUri, Object meta, Owner owner, List<TagResource> resources) {
        super(uuid, name, resourceUri);
        this.meta = meta;
        this.owner = owner;
        this.resources = resources;
    }

    /**
     *
     * @return tag meta data
     */
    public Object getMeta() {
        return meta;
    }

    /**
     *
     * @return tag owner
     */
    public Owner getOwner() {
        return owner;
    }

    /**
     *
     * @return tag's resource list
     */
    public List<TagResource> getResources() {
        return resources;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tag other = (Tag) obj;
        if (meta == null) {
            if (other.meta != null)
                return false;
        } else if (!meta.equals(other.meta))
            return false;
        if (owner == null) {
            if (other.owner != null)
                return false;
        } else if (!owner.equals(other.owner))
            return false;
        if (resources == null) {
            if (other.resources != null)
                return false;
        } else if (!resources.equals(other.resources))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((meta == null) ? 0 : meta.hashCode());
        result = prime * result + ((owner == null) ? 0 : owner.hashCode());
        result = prime * result + ((resources == null) ? 0 : resources.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "[uuid=" + uuid + ", name=" + name + ", resourceUri=" + resourceUri
                + ", meta=" + meta + ", owner=" + owner + ", resources=" + resources + "]";
    }
}
