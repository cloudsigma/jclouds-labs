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
import org.jclouds.cloudsigma2.domain.ServerAvailabilityGroup;
import org.jclouds.http.HttpResponse;
import org.jclouds.json.config.GsonModule;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @author Vladimir Shevchenko
 */
@Test(groups = "unit")
public class ParseAvailabilityGroupListTest extends CloudSigma2ParserTest{

    private static final ParseAvailabilityGroupList PARSE_AVAILABILITY_GROUP = Guice
                                                                        .createInjector(new GsonModule())
                                                                        .getInstance(ParseAvailabilityGroupList.class);

    private List<ServerAvailabilityGroup> expected;

    @BeforeMethod
    public void setUp() throws Exception {
        expected = ImmutableList.of(
                new ServerAvailabilityGroup(ImmutableList.of(
                        "313e73a4-592f-48cf-81c4-a6c079d005a5",
                        "e035a488-8587-4a15-ab25-9b7343236bc9" ))
                , new ServerAvailabilityGroup(ImmutableList.of(
                        "313e73a4-592f-48cf-81c4-a6c079d005a5",
                        "e035a488-8587-4a15-ab25-9b7343236bc9"))
        );
    }

    public void testEmptyJsonArrayReturnEmptyList(){
        Assert.assertEquals(PARSE_AVAILABILITY_GROUP.apply(HttpResponse.builder()
                                                                .statusCode(200)
                                                                .message("OK")
                                                                .payload("[]")
                                                                .build())
        , ImmutableList.of());
    }

    public void test(){
        Assert.assertEquals(PARSE_AVAILABILITY_GROUP.apply(HttpResponse.builder()
                                                                .statusCode(200)
                                                                .message("OK")
                                                                .payload(getJsonResource("/servers-availability-groups"))
                                                                .build())
        , expected);
    }
}
