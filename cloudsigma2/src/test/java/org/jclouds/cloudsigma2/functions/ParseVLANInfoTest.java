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
import org.jclouds.cloudsigma2.domain.*;
import org.jclouds.http.HttpResponse;
import org.jclouds.json.config.GsonModule;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Vladimir Shevchenko
 */
@Test(groups = "unit")
public class ParseVLANInfoTest extends CloudSigma2ParserTest{

    private static final ParseVLANInfo PARSE_VLAN_INFO = Guice.createInjector(new GsonModule()).getInstance(ParseVLANInfo.class);

    private VLANInfo expected;

    @BeforeMethod
    public void setUp() throws Exception {
        Owner owner = new Owner.Builder()
                .resourceUri(new URI("/api/2.0/user/5b4a69a3-8e78-4c45-a8ba-8b13f0895e23/"))
                .uuid("5b4a69a3-8e78-4c45-a8ba-8b13f0895e23")
                .build();

        Map<String, String> meta = new HashMap<String, String>();
        meta.put("description", "test vlan");

        expected = new VLANInfo.Builder()
                            .meta(meta)
                            .owner(owner)
                            .resourceUri(new URI("/api/2.0/vlans/96537817-f4b6-496b-a861-e74192d3ccb0/"))
                            .servers(ImmutableList.of(
                                    new Server.Builder()
                                            .uuid("81f911f9-5152-4328-8671-02543bafbd0e")
                                            .resourceUri(new URI("/api/2.0/servers/81f911f9-5152-4328-8671-02543bafbd0e/"))
                                            .build()
                                    , new Server.Builder()
                                    .uuid("19163e1a-a6d6-4e73-8087-157dd302c373")
                                    .resourceUri(new URI("/api/2.0/servers/19163e1a-a6d6-4e73-8087-157dd302c373/"))
                                    .build()
                            ))
                            .subscription(new Subscription.Builder()
                                    .id("7272")
                                    .resourceUri(new URI("/api/2.0/subscriptions/7272/"))
                                    .build())
                            .tags(new ArrayList<Tag>())
                            .uuid("96537817-f4b6-496b-a861-e74192d3ccb0")
                            .build();
    }

    public void test(){
        Assert.assertEquals(PARSE_VLAN_INFO.apply(HttpResponse.builder()
                .statusCode(200)
                .message("OK")
                .payload(getJsonResource("/vlan-single"))
                .build())
        , expected);
    }
}
