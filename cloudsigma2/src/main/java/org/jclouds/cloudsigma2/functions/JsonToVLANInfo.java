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
import org.jclouds.cloudsigma2.beans.RawServer;
import org.jclouds.cloudsigma2.beans.RawTag;
import org.jclouds.cloudsigma2.beans.RawVLAN;
import org.jclouds.cloudsigma2.domain.Owner;
import org.jclouds.cloudsigma2.domain.Server;
import org.jclouds.cloudsigma2.domain.Tag;
import org.jclouds.cloudsigma2.domain.VLANInfo;
import org.jclouds.javax.annotation.Nullable;

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
public class JsonToVLANInfo implements Function<RawVLAN, VLANInfo>{

    private final JsonToOwner jsonToOwner;
    private final JsonToServer jsonToServer;
    private final JsonToSubscription jsonToSubscription;
    private final JsonToTag jsonToTag;

    @Inject
    public JsonToVLANInfo(JsonToOwner jsonToOwner, JsonToServer jsonToServer, JsonToSubscription jsonToSubscription, JsonToTag jsonToTag) {
        this.jsonToOwner = jsonToOwner;
        this.jsonToServer = jsonToServer;
        this.jsonToSubscription = jsonToSubscription;
        this.jsonToTag = jsonToTag;
    }

    @Override
    public VLANInfo apply(@Nullable RawVLAN input) {
        if(input == null){
            return null;
        }

        VLANInfo.Builder builder = new VLANInfo.Builder();

        if(input.uuid != null){
            builder.uuid(input.uuid);
        }

        if(input.owner != null){
            builder.owner(jsonToOwner.apply(input.owner));
        }

        if(input.meta != null){
            builder.meta(input.meta);
        }

        if(input.resource_uri != null){
            try {
                builder.resourceUri(new URI(input.resource_uri));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        if(input.subscription != null){
            builder.subscription(jsonToSubscription.apply(input.subscription));
        }

        if(input.servers != null){
            List<Server> serverList = new ArrayList<Server>();

            for(RawServer server : input.servers){
                serverList.add(jsonToServer.apply(server));
            }

            builder.servers(serverList);
        }

        if(input.tags != null){
            List<Tag> tagList = new ArrayList<Tag>();

            for(RawTag tag : input.tags){
                tagList.add(jsonToTag.apply(tag));
            }

            builder.tags(tagList);
        }

        return builder.build();
    }
}
