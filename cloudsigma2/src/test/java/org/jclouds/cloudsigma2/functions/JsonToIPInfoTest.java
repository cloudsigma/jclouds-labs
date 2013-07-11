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
import org.jclouds.cloudsigma2.beans.RawIP;
import org.jclouds.cloudsigma2.beans.RawOwner;
import org.jclouds.cloudsigma2.beans.RawSubscription;
import org.jclouds.cloudsigma2.beans.RawTag;
import org.jclouds.cloudsigma2.domain.IPInfo;
import org.jclouds.cloudsigma2.domain.Owner;
import org.jclouds.cloudsigma2.domain.Subscription;
import org.jclouds.cloudsigma2.domain.Tag;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Vladimir Shevchenko
 */
@Test(groups = "unit")
public class JsonToIPInfoTest {

    private final static JsonToIPInfo JSON_TO_IP_INFO = Guice.createInjector().getInstance(JsonToIPInfo.class);

    private RawIP input;
    private IPInfo expected;

    @BeforeMethod
    public void setUp() throws Exception {

        Owner owner = new Owner.Builder()
                .resourceUri(new URI("/api/2.0/user/5b4a69a3-8e78-4c45-a8ba-8b13f0895e23/"))
                .uuid("5b4a69a3-8e78-4c45-a8ba-8b13f0895e23")
                .build();

        Map<String, String> meta = new HashMap<String, String>();
        meta.put("description", "test ip");

        try {
            expected = new IPInfo.Builder()
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
                    .build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        input = new RawIP();

        RawOwner rawOwner = new RawOwner();
        rawOwner.resource_uri = "/api/2.0/user/5b4a69a3-8e78-4c45-a8ba-8b13f0895e23/";
        rawOwner.uuid = "5b4a69a3-8e78-4c45-a8ba-8b13f0895e23";

        RawSubscription rawSubscription = new RawSubscription();
        rawSubscription.id = "7273";
        rawSubscription.resource_uri = "/api/2.0/subscriptions/7273/";

        input.owner = rawOwner;
        input.uuid = "185.12.6.183";
        input.resource_uri = "/api/2.0/ips/185.12.6.183/";
        input.meta = meta;
        input.tags = new ArrayList<RawTag>();
        input.nameservers = ImmutableList.of("69.194.139.62", "178.22.66.167", "178.22.71.56");
        input.netmask = 24;
        input.subscription = rawSubscription;
        input.gateway = "185.12.6.1";
    }

    public void test(){
        Assert.assertEquals(JSON_TO_IP_INFO.apply(input), expected);
    }
}
