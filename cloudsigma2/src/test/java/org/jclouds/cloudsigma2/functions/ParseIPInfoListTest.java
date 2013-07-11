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
import org.jclouds.cloudsigma2.domain.IPInfo;
import org.jclouds.cloudsigma2.domain.Owner;
import org.jclouds.cloudsigma2.domain.Subscription;
import org.jclouds.cloudsigma2.domain.Tag;
import org.jclouds.http.HttpResponse;
import org.jclouds.json.config.GsonModule;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Vladimir Shevchenko
 */
@Test(groups = "unit")
public class ParseIPInfoListTest extends CloudSigma2ParserTest{

    private final static ParseIPInfoList PARSE_IP_INFO_LIST = Guice.createInjector(new GsonModule()).getInstance(ParseIPInfoList.class);

    private List<IPInfo> expected;

    @BeforeMethod
    public void setUp() throws Exception {
        Owner owner = new Owner.Builder()
                .resourceUri(new URI("/api/2.0/user/5b4a69a3-8e78-4c45-a8ba-8b13f0895e23/"))
                .uuid("5b4a69a3-8e78-4c45-a8ba-8b13f0895e23")
                .build();

        Map<String, String> meta = new HashMap<String, String>();
        meta.put("description", "test ip");

        expected = ImmutableList.of(
                new IPInfo.Builder()
                        .gateway("185.12.6.1")
                        .meta(meta)
                        .nameservers(ImmutableList.of(
                                "69.194.139.62",
                                "178.22.66.167",
                                "178.22.71.56"))
                        .netmask(24)
                        .owner(owner)
                        .resourceUri(new URI("/api/2.0/ips/185.12.6.183/"))
                        .server(null)
                        .subscription(new Subscription.Builder()
                                .id("7273")
                                .resourceUri(new URI("/api/2.0/subscriptions/7273/"))
                                .build())
                        .tags(new ArrayList<Tag>())
                        .uuid("185.12.6.183")
                        .build()
                , new IPInfo.Builder()
                        .gateway("185.12.5.1")
                        .meta(new HashMap<String, String>())
                        .nameservers(ImmutableList.of(
                                "178.22.66.167",
                                "178.22.71.56",
                                "8.8.8.8"))
                        .netmask(24)
                        .owner(owner)
                        .resourceUri(new URI("/api/2.0/ips/185.12.5.233/"))
                        .tags(new ArrayList<Tag>())
                        .uuid("185.12.5.233")
                        .build()
        );
    }

    public void testEmptyJsonArrayToEmptyList(){
        Assert.assertEquals(PARSE_IP_INFO_LIST.apply(HttpResponse.builder()
                .statusCode(200)
                .message("OK")
                .payload(EMPTY_OBJECTS_ARRAY)
                .build())
                , new ArrayList<IPInfo>());
    }

    public void test(){
        Assert.assertEquals(PARSE_IP_INFO_LIST.apply(HttpResponse.builder()
                        .statusCode(200)
                        .message("OK")
                        .payload(getJsonResource("/ips"))
                        .build())
        , expected);
    }
}
