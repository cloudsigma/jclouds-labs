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
import org.jclouds.cloudsigma2.domain.AccountBalance;
import org.jclouds.cloudsigma2.domain.AccountUsage;
import org.jclouds.cloudsigma2.domain.CurrentUsage;
import org.jclouds.cloudsigma2.domain.Usage;
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
public class ParseCurrentUsageTest extends CloudSigma2ParserTest{

    private static final ParseCurrentUsage PARSE_CURRENT_USAGE = Guice.createInjector(new GsonModule()).getInstance(ParseCurrentUsage.class);

    private CurrentUsage expected;

    @BeforeMethod
    public void setUp() throws Exception {
        expected = new CurrentUsage.Builder()
                .balance(new AccountBalance(128.20175975233523933736, "USD"))
                .usage(new AccountUsage.Builder()
                        .cpu(new Usage(new BigInteger("0"), new BigInteger("8000"), new BigInteger("0")))
                        .dssd(new Usage(new BigInteger("57783091200"), new BigInteger("32212254720"), new BigInteger("89995345920")))
                        .ip(new Usage(new BigInteger("0"), new BigInteger("1"), new BigInteger("0")))
                        .mem(new Usage(new BigInteger("0"), new BigInteger("17179869184"), new BigInteger("0")))
                        .windowsWebServer2008(new Usage(new BigInteger("0"), new BigInteger("0"), new BigInteger("0")))
                        .windowsServer2008Standard(new Usage(new BigInteger("0"), new BigInteger("0"), new BigInteger("0")))
                        .sqlServerStandard2008(new Usage(new BigInteger("0"), new BigInteger("0"), new BigInteger("0")))
                        .sms(new Usage(new BigInteger("0"), new BigInteger("0"), new BigInteger("0")))
                        .ssd(new Usage(new BigInteger("0"), new BigInteger("0"), new BigInteger("0")))
                        .tx(new Usage(new BigInteger("0"), new BigInteger("0"), new BigInteger("0")))
                        .vlan(new Usage(new BigInteger("0"), new BigInteger("1"), new BigInteger("0")))
                        .build())
                .build();
    }

    public void test(){
        Assert.assertEquals(PARSE_CURRENT_USAGE.apply(HttpResponse.builder()
                                                .statusCode(200)
                                                .message("OK")
                                                .payload(getJsonResource("/currentusage"))
                                                .build())
        , expected);
    }
}
