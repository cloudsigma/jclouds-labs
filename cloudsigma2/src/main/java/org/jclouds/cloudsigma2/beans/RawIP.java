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
import java.util.Map;

/**
 * @author Vladimir Shevchenko
 */
public class RawIP {
    public RawOwner owner;
    public String uuid;
    public RawServer server;
    public String resource_uri;
    public Map<String, String> meta;
    public RawSubscription subscription;
    public List<RawTag> tags;
    public List<String> nameservers;
    public Integer netmask;
    public String gateway;
}
