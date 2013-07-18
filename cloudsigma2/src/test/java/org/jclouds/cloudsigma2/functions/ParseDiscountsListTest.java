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
import org.jclouds.cloudsigma2.domain.Discount;
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
public class ParseDiscountsListTest extends CloudSigma2ParserTest{

    private static final ParseDiscountsList PARSE_DISCOUNTS_LIST = Guice.createInjector(new GsonModule()).getInstance(ParseDiscountsList.class);

    private List<Discount> expected;

    @BeforeMethod
    public void setUp() throws Exception {
        expected = ImmutableList.of(
                new Discount("3 months", 0.03)
                , new Discount("6 months", 0.1)
                , new Discount("1 year", 0.25)
                , new Discount("2 years", 0.35)
                , new Discount("3 years", 0.45)
        );
    }

    public void testEmptyJsonArrayTOEmptyList(){
        Assert.assertEquals(PARSE_DISCOUNTS_LIST.apply(HttpResponse.builder()
                                                .statusCode(200)
                                                .message("OK")
                                                .payload(EMPTY_OBJECTS_ARRAY)
                                                .build())
        , new ArrayList<Discount>());
    }

    public void test(){
        Assert.assertEquals(PARSE_DISCOUNTS_LIST.apply(HttpResponse.builder()
                                                .statusCode(200)
                                                .message("OK")
                                                .payload(getJsonResource("/discount"))
                                                .build())
        , expected);
    }
}
