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
import org.jclouds.cloudsigma2.beans.RawProfileInfo;
import org.jclouds.cloudsigma2.domain.ProfileInfo;
import org.jclouds.date.internal.SimpleDateFormatDateService;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Vladimir Shevchenko
 */
@Test(groups = "unit")
public class JsonToProfileInfoTest {

    private static final JsonToProfileInfo JSON_TO_PROFILE_INFO = Guice.createInjector().getInstance(JsonToProfileInfo.class);

    private RawProfileInfo input;
    private ProfileInfo expected;

    @BeforeMethod
    public void setUp() throws Exception {
        Map<String, String> meta = new HashMap<String, String>();
        meta.put("description", "profile info");

        expected = new ProfileInfo.Builder()
                .address("test_address")
                .isApiHttpsOnly(false)
                .autotopupAmount("0E-16")
                .autotopupThreshold("0E-16")
                .bankReference("jdoe123")
                .company("Newly Set Company Name")
                .country("GB")
                .currency("USD")
                .email("user@example.com")
                .firstName("John")
                .hasAutotopup(false)
                .invoicing(true)
                .isKeyAuth(false)
                .language("en-au")
                .lastName("Doe")
                .isMailingListEnabled(true)
                .meta(meta)
                .myNotes("test notes")
                .nickname("test nickname")
                .phone("123456789")
                .postcode("12345")
                .reseller("test reseller")
                .signupTime(new SimpleDateFormatDateService().iso8601SecondsDateParse("2013-05-28T11:57:01+00:00"))
                .state("REGULAR")
                .taxRate(3.14)
                .taxName("test tax_name")
                .title("test title")
                .town("test town")
                .uuid("6f670b3c-a2e6-433f-aeab-b976b1cdaf03")
                .vat("test vat")
                .build();

        input = new RawProfileInfo();

        input.address = "test_address";
        input.api_https_only = false;
        input.autotopup_amount = "0E-16";
        input.autotopup_threshold = "0E-16";
        input.bank_reference = "jdoe123";
        input.company = "Newly Set Company Name";
        input.country = "GB";
        input.currency = "USD";
        input.email = "user@example.com";
        input.first_name = "John";
        input.has_autotopup = false;
        input.invoicing = true;
        input.key_auth = false;
        input.language = "en-au";
        input.last_name = "Doe";
        input.mailing_list = true;
        input.meta = meta;
        input.my_notes = "test notes";
        input.nickname = "test nickname";
        input.phone = "123456789";
        input.postcode = "12345";
        input.reseller = "test reseller";
        input.signup_time = "2013-05-28T11:57:01+00:00";
        input.state = "REGULAR";
        input.tax_name = "test tax_name";
        input.tax_rate = 3.14;
        input.title = "test title";
        input.town = "test town";
        input.uuid = "6f670b3c-a2e6-433f-aeab-b976b1cdaf03";
        input.vat = "test vat";
    }

    public void test(){
        Assert.assertEquals(JSON_TO_PROFILE_INFO.apply(input), expected);
    }
}
