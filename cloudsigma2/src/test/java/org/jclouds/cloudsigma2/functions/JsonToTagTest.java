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
import org.jclouds.cloudsigma2.beans.RawOwner;
import org.jclouds.cloudsigma2.beans.RawTag;
import org.jclouds.cloudsigma2.domain.Owner;
import org.jclouds.cloudsigma2.domain.Tag;
import org.jclouds.cloudsigma2.domain.TagResource;
import org.jclouds.cloudsigma2.domain.TagResourceType;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Vladimir Shevchenko
 */
@Test(groups = "unit")
public class JsonToTagTest {

    private static final JsonToTag JSON_TO_TAG = Guice.createInjector().getInstance(JsonToTag.class);

    private RawTag input;
    private Tag expected;

    @BeforeMethod
    public void setUp() throws Exception {
        Owner owner = new Owner.Builder()
                .resourceUri(new URI("/api/2.0/user/5b4a69a3-8e78-4c45-a8ba-8b13f0895e23/"))
                .uuid("5b4a69a3-8e78-4c45-a8ba-8b13f0895e23")
                .build();

        Map<String, String> meta = new HashMap<String, String>();
        meta.put("description", "test tag");

        expected = new Tag.Builder()
                .meta(meta)
                .name("TagCreatedWithResource")
                .owner(owner)
                .resourceUri(new URI("/api/2.0/tags/68bb0cfc-0c76-4f37-847d-7bb705c5ae46/"))
                .resources(ImmutableList.of(
                        new TagResource.Builder()
                                .resourceType(TagResourceType.VLANS)
                                .resourceUri(new URI("/api/2.0/vlans/96537817-f4b6-496b-a861-e74192d3ccb0/"))
                                .uuid("96537817-f4b6-496b-a861-e74192d3ccb0")
                                .owner(owner)
                                .build()
                        , new TagResource.Builder()
                                .resourceType(TagResourceType.SERVERS)
                                .resourceUri(new URI("/api/2.0/servers/61bcc398-c034-42f1-81c9-f6d7f62c4ea0/"))
                                .uuid("61bcc398-c034-42f1-81c9-f6d7f62c4ea0")
                                .owner(owner)
                                .build()
                        , new TagResource.Builder()
                                .resourceType(TagResourceType.DRIVES)
                                .resourceUri(new URI("/api/2.0/drives/3610d935-514a-4552-acf3-a40dd0a5f961/"))
                                .uuid("3610d935-514a-4552-acf3-a40dd0a5f961")
                                .owner(owner)
                                .build()
                        , new TagResource.Builder()
                                .resourceType(TagResourceType.IPS)
                                .resourceUri(new URI("/api/2.0/ips/185.12.6.183/"))
                                .uuid("185.12.6.183")
                                .owner(owner)
                                .build()
                ))
                .uuid("68bb0cfc-0c76-4f37-847d-7bb705c5ae46")
                .build();

        input = new RawTag();

        RawOwner rawOwner = new RawOwner();
        rawOwner.resource_uri = "/api/2.0/user/5b4a69a3-8e78-4c45-a8ba-8b13f0895e23/";
        rawOwner.uuid = "5b4a69a3-8e78-4c45-a8ba-8b13f0895e23";

        input.meta = meta;
        input.owner = rawOwner;
        input.resource_uri = "/api/2.0/tags/68bb0cfc-0c76-4f37-847d-7bb705c5ae46/";
        input.uuid = "68bb0cfc-0c76-4f37-847d-7bb705c5ae46";
        input.name = "TagCreatedWithResource";

        RawTag.RawTagResource rawTagResource1 = input.new RawTagResource();
        rawTagResource1.owner = rawOwner;
        rawTagResource1.res_type = "vlans";
        rawTagResource1.resource_uri = "/api/2.0/vlans/96537817-f4b6-496b-a861-e74192d3ccb0/";
        rawTagResource1.uuid = "96537817-f4b6-496b-a861-e74192d3ccb0";
        RawTag.RawTagResource rawTagResource2 = input.new RawTagResource();
        rawTagResource2.owner = rawOwner;
        rawTagResource2.res_type = "servers";
        rawTagResource2.resource_uri = "/api/2.0/servers/61bcc398-c034-42f1-81c9-f6d7f62c4ea0/";
        rawTagResource2.uuid = "61bcc398-c034-42f1-81c9-f6d7f62c4ea0";
        RawTag.RawTagResource rawTagResource3 = input.new RawTagResource();
        rawTagResource3.owner = rawOwner;
        rawTagResource3.res_type = "drives";
        rawTagResource3.resource_uri = "/api/2.0/drives/3610d935-514a-4552-acf3-a40dd0a5f961/";
        rawTagResource3.uuid = "3610d935-514a-4552-acf3-a40dd0a5f961";
        RawTag.RawTagResource rawTagResource4 = input.new RawTagResource();
        rawTagResource4.owner = rawOwner;
        rawTagResource4.res_type = "ips";
        rawTagResource4.resource_uri = "/api/2.0/ips/185.12.6.183/";
        rawTagResource4.uuid = "185.12.6.183";

        input.resources = ImmutableList.of(rawTagResource1, rawTagResource2, rawTagResource3 ,rawTagResource4);
    }

    public void test(){
        Assert.assertEquals(JSON_TO_TAG.apply(input), expected);
    }
}
