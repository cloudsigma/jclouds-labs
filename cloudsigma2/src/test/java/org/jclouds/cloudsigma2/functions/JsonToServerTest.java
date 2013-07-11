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

import com.google.inject.Guice;
import org.jclouds.cloudsigma2.beans.RawOwner;
import org.jclouds.cloudsigma2.beans.RawServer;
import org.jclouds.cloudsigma2.domain.Owner;
import org.jclouds.cloudsigma2.domain.Server;
import org.jclouds.cloudsigma2.domain.ServerStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.URI;

/**
 * @author Vladimir Shevchenko
 */
@Test(groups = "unit")
public class JsonToServerTest {

    private static final JsonToServer JSON_TO_SERVER = Guice.createInjector().getInstance(JsonToServer.class);

    private RawServer input;
    private Server expected;

    @BeforeMethod
    public void setUp() throws Exception {
        RawOwner rawOwner = new RawOwner();
        rawOwner.resource_uri = "/api/2.0/user/5b4a69a3-8e78-4c45-a8ba-8b13f0895e23/";
        rawOwner.uuid = "5b4a69a3-8e78-4c45-a8ba-8b13f0895e23";

        input = new RawServer();
        input.name = "test_acc_full_server";
        input.owner = rawOwner;
        input.resource_uri = "/api/2.0/servers/a19a425f-9e92-42f6-89fb-6361203071bb/";
        input.status = "stopped";
        input.uuid = "a19a425f-9e92-42f6-89fb-6361203071bb";
        input.status = "stopped";

        Owner owner = new Owner.Builder()
                .resourceUri(new URI("/api/2.0/user/5b4a69a3-8e78-4c45-a8ba-8b13f0895e23/"))
                .uuid("5b4a69a3-8e78-4c45-a8ba-8b13f0895e23")
                .build();

        expected = new Server.Builder()
                .name("test_acc_full_server")
                .owner(owner)
                .resourceUri(new URI("/api/2.0/servers/a19a425f-9e92-42f6-89fb-6361203071bb/"))
                .runtime(null)
                .status(ServerStatus.STOPPED)
                .uuid("a19a425f-9e92-42f6-89fb-6361203071bb")
                .build();
    }

    public void test(){
        Assert.assertEquals(JSON_TO_SERVER.apply(input), expected);
    }
}
