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
import org.jclouds.cloudsigma2.beans.RawTransaction;
import org.jclouds.cloudsigma2.domain.Transaction;
import org.jclouds.date.internal.SimpleDateFormatDateService;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Vladimir Shevchenko
 */
@Test(groups = "unit")
public class JsonToTransactionTest {

    private static final JsonToTransaction JSON_TO_TRANSACTION = Guice.createInjector().getInstance(JsonToTransaction.class);

    private RawTransaction input;
    private Transaction expected;

    @BeforeMethod
    public void setUp() throws Exception {
        expected = new Transaction.Builder()
                .amount(0.00173818150273075810)
                .billingCycle(68844)
                .end(128.20175975233523933736)
                .id("11042920")
                .initial(128.20349793383797009546)
                .reason("Burst: 57783091200 of dssd for 299 seconds at 2013-07-09 07:49:06+00:00")
                .time(new SimpleDateFormatDateService().iso8601SecondsDateParse("2013-07-09T07:49:54+00:00"))
                .build();

        input = new RawTransaction();
        input.amount = 0.00173818150273075810;
        input.billing_cycle = 68844l;
        input.end = 128.20175975233523933736;
        input.id = "11042920";
        input.initial = 128.20349793383797009546;
        input.reason = "Burst: 57783091200 of dssd for 299 seconds at 2013-07-09 07:49:06+00:00";
        input.time = "2013-07-09T07:49:54+00:00";
    }

    public void test(){
        Assert.assertEquals(JSON_TO_TRANSACTION.apply(input), expected);
    }
}
