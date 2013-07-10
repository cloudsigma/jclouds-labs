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

import java.math.BigInteger;
import java.net.URI;
import java.util.List;

/**
 * @author Vladimir Shevchenko
 */
public class RawDrive {
    public RawOwner owner;
    public URI resource_uri;
    public String status;
    public String uuid;

    public List<String> affinities;
    public Boolean allow_multimount;
    public List<String> jobs;
    public List<RawDriveLicense> licenses;
    public String media;
    public List<RawServer> mounted_on;
    public String name;
    public BigInteger size;
    public List<String> tags;
    public Object meta;

    public class RawDriveLicense{
        public Integer amount;
        public RawLicense license;
        public RawOwner user;
    }

    public class RawLicense {
        public Boolean burstable;
        public String long_name;
        public String name;
        public String resource_uri;
        public String type;
        public String user_metric;
    }
}
