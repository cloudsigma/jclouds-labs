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
import org.jclouds.cloudsigma2.beans.RawOwner;
import org.jclouds.cloudsigma2.domain.Drive;
import org.jclouds.cloudsigma2.domain.DriveStatus;
import org.jclouds.cloudsigma2.domain.Owner;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Vladimir Shevchenko
 */
@Test(groups = "unit")
public class JsonToDriveTest extends CloudSigma2ParserTest{

    private static final JsonToDrive JSON_TO_DRIVE = Guice.createInjector().getInstance(JsonToDrive.class);

    private static RawDrive rawDrive = new RawDrive();
    private static RawOwner rawOwner = new RawOwner();
    private static Drive result;

    @BeforeMethod
    public void setUp() throws Exception {
        rawOwner.uuid = "5b4a69a3-8e78-4c45-a8ba-8b13f0895e23";
        rawOwner.resource_uri = "/api/2.0/user/5b4a69a3-8e78-4c45-a8ba-8b13f0895e23/";

        rawDrive.owner = rawOwner;
        rawDrive.resource_uri = "/api/2.0/drives/e96f3c63-6f50-47eb-9401-a56c5ccf6b32/";
        rawDrive.status = "unmounted";
        rawDrive.uuid = "e96f3c63-6f50-47eb-9401-a56c5ccf6b32";

        Owner owner = new Owner.Builder()
                .resourceUri(new URI("/api/2.0/user/5b4a69a3-8e78-4c45-a8ba-8b13f0895e23/"))
                .uuid("5b4a69a3-8e78-4c45-a8ba-8b13f0895e23")
                .build();

        try {
            result = new Drive.Builder()
                    .owner(owner)
                    .resourceUri(new URI("/api/2.0/drives/e96f3c63-6f50-47eb-9401-a56c5ccf6b32/"))
                    .status(DriveStatus.UNMOUNTED)
                    .uuid("e96f3c63-6f50-47eb-9401-a56c5ccf6b32")
                    .build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void test(){
        Assert.assertEquals(JSON_TO_DRIVE.apply(rawDrive), result);
    }
}
