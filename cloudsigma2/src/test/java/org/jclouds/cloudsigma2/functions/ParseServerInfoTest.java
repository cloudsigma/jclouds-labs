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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Vladimir Shevchenko
 */
@Test(groups = "unit")
public class ParseServerInfoTest extends CloudSigma2ParserTest{

    private static final ParseServerInfo PARSE_SERVER_INFO = Guice.createInjector(new GsonModule()).getInstance(ParseServerInfo.class);

    private ServerInfo expected;

    @BeforeMethod
    public void setUp() throws Exception {
        Owner owner = new Owner.Builder()
                .resourceUri(new URI("/api/2.0/user/5b4a69a3-8e78-4c45-a8ba-8b13f0895e23/"))
                .uuid("5b4a69a3-8e78-4c45-a8ba-8b13f0895e23")
                .build();

        Map<String, String> meta = new HashMap<String, String>();
        meta.put("description", "A full server with description");

        expected = new ServerInfo.Builder()
                .cpu(1000)
                .cpusInsteadOfCores(false)
                .drives(ImmutableList.of(
                        new ServerDrive.Builder()
                                .deviceChannel("0:0")
                                .deviceEmulationType(DeviceEmulationType.IDE)
                                .drive(new Drive.Builder()
                                        .resourceUri(new URI("/api/2.0/drives/ae78e68c-9daa-4471-8878-0bb87fa80260/"))
                                        .uuid("ae78e68c-9daa-4471-8878-0bb87fa80260")
                                        .build())
                                .build()
                        , new ServerDrive.Builder()
                        .bootOrder(1)
                        .deviceChannel("0:0")
                        .deviceEmulationType(DeviceEmulationType.VIRTIO)
                        .drive(new Drive.Builder()
                                .resourceUri(new URI("/api/2.0/drives/22826af4-d6c8-4d39-bd41-9cea86df2976/"))
                                .uuid("22826af4-d6c8-4d39-bd41-9cea86df2976")
                                .build())
                        .build()
                ))
                .enableNuma(false)
                .hvRelaxed(false)
                .hvTsc(false)
                .memory(new BigInteger("268435456"))
                .meta(meta)
                .name("test_acc_full_server")
                .nics(ImmutableList.of(
                        new NIC.Builder()
                                .firewallPolicy(null)
                                .ipV4Configuration(new IPConfiguration(IPConfigurationType.DHCP, null))
                                .ipV6Configuration(null)
                                .mac("22:a7:a0:0d:43:48")
                                .model(Model.VIRTIO)
                                .runtime(null)
                                .vlan(null)
                                .build()
                ))
                .owner(owner)
                .requirements(new ArrayList<String>())
                .resourceUri(new URI("/api/2.0/servers/a19a425f-9e92-42f6-89fb-6361203071bb/"))
                .runtime(null)
                .smp(1)
                .status(ServerStatus.STOPPED)
                .tags(ImmutableList.of("tag_uuid_1", "tag_uuid_2"))
                .uuid("a19a425f-9e92-42f6-89fb-6361203071bb")
                .vncPassword("tester")
                .build();
    }

    public void test(){
        Assert.assertEquals(PARSE_SERVER_INFO.apply(HttpResponse.builder()
                .message("OK")
                .statusCode(200)
                .payload(getJsonResource("/servers-single.json"))
                .build())
        , expected);
    }
}
