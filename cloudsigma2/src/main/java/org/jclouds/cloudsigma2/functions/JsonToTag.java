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
import org.jclouds.cloudsigma2.beans.RawTag;
import org.jclouds.cloudsigma2.domain.Owner;
import org.jclouds.cloudsigma2.domain.Tag;
import org.jclouds.cloudsigma2.domain.TagResource;
import org.jclouds.cloudsigma2.domain.TagResourceType;
import org.jclouds.javax.annotation.Nullable;

import javax.inject.Singleton;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Shevchenko
 */
@Singleton
public class JsonToTag implements Function<RawTag, Tag> {
    @Override
    public Tag apply(@Nullable RawTag input) {
        Tag.Builder tagBuilder = new Tag.Builder();

        if(input.owner != null){
            Owner.Builder ownerBuilder = new Owner.Builder();

            if(input.owner.uuid != null){
                ownerBuilder.uuid(input.owner.uuid);
            }
            if(input.owner.email != null){
                ownerBuilder.email(input.owner.email);
            }
            if(input.owner.resource_uri != null){
                ownerBuilder.resourceUri(input.owner.resource_uri);
            }
            tagBuilder.owner(ownerBuilder.build());
        }

        if(input.resource_uri != null){
            try {
                tagBuilder.resourceUri(new URI(input.resource_uri));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        if(input.uuid != null){
            tagBuilder.uuid(input.uuid);
        }

        if(input.name != null){
            tagBuilder.name(input.name);
        }

        if(input.meta != null){
            tagBuilder.meta(input.meta);
        }

        if(input.resources != null){
            List<TagResource> tagResourceList = new ArrayList<TagResource>();

            for(RawTag.RawTagResource rawTagResource : input.resources){
                TagResource.Builder tagResourceBuilder = new TagResource.Builder();

                if(rawTagResource.owner != null){
                    Owner.Builder ownerBuilder = new Owner.Builder();

                    if(rawTagResource.owner.uuid != null){
                        ownerBuilder.uuid(rawTagResource.owner.uuid);
                    }
                    if(rawTagResource.owner.email != null){
                        ownerBuilder.email(rawTagResource.owner.email);
                    }
                    if(rawTagResource.owner.resource_uri != null){
                        ownerBuilder.resourceUri(rawTagResource.owner.resource_uri);
                    }
                    tagResourceBuilder.owner(ownerBuilder.build());
                }

                if(rawTagResource.res_type != null){
                    tagResourceBuilder.resourceType(TagResourceType.fromValue(rawTagResource.res_type));
                }

                if(rawTagResource.uuid != null){
                    tagResourceBuilder.uuid(rawTagResource.uuid);
                }

                if(rawTagResource.resource_uri != null){
                    tagResourceBuilder.resourceUri(rawTagResource.resource_uri);
                }

                tagResourceList.add(tagResourceBuilder.build());
            }

            tagBuilder.resources(tagResourceList);
        }

        return tagBuilder.build();
    }
}
