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
import org.jclouds.cloudsigma2.beans.RawDrive;
import org.jclouds.cloudsigma2.domain.License;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.URI;

/**
 * @author Vladimir Shevchenko
 */
@Test(groups = "unit")
public class JsonToLicenseTest {

    private static final JsonToLicense JSON_TO_LICENSE = Guice.createInjector().getInstance(JsonToLicense.class);

    private RawDrive.RawLicense input;
    private License expected;

    @BeforeMethod
    public void setUp() throws Exception {
        expected = new License.Builder()
                .isBurstable(true)
                .longName("Microsoft Windows Web Server 2008")
                .name("msft_lwa_00135")
                .resourceUri(new URI("/api/2.0/licenses/9/"))
                .type("instance")
                .userMetric("")
                .build();

        input = new RawDrive().new RawLicense();
        input.burstable = true;
        input.long_name = "Microsoft Windows Web Server 2008";
        input.name = "msft_lwa_00135";
        input.resource_uri = "/api/2.0/licenses/9/";
        input.type = "instance";
        input.user_metric = "";
    }

    public void test(){
        Assert.assertEquals(JSON_TO_LICENSE.apply(input), expected);
    }
}
