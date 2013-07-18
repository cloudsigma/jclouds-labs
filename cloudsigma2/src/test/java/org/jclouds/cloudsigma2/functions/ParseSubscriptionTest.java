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

/**
 * @author Vladimir Shevchenko
 */
@Test(groups = "unit")
public class ParseSubscriptionTest extends CloudSigma2ParserTest{

    private static final ParseSubscription PARSE_SUBSCRIPTION = Guice.createInjector(new GsonModule()).getInstance(ParseSubscription.class);

    private Subscription expected;

    @BeforeMethod
    public void setUp() throws Exception {
        expected = new Subscription.Builder()
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
                        .uuid("f622f364-bccf-4a36-a1ce-632a81640ad4")
                        .build();

    }

    public void test(){
        Assert.assertEquals(PARSE_SUBSCRIPTION.apply(HttpResponse.builder()
                                            .message("OK")
                                            .statusCode(200)
                                            .payload(getJsonResource("/subscriptions-single.json"))
                                            .build())
        , expected);
    }
}
