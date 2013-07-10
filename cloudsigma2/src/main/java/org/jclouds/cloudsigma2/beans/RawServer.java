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

import java.net.URI;

/**
 * @author Vladimir Shevchenko
 */
public class RawServer {
    public String name;
    public RawOwner owner;
    public URI resource_uri;
    public String status;
    public String uuid;
    public RawServerRuntime runtime;

    public class RawServerRuntime {
        public String active_since;
        public Iterable<RawNICStats> nics;
    }

    public class RawNICStats {
        public String interface_type;
        public String ip_v4;
        public String ip_v6;
        public String mac;
        public RawIOStats io;
    }

    public class RawIOStats{
        public String bytes_recv;
        public String bytes_sent;
        public String packets_recv;
        public String packets_sent;
    }
}
