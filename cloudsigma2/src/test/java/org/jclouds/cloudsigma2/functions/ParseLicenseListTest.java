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
import org.jclouds.cloudsigma2.domain.License;
import org.jclouds.http.HttpResponse;
import org.jclouds.json.config.GsonModule;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.URI;
import java.util.List;

/**
 * @author Vladimir Shevchenko
 */
@Test(groups = "unit")
public class ParseLicenseListTest extends CloudSigma2ParserTest{

    private static final ParseLicenseList PARSE_LICENSE_LIST = Guice.createInjector(new GsonModule()).getInstance(ParseLicenseList.class);

    private List<License> expected;

    @BeforeMethod
    public void setUp() throws Exception {
        expected = ImmutableList.of(
                new License.Builder()
                        .isBurstable(false)
                        .longName("Microsoft Windows Web Server 2008")
                        .name("msft_lwa_00135")
                        .resourceUri(new URI("/api/2.0/licenses/9/"))
                        .type("instance")
                        .userMetric("")
                        .build()
                , new License.Builder()
                        .isBurstable(false)
                        .longName("Windows Server 2008 Standard")
                        .name("msft_p73_04837")
                        .resourceUri(new URI("/api/2.0/licenses/10/"))
                        .type("instance")
                        .userMetric("")
                        .build()
                , new License.Builder()
                        .isBurstable(false)
                        .longName("SQL Server Standard 2008")
                        .name("msft_tfa_00009")
                        .resourceUri(new URI("/api/2.0/licenses/11/"))
                        .type("instance")
                        .userMetric("")
                        .build()
        );
    }

    public void testEmptyJsonArrayToEmptyList(){
        Assert.assertEquals(PARSE_LICENSE_LIST.apply(HttpResponse.builder()
                                                                .message("OK")
                                                                .statusCode(200)
                                                                .payload(EMPTY_OBJECTS_ARRAY)
                                                                .build())
        , ImmutableList.of());
    }

    public void test(){
        Assert.assertEquals(PARSE_LICENSE_LIST.apply(HttpResponse.builder()
                                                                .message("OK")
                                                                .statusCode(200)
                                                                .payload(getJsonResource("/licences"))
                                                                .build())
        , expected);
    }
}
