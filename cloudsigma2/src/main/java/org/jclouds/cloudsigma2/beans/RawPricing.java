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
import java.util.List;

/**
 * @author Vladimir Shevchenko
 */
public class RawPricing {
    public RawBurstLevel current;
    public RawBurstLevel next;
    public List<RawPrice> objects;

    public class RawBurstLevel{
        public Integer cpu;
        public Integer dssd;
        public Integer ip;
        public Integer mem;
        public Integer msft_lwa_00135;
        public Integer msft_p73_04837;
        public Integer msft_tfa_00009;
        public Integer sms;
        public Integer ssd;
        public Integer tx;
        public Integer vlan;
    }


    public class RawPrice{
        public String currency;
        public String id;
        public Integer level;
        public BigInteger multiplier;
        public Double price;
        public String resource;
        public String unit;
    }
}
