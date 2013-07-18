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
import org.jclouds.cloudsigma2.domain.Subscription;
import org.jclouds.cloudsigma2.domain.SubscriptionResource;
import org.jclouds.date.internal.SimpleDateFormatDateService;
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
public class ParseSubscriptionsListTest extends CloudSigma2ParserTest{

    private final static ParseSubscriptionsList PARSE_SUBSCRIPTIONS_LIST = Guice.createInjector(new GsonModule()).getInstance(ParseSubscriptionsList.class);

    private List<Subscription> expected;

    @BeforeMethod
    public void setUp() throws Exception {
        expected = ImmutableList.of(
                new Subscription.Builder()
                        .amount("1")
                        .isAutoRenewEnabled(true)
                        .descendants(new ArrayList<Subscription>())
                        .discountAmount(10)
                        .discountPercent(10)
                        .endTime(new SimpleDateFormatDateService().iso8601SecondsDateParse("2014-02-20T11:12:34+00:00"))
                        .id("7272")
                        .period("345 days, 0:00:00")
                        .price(0E-20)
                        .remaining("1")
                        .resource(SubscriptionResource.VLAN)
                        .resourceUri(new URI("/api/2.0/subscriptions/7272/"))
                        .startTime(new SimpleDateFormatDateService().iso8601SecondsDateParse("2013-03-12T11:12:34+00:00"))
                        .status("active")
                        .subscribedObject("96537817-f4b6-496b-a861-e74192d3ccb0")
                        .uuid("509f8e27-1e64-49bb-aa5a-baec074b0210")
                        .build()
                , new Subscription.Builder()
                        .amount("1")
                        .isAutoRenewEnabled(true)
                        .descendants(new ArrayList<Subscription>())
                        .endTime(new SimpleDateFormatDateService().iso8601SecondsDateParse("2014-02-20T11:12:41+00:00"))
                        .id("7273")
                        .period("345 days, 0:00:00")
                        .price(0E-20)
                        .remaining("1")
                        .resource(SubscriptionResource.IP)
                        .resourceUri(new URI("/api/2.0/subscriptions/7273/"))
                        .startTime(new SimpleDateFormatDateService().iso8601SecondsDateParse("2013-03-12T11:12:41+00:00"))
                        .status("active")
                        .subscribedObject("185.12.6.183")
                        .uuid("c2423c1a-8768-462c-bdc3-4ca09c1e650b")
                        .build()
                , new Subscription.Builder()
                        .amount("17179869184")
                        .isAutoRenewEnabled(true)
                        .descendants(new ArrayList<Subscription>())
                        .endTime(new SimpleDateFormatDateService().iso8601SecondsDateParse("2014-02-20T14:04:14+00:00"))
                        .id("3985")
                        .period("365 days, 0:00:00")
                        .price(0E-20)
                        .remaining("17179869184")
                        .resource(SubscriptionResource.MEM)
                        .resourceUri(new URI("/api/2.0/subscriptions/3985/"))
                        .startTime(new SimpleDateFormatDateService().iso8601SecondsDateParse("2013-02-20T14:04:14+00:00"))
                        .status("active")
                        .uuid("9bb117d3-4bc5-4e2d-a907-b20abd48eaf9")
                        .build()
        );
    }

    public void testEmptyJsonArrayReturnEmptyList(){
        Assert.assertEquals(PARSE_SUBSCRIPTIONS_LIST.apply(HttpResponse.builder()
                                                                    .message("OK")
                                                                    .statusCode(200)
                                                                    .payload(EMPTY_OBJECTS_ARRAY)
                                                                    .build())
        , new ArrayList<Subscription>());
    }

    public void test(){
        Assert.assertEquals(PARSE_SUBSCRIPTIONS_LIST.apply(HttpResponse.builder()
                                                                    .message("OK")
                                                                    .statusCode(200)
                                                                    .payload(getJsonResource("/subscriptions"))
                                                                    .build())
        , expected);
    }
}
