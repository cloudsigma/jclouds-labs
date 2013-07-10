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
import org.jclouds.cloudsigma2.domain.*;
import org.jclouds.date.internal.SimpleDateFormatDateService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Shevchenko
 */
@Singleton
public class JsonToServer implements Function<RawServer, Server> {

    private final JsonToOwner jsonToOwner;

    @Inject
    public JsonToServer(JsonToOwner jsonToOwner) {
        this.jsonToOwner = jsonToOwner;
    }

    @Override
    public Server apply(RawServer input) {
        if(input == null){
            return null;
        }

        Server.Builder builder = new Server.Builder();
        if(input.owner != null){
            builder.owner(jsonToOwner.apply(input.owner));
        }

        if(input.resource_uri != null){
            builder.resourceUri(input.resource_uri);
        }

        if(input.status != null){
            builder.status(ServerStatus.fromValue(input.status));
        }

        if(input.uuid != null){
            builder.uuid(input.uuid);
        }

        if(input.name != null){
            builder.name(input.name);
        }

        if(input.runtime != null){
            ServerRuntime.Builder runtimeBuilder = new ServerRuntime.Builder();

            if(input.runtime.active_since != null){
                runtimeBuilder.activeSince(new SimpleDateFormatDateService().iso8601DateParse(input.runtime.active_since));
            }

            if(input.runtime.nics != null){
                List<NICStats> nicStatsIterable = new ArrayList<NICStats>();
                for(RawServer.RawNICStats nicStats : input.runtime.nics){
                    IOStats ioStats = new IOStats(nicStats.io.bytes_recv
                                                , nicStats.io.bytes_sent
                                                , nicStats.io.packets_recv
                                                , nicStats.io.packets_sent);
                    nicStatsIterable.add(new NICStats(InterfaceType.fromValue(nicStats.interface_type)
                            , ioStats
                            , nicStats.ip_v4
                            , nicStats.ip_v6
                            , nicStats.mac));
                }

                runtimeBuilder.nicStats(nicStatsIterable);
            }
        }

        return builder.build();
    }
}
