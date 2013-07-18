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
import org.jclouds.cloudsigma2.domain.Transaction;
import org.jclouds.date.internal.SimpleDateFormatDateService;
import org.jclouds.http.HttpResponse;
import org.jclouds.json.config.GsonModule;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Shevchenko
 */
@Test(groups = "unit")
public class ParseTransactionListTest extends CloudSigma2ParserTest{

    private static final ParseTransactionList PARSE_TRANSACTION_LIST = Guice
                                                                    .createInjector(new GsonModule())
                                                                    .getInstance(ParseTransactionList.class);

    private List<Transaction> expected;

    @BeforeMethod
    public void setUp() throws Exception {
        expected = ImmutableList.of(
                new Transaction.Builder()
                        .amount(0.00173818150273075810)
                        .billingCycle(68844)
                        .end(128.20175975233523933736)
                        .id("11042920")
                        .initial(128.20349793383797009546)
                        .reason("Burst: 57783091200 of dssd for 299 seconds at 2013-07-09 07:49:06+00:00")
                        .time(new SimpleDateFormatDateService().iso8601SecondsDateParse("2013-07-09T07:49:54+00:00"))
                        .build()
                , new Transaction.Builder()
                        .amount(0.00174399481879340278)
                        .billingCycle(68844)
                        .end(128.20349793383797009546)
                        .id("11042919")
                        .initial(128.20524192865676349824)
                        .reason("Burst: 57783091200 of dssd for 300 seconds at 2013-07-09 07:44:06+00:00")
                        .time(new SimpleDateFormatDateService().iso8601SecondsDateParse("2013-07-09T07:49:54+00:00"))
                        .build()
                , new Transaction.Builder()
                        .amount(0.00173818150273075810)
                        .billingCycle(68843)
                        .end(128.20524192865676349824)
                        .id("11042661")
                        .initial(128.20698011015949425634)
                        .reason("Burst: 57783091200 of dssd for 299 seconds at 2013-07-09 07:39:06+00:00")
                        .time(new SimpleDateFormatDateService().iso8601SecondsDateParse("2013-07-09T07:40:05+00:00"))
                        .build()
                , new Transaction.Builder()
                        .amount(0.00174399481879340278)
                        .billingCycle(68843)
                        .end(128.20698011015949425634)
                        .id("11042660")
                        .initial(128.20872410497828765912)
                        .reason("Burst: 57783091200 of dssd for 300 seconds at 2013-07-09 07:34:06+00:00")
                        .time(new SimpleDateFormatDateService().iso8601SecondsDateParse("2013-07-09T07:40:04+00:00"))
                        .build()
        );
    }

    public void testEmptyJsonArrayToEmptyList(){
        Assert.assertEquals(PARSE_TRANSACTION_LIST.apply(HttpResponse.builder()
                                                                .message("OK")
                                                                .statusCode(200)
                                                                .payload(EMPTY_OBJECTS_ARRAY)
                                                                .build())
        , new ArrayList<Transaction>());
    }

    public void test(){
        Assert.assertEquals(PARSE_TRANSACTION_LIST.apply(HttpResponse.builder()
                                                                    .message("OK")
                                                                    .statusCode(200)
                                                                    .payload(getJsonResource("/ledger.json"))
                                                                    .build())
        , expected);
    }
}
