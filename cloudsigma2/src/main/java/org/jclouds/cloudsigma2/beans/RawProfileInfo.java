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

import java.util.Map;

/**
 * @author Vladimir Shevchenko
 */
public class RawProfileInfo {
    public String address;
    public Boolean api_https_only;
    public String autotopup_amount;
    public String autotopup_threshold;
    public String bank_reference;
    public String company;
    public String country;
    public String currency;
    public String email;
    public String first_name;
    public Boolean has_autotopup;
    public Boolean invoicing;
    public Boolean key_auth;
    public String language;
    public String last_name;
    public Boolean mailing_list;
    public Map<String, String> meta;
    public String my_notes;
    public String nickname;
    public String phone;
    public String postcode;
    public String reseller;
    public String signup_time;
    public String state;
    public String tax_name;
    public Double tax_rate;
    public String title;
    public String town;
    public String uuid;
    public String vat;
}
