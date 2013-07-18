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
import org.jclouds.cloudsigma2.domain.Owner;
import org.jclouds.cloudsigma2.domain.Server;
import org.jclouds.cloudsigma2.domain.ServerStatus;
import org.jclouds.http.HttpResponse;
import org.jclouds.json.config.GsonModule;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Shevchenko
 */
@Test(groups = "unit")
public class ParseServerListTest extends CloudSigma2ParserTest{

    private final static ParseServerList PARSE_SERVER_LIST = Guice.createInjector(new GsonModule()).getInstance(ParseServerList.class);

    private List<Server> expectedList;

    @BeforeMethod
    public void setUp() throws Exception {
        Owner owner = new Owner.Builder()
                            .resourceUri(new URI("/api/2.0/user/5b4a69a3-8e78-4c45-a8ba-8b13f0895e23/"))
                            .uuid("5b4a69a3-8e78-4c45-a8ba-8b13f0895e23")
                            .build();

        expectedList = ImmutableList.of(
                new Server.Builder()
                        .name("test_server_3")
                        .owner(owner)
                        .resourceUri(new URI("/api/2.0/servers/61d61337-884b-4c87-b4de-f7f48f9cfc84/"))
                        .runtime(null)
                        .status(ServerStatus.STOPPED)
                        .uuid("61d61337-884b-4c87-b4de-f7f48f9cfc84")
                        .build()
                , new Server.Builder()
                        .name("test_server_1")
                        .owner(owner)
                        .resourceUri(new URI("/api/2.0/servers/33e71c37-0d0a-4a3a-a1ea-dc7265c9a154/"))
                        .runtime(null)
                        .status(ServerStatus.STOPPED)
                        .uuid("33e71c37-0d0a-4a3a-a1ea-dc7265c9a154")
                .build()
                , new Server.Builder()
                        .name("test_server_2")
                        .owner(owner)
                        .resourceUri(new URI("/api/2.0/servers/05c16b9a-f2f5-4da6-a1cb-b90722c32212/"))
                        .runtime(null)
                        .status(ServerStatus.STOPPED)
                        .uuid("05c16b9a-f2f5-4da6-a1cb-b90722c32212")
                        .build()
        );
    }

    public void testEmptyJsonArrayReturnEmptyList(){
        Assert.assertEquals(PARSE_SERVER_LIST.apply(HttpResponse.builder()
                .message("OK")
                .statusCode(200)
                .payload(EMPTY_OBJECTS_ARRAY)
                .build())
                , new ArrayList<Server>());
    }

    public void test(){
        Assert.assertEquals(PARSE_SERVER_LIST.apply(HttpResponse.builder()
                .message("OK")
                .statusCode(200)
                .payload(getJsonResource("/servers.json"))
                .build())
            , expectedList);
    }
}
