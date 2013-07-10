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
package org.jclouds.cloudsigma2.beans;

import java.util.List;

/**
 * @author Vladimir Shevchenko
 */
public class RawServerInfo extends RawServer {
    public Integer cpu;
    public Boolean cpus_nstead_of_cores;
    public List<RawServerDrive> drives;
    public Boolean enable_numa;
    public Boolean hv_relaxed;
    public Boolean hv_tsc;
    public Long mem;
    public Object meta;
    public List<RawNIC> nics;
    public List<String> requirements;
    public List<String> tags;
    public String vnc_password;
    public Integer smp;

    public class RawNIC{
        public Integer boot_order;
        public RawFirewallPolicy firewall_policy;
        public RawIPConf ip_v4_conf;
        public RawIPConf ip_v6_conf;
        public String mac;
        public String model;
        public RawNicRuntime runtime;
        public RawVLAN vlan;
    }

    public class RawIPConf {
        public String conf;
        public RawIP ip;
    }

    public class RawNicRuntime{
        public String interface_type;
        public RawNICRuntimeIOStats io;
        public String ip_v4;
        public String ip_v6;
    }

    public class RawNICRuntimeIOStats{
        public String bytes_recv;
        public String bytes_sent;
        public String packets_recv;
        public String packets_sent;
    }

    public class RawServerDrive{
        public Integer boot_order;
        public String dev_channel;
        public String device;
        public RawDrive drive;
    }
}
