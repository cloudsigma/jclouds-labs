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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Vladimir Shevchenko
 */
@Test(groups = "unit")
public class ParseLibraryDrivesListTest extends CloudSigma2ParserTest{

    private static final ParseLibraryDrivesList PARSE_LIBRARY_DRIVES_LIST = Guice.createInjector(new GsonModule()).getInstance(ParseLibraryDrivesList.class);

    private List<LibraryDrive> resultList;

    @BeforeMethod
    public void setUp() throws Exception {
        Owner owner = new Owner.Builder()
                .resourceUri(new URI("/api/2.0/user/5b4a69a3-8e78-4c45-a8ba-8b13f0895e23/"))
                .uuid("5b4a69a3-8e78-4c45-a8ba-8b13f0895e23")
                .build();

        try {
            resultList = ImmutableList.of(
                    new LibraryDrive.Builder()
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
                            .name("ZeroShell 1.3 Linux Install CD")
                            .os("linux")
                            .owner(owner)
                            .isPaid(false)
                            .resourceUri(new URI("/api/2.0/libdrives/8c45d8d9-4efd-44ec-9833-8d52004b4298/"))
                            .size(new BigInteger("1000000000"))
                            .status(DriveStatus.UNMOUNTED)
                            .tags(new ArrayList<String>())
                            .url("test_url")
                            .uuid("8c45d8d9-4efd-44ec-9833-8d52004b4298")
                            .build(),

                    new LibraryDrive.Builder()
                            .affinities(new ArrayList<String>())
                            .allowMultimount(false)
                            .arch("64")
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
                            .name("Windows_Server_2003_r2_standard_x64_with_sp2_cd2.iso pub")
                            .os("windows")
                            .owner(null)
                            .isPaid(false)
                            .resourceUri(new URI("/api/2.0/libdrives/d1ec9f26-ba44-4002-bbdf-82a31a84b611/"))
                            .size(new BigInteger("1000000000"))
                            .status(DriveStatus.UNMOUNTED)
                            .tags(new ArrayList<String>())
                            .url("test_url")
                            .uuid("d1ec9f26-ba44-4002-bbdf-82a31a84b611")
                            .build(),

                    new LibraryDrive.Builder()
                            .affinities(new ArrayList<String>())
                            .allowMultimount(false)
                            .arch("64")
                            .category(ImmutableList.of("general"))
                            .description("")
                            .isFavorite(true)
                            .imageType("install")
                            .installNotes("")
                            .jobs(new ArrayList<String>())
                            .licenses(new ArrayList<DriveLicense>())
                            .media(MediaType.CDROM)
                            .meta(new HashMap<String, String>())
                            .mountedOn(new ArrayList<Server>())
                            .name("Windows_Server_2003_r2_standard_x64_with_sp2_cd1.iso pub")
                            .os("windows")
                            .owner(null)
                            .isPaid(false)
                            .resourceUri(new URI("/api/2.0/libdrives/dd9da460-b1ab-419a-9fa1-804540eee4c3/"))
                            .size(new BigInteger("1000000000"))
                            .status(DriveStatus.UNMOUNTED)
                            .tags(new ArrayList<String>())
                            .url("test_url")
                            .uuid("dd9da460-b1ab-419a-9fa1-804540eee4c3")
                            .build()
            );
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void testEmptyJsonArrayToResturnEmptyList(){
        Assert.assertEquals(PARSE_LIBRARY_DRIVES_LIST.apply(HttpResponse.builder()
                .statusCode(200)
                .message("OK")
                .payload(EMPTY_OBJECTS_ARRAY)
                .build()), new ArrayList<LibraryDrive>());
    }

    public void test(){
        Assert.assertEquals(PARSE_LIBRARY_DRIVES_LIST.apply(HttpResponse.builder()
        .statusCode(200)
        .message("OK")
        .payload(getJsonResource("/libdrives.json"))
        .build()), resultList);
    }
}
