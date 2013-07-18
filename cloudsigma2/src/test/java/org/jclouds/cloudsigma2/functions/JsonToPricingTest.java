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
import org.jclouds.cloudsigma2.beans.RawPricing;
import org.jclouds.cloudsigma2.domain.BurstLevel;
import org.jclouds.cloudsigma2.domain.Price;
import org.jclouds.cloudsigma2.domain.Pricing;
import org.jclouds.cloudsigma2.domain.SubscriptionResource;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigInteger;

/**
 * @author Vladimir Shevchenko
 */
@Test(groups = "unit")
public class JsonToPricingTest {

    private static final JsonToPricing JSON_TO_PRICING = Guice.createInjector().getInstance(JsonToPricing.class);

    private RawPricing input;
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

        input = new RawPricing();

        RawPricing.RawBurstLevel currentRawBurstLevel = input.new RawBurstLevel();
        currentRawBurstLevel.cpu = 6;
        currentRawBurstLevel.dssd = 1;
        currentRawBurstLevel.ip = 1;
        currentRawBurstLevel.mem = 6;
        currentRawBurstLevel.msft_lwa_00135 = 1;
        currentRawBurstLevel.msft_p73_04837 = 1;
        currentRawBurstLevel.msft_tfa_00009 = 1;
        currentRawBurstLevel.sms = 1;
        currentRawBurstLevel.ssd = 1;
        currentRawBurstLevel.tx = 6;
        currentRawBurstLevel.vlan = 1;

        RawPricing.RawBurstLevel nextRawBurstLevel = input.new RawBurstLevel();
        nextRawBurstLevel.cpu = 6;
        nextRawBurstLevel.dssd = 1;
        nextRawBurstLevel.ip = 1;
        nextRawBurstLevel.mem = 6;
        nextRawBurstLevel.msft_lwa_00135 = 1;
        nextRawBurstLevel.msft_p73_04837 = 1;
        nextRawBurstLevel.msft_tfa_00009 = 1;
        nextRawBurstLevel.sms = 1;
        nextRawBurstLevel.ssd = 1;
        nextRawBurstLevel.tx = 5;
        nextRawBurstLevel.vlan = 1;

        RawPricing.RawPrice price1 = input.new RawPrice();
        price1.currency = "USD";
        price1.id = "568";
        price1.level = 1;
        price1.multiplier = new BigInteger("2783138807808000");
        price1.price = 0.28000000000000000000;
        price1.resource = "dssd";
        price1.unit = "GB/month";

        RawPricing.RawPrice price2 = input.new RawPrice();
        price2.currency = "GBP";
        price2.id = "567";
        price2.level = 1;
        price2.multiplier = new BigInteger("2783138807808000");
        price2.price = 0.18200000000000000000;
        price2.resource = "dssd";
        price2.unit = "GB/month";

        RawPricing.RawPrice price3 = input.new RawPrice();
        price3.currency = "EUR";
        price3.id = "566";
        price3.level = 1;
        price3.multiplier = new BigInteger("2783138807808000");
        price3.price = 0.21000000000000000000;
        price3.resource = "dssd";
        price3.unit = "GB/month";

        RawPricing.RawPrice price4 = input.new RawPrice();
        price4.currency = "CHF";
        price4.id = "565";
        price4.level = 1;
        price4.multiplier = new BigInteger("2783138807808000");
        price4.price = 0.26600000000000000000;
        price4.resource = "dssd";
        price4.unit = "GB/month";

        RawPricing.RawPrice price5 = input.new RawPrice();
        price5.currency = "CHF";
        price5.id = "304";
        price5.level = 0;
        price5.multiplier = new BigInteger("2783138807808000");
        price5.price = 0.13300000000000000000;
        price5.resource = "dssd";
        price5.unit = "GB/month";

        input.current = currentRawBurstLevel;
        input.next = nextRawBurstLevel;
        input.objects = ImmutableList.of(price1, price2, price3, price4, price5);
    }

    public void test(){
        Assert.assertEquals(JSON_TO_PRICING.apply(input), expected);
    }
}
