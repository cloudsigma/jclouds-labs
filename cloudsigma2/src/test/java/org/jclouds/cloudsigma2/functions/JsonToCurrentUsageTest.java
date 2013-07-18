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
import org.jclouds.cloudsigma2.beans.RawCurrentUsage;
import org.jclouds.cloudsigma2.domain.AccountBalance;
import org.jclouds.cloudsigma2.domain.AccountUsage;
import org.jclouds.cloudsigma2.domain.CurrentUsage;
import org.jclouds.cloudsigma2.domain.Usage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigInteger;

/**
 * @author Vladimir Shevchenko
 */
@Test(groups = "unit")
public class JsonToCurrentUsageTest {

    private static final JsonToCurrentUsage JSON_TO_CURRENT_USAGE = Guice.createInjector().getInstance(JsonToCurrentUsage.class);

    private RawCurrentUsage input;
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

        input = new RawCurrentUsage();

        RawAccountBalance rawAccountBalance = new RawAccountBalance();
        rawAccountBalance.balance = "128.20175975233523933736";
        rawAccountBalance.currency = "USD";

        RawCurrentUsage.RawAccountUsage accountUsage = input.new RawAccountUsage();

        RawCurrentUsage.RawUsage cpuUsage = input.new RawUsage();
        cpuUsage.burst = new BigInteger("0");
        cpuUsage.subscribed = new BigInteger("8000");
        cpuUsage.using = new BigInteger("0");

        RawCurrentUsage.RawUsage dssdUsage = input.new RawUsage();
        dssdUsage.burst = new BigInteger("57783091200");
        dssdUsage.subscribed = new BigInteger("32212254720");
        dssdUsage.using = new BigInteger("89995345920");

        RawCurrentUsage.RawUsage ipUsage = input.new RawUsage();
        ipUsage.burst = new BigInteger("0");
        ipUsage.subscribed = new BigInteger("1");
        ipUsage.using = new BigInteger("0");

        RawCurrentUsage.RawUsage memUsage = input.new RawUsage();
        memUsage.burst = new BigInteger("0");
        memUsage.subscribed = new BigInteger("17179869184");
        memUsage.using = new BigInteger("0");

        RawCurrentUsage.RawUsage msft_lwa_00135Usage = input.new RawUsage();
        msft_lwa_00135Usage.burst = new BigInteger("0");
        msft_lwa_00135Usage.subscribed = new BigInteger("0");
        msft_lwa_00135Usage.using = new BigInteger("0");

        RawCurrentUsage.RawUsage msft_p73_04837Usage = input.new RawUsage();
        msft_p73_04837Usage.burst = new BigInteger("0");
        msft_p73_04837Usage.subscribed = new BigInteger("0");
        msft_p73_04837Usage.using = new BigInteger("0");

        RawCurrentUsage.RawUsage msft_tfa_00009Usage = input.new RawUsage();
        msft_tfa_00009Usage.burst = new BigInteger("0");
        msft_tfa_00009Usage.subscribed = new BigInteger("0");
        msft_tfa_00009Usage.using = new BigInteger("0");

        RawCurrentUsage.RawUsage smsUsage = input.new RawUsage();
        smsUsage.burst = new BigInteger("0");
        smsUsage.subscribed = new BigInteger("0");
        smsUsage.using = new BigInteger("0");

        RawCurrentUsage.RawUsage ssdUsage = input.new RawUsage();
        ssdUsage.burst = new BigInteger("0");
        ssdUsage.subscribed = new BigInteger("0");
        ssdUsage.using = new BigInteger("0");

        RawCurrentUsage.RawUsage txUsage = input.new RawUsage();
        txUsage.burst = new BigInteger("0");
        txUsage.subscribed = new BigInteger("0");
        txUsage.using = new BigInteger("0");

        RawCurrentUsage.RawUsage vlanUsage = input.new RawUsage();
        vlanUsage.burst = new BigInteger("0");
        vlanUsage.subscribed = new BigInteger("1");
        vlanUsage.using = new BigInteger("0");

        accountUsage.cpu = cpuUsage;
        accountUsage.dssd = dssdUsage;
        accountUsage.ip = ipUsage;
        accountUsage.mem = memUsage;
        accountUsage.msft_lwa_00135 = msft_lwa_00135Usage;
        accountUsage.msft_p73_04837 = msft_p73_04837Usage;
        accountUsage.msft_tfa_00009 = msft_tfa_00009Usage;
        accountUsage.sms = smsUsage;
        accountUsage.ssd = ssdUsage;
        accountUsage.tx = txUsage;
        accountUsage.vlan = vlanUsage;

        input.balance = rawAccountBalance;
        input.usage = accountUsage;
    }

    public void test(){
        Assert.assertEquals(JSON_TO_CURRENT_USAGE.apply(input), expected);
    }
}
