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
import org.jclouds.cloudsigma2.beans.RawDrive;
import org.jclouds.cloudsigma2.beans.RawOwner;
import org.jclouds.cloudsigma2.beans.RawServerInfo;
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
public class JsonToServerInfoTest {

    private static final JsonToServerInfo JSON_TO_SERVER_INFO = Guice.createInjector().getInstance(JsonToServerInfo.class);

    private RawServerInfo input;
    private Server expected;

    @BeforeMethod
    public void setUp() throws Exception {
        Owner owner = new Owner.Builder()
                .resourceUri(new URI("/api/2.0/user/5b4a69a3-8e78-4c45-a8ba-8b13f0895e23/"))
                .uuid("5b4a69a3-8e78-4c45-a8ba-8b13f0895e23")
                .build();

        Map<String, String> meta = new HashMap<String, String>();
        meta.put("description", "A full server with description");

        input = new RawServerInfo();

        RawOwner rawOwner = new RawOwner();
        rawOwner.resource_uri = "/api/2.0/user/5b4a69a3-8e78-4c45-a8ba-8b13f0895e23/";
        rawOwner.uuid = "5b4a69a3-8e78-4c45-a8ba-8b13f0895e23";

        RawDrive rawDrive1 = new RawDrive();
        rawDrive1.resource_uri = "/api/2.0/drives/ae78e68c-9daa-4471-8878-0bb87fa80260/";
        rawDrive1.uuid = "ae78e68c-9daa-4471-8878-0bb87fa80260";

        RawServerInfo.RawServerDrive rawServerDrive1 = input.new RawServerDrive();
        rawServerDrive1.boot_order = null;
        rawServerDrive1.dev_channel = "0:0";
        rawServerDrive1.device = "ide";
        rawServerDrive1.drive = rawDrive1;

        RawDrive rawDrive2 = new RawDrive();
        rawDrive2.resource_uri = "/api/2.0/drives/22826af4-d6c8-4d39-bd41-9cea86df2976/";
        rawDrive2.uuid = "22826af4-d6c8-4d39-bd41-9cea86df2976";

        RawServerInfo.RawServerDrive rawServerDrive2 = input.new RawServerDrive();
        rawServerDrive2.boot_order = 1;
        rawServerDrive2.dev_channel = "0:0";
        rawServerDrive2.device = "virtio";
        rawServerDrive2.drive = rawDrive2;

        RawServerInfo.RawIPConf rawIP4Conf = input.new RawIPConf();
        rawIP4Conf.conf = "dhcp";

        RawServerInfo.RawNIC rawNIC = input.new RawNIC();
        rawNIC.mac = "22:a7:a0:0d:43:48";
        rawNIC.model = "virtio";
        rawNIC.ip_v4_conf = rawIP4Conf;

        input.name = "test_acc_full_server";
        input.owner = rawOwner;
        input.resource_uri = "/api/2.0/servers/a19a425f-9e92-42f6-89fb-6361203071bb/";
        input.status = "stopped";
        input.uuid = "a19a425f-9e92-42f6-89fb-6361203071bb";
        input.status = "stopped";
        input.cpu = 1000;
        input.cpus_instead_of_cores = false;
        input.drives = ImmutableList.of(rawServerDrive1, rawServerDrive2);
        input.enable_numa = false;
        input.hv_relaxed = false;
        input.hv_tsc = false;
        input.hv_relaxed = false;
        input.mem = new BigInteger("268435456");
        input.meta = meta;
        input.nics = ImmutableList.of(rawNIC);
        input.requirements = new ArrayList<String>();
        input.tags = ImmutableList.of("tag_uuid_1", "tag_uuid_2");
        input.vnc_password = "tester";
        input.smp = 1;

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
        Assert.assertEquals(JSON_TO_SERVER_INFO.apply(input), expected);
    }
}
