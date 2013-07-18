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
import org.jclouds.cloudsigma2.domain.Drive;
import org.jclouds.cloudsigma2.domain.DriveStatus;
import org.jclouds.cloudsigma2.domain.Owner;
import org.jclouds.http.HttpResponse;
import org.jclouds.json.config.GsonModule;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.URI;
import java.util.List;

/**
 * @author Vladimir Shevchenko
 */
@Test(groups = "unit")
public class ParseDrivesListTest extends CloudSigma2ParserTest {

    private static final ParseDrivesList PARSE_DRIVES_LIST = Guice.createInjector(new GsonModule()).getInstance(ParseDrivesList.class);

    private List<Drive> resultList = null;

    @BeforeMethod
    public void setUp() throws Exception {
        resultList = ImmutableList.of(new Drive.Builder()
                .owner(new Owner.Builder()
                        .resourceUri(new URI("/api/2.0/user/5b4a69a3-8e78-4c45-a8ba-8b13f0895e23/"))
                        .uuid("5b4a69a3-8e78-4c45-a8ba-8b13f0895e23")
                        .build())
                .resourceUri(new URI("/api/2.0/drives/92ca1450-417e-4cc1-983b-1015777e2591/"))
                .status(DriveStatus.UNMOUNTED)
                .uuid("92ca1450-417e-4cc1-983b-1015777e2591")
                .build(),

                new Drive.Builder()
                        .owner(new Owner.Builder()
                                .resourceUri(new URI("/api/2.0/user/5b4a69a3-8e78-4c45-a8ba-8b13f0895e23/"))
                                .uuid("5b4a69a3-8e78-4c45-a8ba-8b13f0895e23")
                                .build())
                        .resourceUri(new URI("/api/2.0/drives/414ad24b-ba41-47c0-9751-ef5060b6c391/"))
                        .status(DriveStatus.UNMOUNTED)
                        .uuid("414ad24b-ba41-47c0-9751-ef5060b6c391")
                        .build(),

                new Drive.Builder()
                        .owner(new Owner.Builder()
                                .resourceUri(new URI("/api/2.0/user/5b4a69a3-8e78-4c45-a8ba-8b13f0895e23/"))
                                .uuid("5b4a69a3-8e78-4c45-a8ba-8b13f0895e23")
                                .build())
                        .resourceUri(new URI("/api/2.0/drives/7bc04bc5-bd09-4269-b45d-16b58d6f71b4/"))
                        .status(DriveStatus.MOUNTED)
                        .uuid("7bc04bc5-bd09-4269-b45d-16b58d6f71b4")
                        .build());
    }

    public void testEmptyArrayReturnEmptyList(){
        Assert.assertEquals(PARSE_DRIVES_LIST.apply(HttpResponse.builder().payload(EMPTY_OBJECTS_ARRAY).build())
                , ImmutableList.of());
    }

    public void test(){
        Assert.assertEquals(PARSE_DRIVES_LIST.apply(HttpResponse.builder()
                .statusCode(200)
                .message("ok")
                .payload(getJsonResource("/drives.json"))
                .build())
                , resultList);
    }
}
