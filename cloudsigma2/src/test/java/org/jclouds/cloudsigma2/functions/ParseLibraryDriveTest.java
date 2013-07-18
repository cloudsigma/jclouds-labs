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
import org.jclouds.cloudsigma2.domain.*;
import org.jclouds.http.HttpResponse;
import org.jclouds.json.config.GsonModule;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Vladimir Shevchenko
 */
@Test(groups = "unit")
public class ParseLibraryDriveTest extends CloudSigma2ParserTest{

    private static final ParseLibraryDrive PARSE_LIBRARY_DRIVE = Guice.createInjector(new GsonModule()).getInstance(ParseLibraryDrive.class);

    private LibraryDrive expected;
    {
        try {
            expected = new LibraryDrive.Builder()
                    .affinities(new ArrayList<String>())
                    .allowMultimount(false)
                    .arch("32")
                    .category(ImmutableList.of("general"))
                    .description("test_description")
                    .isFavorite(true)
                    .imageType("install")
                    .installNotes("test_install_notes")
                    .jobs(new ArrayList<String>())
                    .licenses(new ArrayList<DriveLicense>())
                    .media(MediaType.CDROM)
                    .meta(new HashMap<String, String>())
                    .mountedOn(new ArrayList<Server>())
                    .name("Vyatta-6.5-32bit-Virtualization-ISO")
                    .os("linux")
                    .owner(null)
                    .isPaid(false)
                    .resourceUri(new URI("/api/2.0/libdrives/6d53b92c-42dc-472b-a7b6-7021f45f377a/"))
                    .size(new BigInteger("1000000000"))
                    .status(DriveStatus.MOUNTED)
                    .tags(new ArrayList<String>())
                    .url("http://www.vyatta.org/")
                    .uuid("6d53b92c-42dc-472b-a7b6-7021f45f377a")
                    .build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void test(){
        Assert.assertEquals(PARSE_LIBRARY_DRIVE.apply(HttpResponse.builder()
                                                            .statusCode(200)
                                                            .message("OK")
                                                            .payload(getJsonResource("/libdrives-single"))
                                                            .build())
                , expected);
    }
}
