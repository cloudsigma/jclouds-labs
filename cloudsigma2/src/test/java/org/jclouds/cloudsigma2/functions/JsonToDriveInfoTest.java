package org.jclouds.cloudsigma2.functions;

import com.google.common.collect.ImmutableList;
import com.google.inject.Guice;
import org.jclouds.cloudsigma2.beans.RawDrive;
import org.jclouds.cloudsigma2.beans.RawOwner;
import org.jclouds.cloudsigma2.beans.RawServer;
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
import org.jclouds.cloudsigma2.domain.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigInteger;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Vladimir Shevchenko
 */
@Test(groups = "unit")
public class JsonToDriveInfoTest {
    private static final JsonToDriveInfo JSON_TO_DRIVE_INFO = Guice.createInjector().getInstance(JsonToDriveInfo.class);

    private static RawDrive rawDrive = new RawDrive();
    private static DriveInfo result;

    @BeforeMethod
    public void setUp() throws Exception {
        Owner owner = new Owner.Builder()
                .resourceUri(new URI("/api/2.0/user/5b4a69a3-8e78-4c45-a8ba-8b13f0895e23/"))
                .uuid("5b4a69a3-8e78-4c45-a8ba-8b13f0895e23")
                .build();

        Map<String, String> metaMap = new HashMap<String, String>();
        metaMap.put("description", "");
        metaMap.put("install_notes", "");

        RawOwner rawOwner = new RawOwner();
        rawOwner.uuid = "5b4a69a3-8e78-4c45-a8ba-8b13f0895e23";
        rawOwner.resource_uri = "/api/2.0/user/5b4a69a3-8e78-4c45-a8ba-8b13f0895e23/";

        RawServer rawServer1 = new RawServer();
        rawServer1.uuid = "81f911f9-5152-4328-8671-02543bafbd0e";
        rawServer1.resource_uri = "/api/2.0/servers/81f911f9-5152-4328-8671-02543bafbd0e/";

        RawServer rawServer2 = new RawServer();
        rawServer2.uuid = "19163e1a-a6d6-4e73-8087-157dd302c373";
        rawServer2.resource_uri = "/api/2.0/servers/19163e1a-a6d6-4e73-8087-157dd302c373/";

        RawDrive.RawLicense rawLicense = rawDrive.new RawLicense();
        rawLicense.burstable = true;
        rawLicense.long_name = "sample_longname";
        rawLicense.name = "sample_name";
        rawLicense.resource_uri = "/api/2.0/samples/";
        rawLicense.type = "sample_type";
        rawLicense.user_metric = "sample";

        RawDrive.RawDriveLicense rawDriveLicense = rawDrive.new RawDriveLicense();
        rawDriveLicense.amount = 1;
        rawDriveLicense.user = rawOwner;
        rawDriveLicense.license = rawLicense;

        rawDrive.owner = rawOwner;
        rawDrive.resource_uri = "/api/2.0/drives/e96f3c63-6f50-47eb-9401-a56c5ccf6b32/";
        rawDrive.status = "unmounted";
        rawDrive.uuid = "e96f3c63-6f50-47eb-9401-a56c5ccf6b32";
        rawDrive.affinities = ImmutableList.of("ssd", "sample");
        rawDrive.allow_multimount = true;
        rawDrive.jobs = new ArrayList<String>();
        rawDrive.licenses = ImmutableList.of(rawDriveLicense);
        rawDrive.media = "disk";
        rawDrive.mounted_on = ImmutableList.of(rawServer1, rawServer2);
        rawDrive.name = "test_drive_y";
        rawDrive.size = new BigInteger("1024000000");
        rawDrive.tags = ImmutableList.of("tag_uuid_1", "tag_uuid_2");
        rawDrive.meta = metaMap;

        result = new DriveInfo.Builder()
                .affinities(ImmutableList.of("ssd", "sample"))
                .allowMultimount(true)
                .jobs(new ArrayList<String>())
                .licenses(ImmutableList.of(new DriveLicense.Builder()
                        .amount(1)
                        .license(new License.Builder()
                                .isBurstable(true)
                                .longName("sample_longname")
                                .name("sample_name")
                                .resourceUri(new URI("/api/2.0/samples/"))
                                .type("sample_type")
                                .userMetric("sample")
                                .build())
                        .user(owner)
                        .build()))
                .media(MediaType.DISK)
                .meta(metaMap)
                .mountedOn(ImmutableList.of(new Server.Builder()
                        .uuid("81f911f9-5152-4328-8671-02543bafbd0e")
                        .resourceUri(new URI("/api/2.0/servers/81f911f9-5152-4328-8671-02543bafbd0e/"))
                        .build(),
                        new Server.Builder()
                                .uuid("19163e1a-a6d6-4e73-8087-157dd302c373")
                                .resourceUri(new URI("/api/2.0/servers/19163e1a-a6d6-4e73-8087-157dd302c373/"))
                                .build()))
                .name("test_drive_y")
                .owner(owner)
                .resourceUri(new URI("/api/2.0/drives/e96f3c63-6f50-47eb-9401-a56c5ccf6b32/"))
                .size(new BigInteger("1024000000"))
                .status(DriveStatus.UNMOUNTED)
                .tags(ImmutableList.of("tag_uuid_1", "tag_uuid_2"))
                .uuid("e96f3c63-6f50-47eb-9401-a56c5ccf6b32")
                .build();
    }

    public void test(){
        Assert.assertEquals(JSON_TO_DRIVE_INFO.apply(rawDrive), result);
    }
}
