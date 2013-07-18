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
import org.jclouds.cloudsigma2.domain.Owner;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.URI;

/**
 * @author Vladimir Shevchenko
 */
@Test(groups = "unit")
public class JsonToOwnerTest {

    private static final JsonToOwner JSON_TO_OWNER = Guice.createInjector().getInstance(JsonToOwner.class);

    private RawOwner input;
    private Owner expected;

    @BeforeMethod
    public void setUp() throws Exception {
        input = new RawOwner();
        input.uuid = "5b4a69a3-8e78-4c45-a8ba-8b13f0895e23";
        input.resource_uri = "/api/2.0/user/5b4a69a3-8e78-4c45-a8ba-8b13f0895e23/";
        input.email = "sample@mail.com";

        expected = new Owner.Builder()
                .resourceUri(new URI("/api/2.0/user/5b4a69a3-8e78-4c45-a8ba-8b13f0895e23/"))
                .uuid("5b4a69a3-8e78-4c45-a8ba-8b13f0895e23")
                .email("sample@mail.com")
                .build();
    }

    public void test(){
        Assert.assertEquals(JSON_TO_OWNER.apply(input), expected);
    }
}
