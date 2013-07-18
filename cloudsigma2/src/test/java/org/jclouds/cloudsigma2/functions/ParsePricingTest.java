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
import org.jclouds.cloudsigma2.domain.BurstLevel;
import org.jclouds.cloudsigma2.domain.Price;
import org.jclouds.cloudsigma2.domain.Pricing;
import org.jclouds.cloudsigma2.domain.SubscriptionResource;
import org.jclouds.http.HttpResponse;
import org.jclouds.json.config.GsonModule;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigInteger;

/**
 * @author Vladimir Shevchenko
 */
@Test(groups = "unit")
public class ParsePricingTest extends CloudSigma2ParserTest{

    private static final ParsePricing PARSE_PRICING = Guice.createInjector(new GsonModule()).getInstance(ParsePricing.class);

    private Pricing expected;

    @BeforeMethod
    public void setUp() throws Exception {
        expected = new Pricing.Builder()
                .current(new BurstLevel.Builder()
                                    .cpu(6)
                                    .dssd(1)
                                    .ip(1)
                                    .mem(6)
                                    .windowsWebServer2008(1)
                                    .windowsServer2008Standard(1)
                                    .sqlServerStandard2008(1)
                                    .sms(1)
                                    .ssd(1)
                                    .tx(6)
                                    .vlan(1)
                                    .build())
                .next(new BurstLevel.Builder()
                                    .cpu(6)
                                    .dssd(1)
                                    .ip(1)
                                    .mem(6)
                                    .windowsWebServer2008(1)
                                    .windowsServer2008Standard(1)
                                    .sqlServerStandard2008(1)
                                    .sms(1)
                                    .ssd(1)
                                    .tx(5)
                                    .vlan(1)
                                    .build())
                .priceList(ImmutableList.of(
                        new Price.Builder()
                                .currency("USD")
                                .id("568")
                                .level(1)
                                .multiplier(new BigInteger("2783138807808000"))
                                .price(0.28000000000000000000)
                                .resource(SubscriptionResource.DSSD)
                                .unit("GB/month")
                                .build()
                        , new Price.Builder()
                                .currency("GBP")
                                .id("567")
                                .level(1)
                                .multiplier(new BigInteger("2783138807808000"))
                                .price(0.18200000000000000000)
                                .resource(SubscriptionResource.DSSD)
                                .unit("GB/month")
                                .build()
                        , new Price.Builder()
                                .currency("EUR")
                                .id("566")
                                .level(1)
                                .multiplier(new BigInteger("2783138807808000"))
                                .price(0.21000000000000000000)
                                .resource(SubscriptionResource.DSSD)
                                .unit("GB/month")
                                .build()
                        , new Price.Builder()
                                .currency("CHF")
                                .id("565")
                                .level(1)
                                .multiplier(new BigInteger("2783138807808000"))
                                .price(0.26600000000000000000)
                                .resource(SubscriptionResource.DSSD)
                                .unit("GB/month")
                                .build()
                        , new Price.Builder()
                                .currency("CHF")
                                .id("304")
                                .level(0)
                                .multiplier(new BigInteger("2783138807808000"))
                                .price(0.13300000000000000000)
                                .resource(SubscriptionResource.DSSD)
                                .unit("GB/month")
                                .build()
                ))
                .build();
    }

    public void test(){
        Assert.assertEquals(PARSE_PRICING.apply(HttpResponse.builder()
                                        .message("OK")
                                        .statusCode(200)
                                        .payload(getJsonResource("/pricing.json"))
                                        .build())
        , expected);
    }
}
