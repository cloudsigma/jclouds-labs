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
public class RawSubscription {
    public String amount;
    public Boolean auto_renew;
    public List<RawSubscription> descendants;
    public Double discount_amount;
    public Double discount_percent;
    public String end_time;
    public String id;
    public String last_notification;
    public String period;
    public Double price;
    public String remaining;
    public String resource;
    public String resource_uri;
    public String start_time;
    public String status;
    public String subscribed_object;
    public String uuid;
}
