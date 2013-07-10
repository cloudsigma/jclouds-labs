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

import com.google.common.base.Function;
import org.jclouds.cloudsigma2.beans.RawServerInfo;
import org.jclouds.cloudsigma2.domain.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Shevchenko
 */
@Singleton
public class JsonToServerInfo implements Function<RawServerInfo, ServerInfo> {

    private final JsonToServer jsonToServer;
    private final JsonToDrive jsonToDrive;
    private final JsonToVLANInfo jsonToVLAN;
    private final JsonToIPInfo jsonToIPInfo;
    private final JsonToFirewallPolicy jsonToFirewallPolicy;

    @Inject
    public JsonToServerInfo(JsonToServer jsonToServer, JsonToDrive jsonToDrive, JsonToVLANInfo jsonToVLAN, JsonToIPInfo jsonToIPInfo, JsonToFirewallPolicy jsonToFirewallPolicy) {
        this.jsonToServer = jsonToServer;
        this.jsonToDrive = jsonToDrive;
        this.jsonToVLAN = jsonToVLAN;
        this.jsonToIPInfo = jsonToIPInfo;
        this.jsonToFirewallPolicy = jsonToFirewallPolicy;
    }

    @Override
    public ServerInfo apply(RawServerInfo input) {
        ServerInfo.Builder serverInfoBuilder = new ServerInfo.Builder().fromServer(jsonToServer.apply(input));

        if(input.cpu != null){
            serverInfoBuilder.cpu(input.cpu.intValue());
        }

        if(input.cpus_nstead_of_cores != null){
            serverInfoBuilder.cpusInsteadOfCores(input.cpus_nstead_of_cores);
        }

        if(input.drives != null){
            List<ServerDrive> serverDrives = new ArrayList<ServerDrive>();

            for(RawServerInfo.RawServerDrive rawServerDrive : input.drives){
                ServerDrive.Builder serverDriveBuilder = new ServerDrive.Builder();

                if(rawServerDrive.boot_order != null){
                    serverDriveBuilder.bootOrder(rawServerDrive.boot_order);
                }

                if(rawServerDrive.dev_channel != null){
                    serverDriveBuilder.deviceChannel(rawServerDrive.dev_channel);
                }

                if(rawServerDrive.device != null){
                    serverDriveBuilder.deviceEmulationType(DeviceEmulationType.fromValue(rawServerDrive.device));
                }

                if(rawServerDrive.drive != null){
                    serverDriveBuilder.drive(jsonToDrive.apply(rawServerDrive.drive));
                }

                serverDrives.add(serverDriveBuilder.build());
            }
            serverInfoBuilder.drives(serverDrives);
        }

        if(input.enable_numa != null){
            serverInfoBuilder.enableNuma(input.enable_numa);
        }

        if(input.hv_relaxed != null){
            serverInfoBuilder.hvRelaxed(input.hv_relaxed);
        }

        if(input.hv_tsc != null){
            serverInfoBuilder.hvTsc(input.hv_tsc);
        }

        if(input.mem != null){
            serverInfoBuilder.memory(new BigInteger("" + input.mem));
        }

        if(input.meta != null){
            serverInfoBuilder.meta(input.meta);
        }

        if(input.requirements != null){
            serverInfoBuilder.requirements(input.requirements);
        }

        if(input.tags != null){
            serverInfoBuilder.tags(input.tags);
        }

        if(input.vnc_password != null){
            serverInfoBuilder.vncPassword(input.vnc_password);
        }

        if(input.smp != null){
            serverInfoBuilder.smp(input.smp);
        }

        if(input.nics != null){
            List<NIC> nics = new ArrayList<NIC>();

            for(RawServerInfo.RawNIC rawNIC : input.nics){
                NIC.Builder nicBuilder = new NIC.Builder();

                if(rawNIC.boot_order != null){
                    nicBuilder.bootOrder(rawNIC.boot_order);
                }

                if(rawNIC.firewall_policy != null){
                    nicBuilder.firewallPolicy(jsonToFirewallPolicy.apply(rawNIC.firewall_policy));
                }

                if(rawNIC.mac != null){
                    nicBuilder.mac(rawNIC.mac);
                }

                if(rawNIC.model != null){
                    nicBuilder.model(Model.fromValue(rawNIC.model));
                }

                if(rawNIC.vlan != null){
                    nicBuilder.vlan(jsonToVLAN.apply(rawNIC.vlan));
                }

                if(rawNIC.runtime != null){
                    IOStats ioStats = new IOStats(rawNIC.runtime.io.bytes_recv
                                                , rawNIC.runtime.io.bytes_sent
                                                , rawNIC.runtime.io.packets_recv
                                                , rawNIC.runtime.io.packets_sent);
                    NICStats nicStats = new NICStats(InterfaceType.fromValue(rawNIC.runtime.interface_type)
                                                    , ioStats
                                                    , rawNIC.runtime.ip_v4
                                                    , rawNIC.runtime.ip_v6
                                                    , null);
                    nicBuilder.runtime(nicStats);
                }

                if(rawNIC.ip_v4_conf != null){
                    IPConfiguration.Builder ipConfBuilder = new IPConfiguration.Builder();

                    if(rawNIC.ip_v4_conf.conf != null){
                        ipConfBuilder.configurationType(IPConfigurationType.fromValue(rawNIC.ip_v4_conf.conf));
                    }

                    if(rawNIC.ip_v4_conf.ip != null){
                        ipConfBuilder.ip(jsonToIPInfo.apply(rawNIC.ip_v4_conf.ip));
                    }

                    nicBuilder.ipV4Configuration(ipConfBuilder.build());
                }

                if(rawNIC.ip_v6_conf != null){
                    IPConfiguration.Builder ipConfBuilder = new IPConfiguration.Builder();

                    if(rawNIC.ip_v6_conf.conf != null){
                        ipConfBuilder.configurationType(IPConfigurationType.fromValue(rawNIC.ip_v6_conf.conf));
                    }

                    if(rawNIC.ip_v6_conf.ip != null){
                        ipConfBuilder.ip(jsonToIPInfo.apply(rawNIC.ip_v6_conf.ip));
                    }

                    nicBuilder.ipV6Configuration(ipConfBuilder.build());
                }

                nics.add(nicBuilder.build());
            }

            serverInfoBuilder.nics(nics);
        }

        return serverInfoBuilder.build();
    }
}
