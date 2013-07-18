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
import org.jclouds.cloudsigma2.beans.RawSubscription;
import org.jclouds.cloudsigma2.domain.Subscription;
import org.jclouds.cloudsigma2.domain.SubscriptionResource;
import org.jclouds.date.internal.SimpleDateFormatDateService;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.URI;
import java.util.ArrayList;

/**
 * @author Vladimir Shevchenko
 */
@Test(groups = "unit")
public class JsonToSubscriptionTest {

    private static final JsonToSubscription JSON_TO_SUBSCRIPTION = Guice.createInjector().getInstance(JsonToSubscription.class);

    private RawSubscription input;
    private Subscription expected;

    @BeforeMethod
    public void setUp() throws Exception {
        expected  = new Subscription.Builder()
                .amount("30000")
                .isAutoRenewEnabled(true)
                .descendants(new ArrayList<Subscription>())
                .discountAmount(10)
                .discountPercent(10)
                .endTime(new SimpleDateFormatDateService().iso8601SecondsDateParse("2013-04-22T12:00:00+00:00"))
                .id("28")
                .period("31 days, 1:31:53.888650")
                .price(0.000003839842975139617919921875)
                .remaining("30000")
                .resource(SubscriptionResource.DSSD)
                .resourceUri(new URI("/api/2.0/subscriptions/28/"))
                .startTime(new SimpleDateFormatDateService().iso8601SecondsDateParse("2013-03-22T10:28:06+00:00"))
                .status("active")
                .subscribedObject("96537817-f4b6-496b-a861-e74192d3ccb0")
                .uuid("f622f364-bccf-4a36-a1ce-632a81640ad4")
                .build();

        input = new RawSubscription();
        input.amount = "30000";
        input.auto_renew = true;
        input.descendants = new ArrayList<RawSubscription>();
        input.discount_amount = 10d;
        input.discount_percent = 10d;
        input.end_time = "2013-04-22T12:00:00+00:00";
        input.id = "28";
        input.period = "31 days, 1:31:53.888650";
        input.price = 0.000003839842975139617919921875;
        input.remaining = "30000";
        input.resource = "dssd";
        input.resource_uri = "/api/2.0/subscriptions/28/";
        input.start_time = "2013-03-22T10:28:06+00:00";
        input.status = "active";
        input.subscribed_object = "96537817-f4b6-496b-a861-e74192d3ccb0";
        input.uuid = "f622f364-bccf-4a36-a1ce-632a81640ad4";
    }

    public void test(){
        Assert.assertEquals(JSON_TO_SUBSCRIPTION.apply(input), expected);
    }
}
