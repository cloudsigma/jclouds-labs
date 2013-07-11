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
import org.jclouds.cloudsigma2.beans.RawAccountBalance;
import org.jclouds.cloudsigma2.domain.AccountBalance;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Vladimir Shevchenko
 */
@Test(groups = "unit")
public class JsonToAccountBalanceTest {

    private static final JsonToAccountBalance JSON_TO_ACCOUNT_BALANCE = Guice.createInjector().getInstance(JsonToAccountBalance.class);

    private RawAccountBalance input;
    private AccountBalance expected;

    @BeforeMethod
    public void setUp() throws Exception {
        input = new RawAccountBalance();
        input.balance = "128.20175975233523933736";
        input.currency = "USD";

        expected = new AccountBalance(128.20175975233523933736, "USD");
    }

    public void test(){
        Assert.assertEquals(JSON_TO_ACCOUNT_BALANCE.apply(input), expected);
    }
}
