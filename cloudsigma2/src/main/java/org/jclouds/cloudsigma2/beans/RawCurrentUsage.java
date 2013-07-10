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

/**
 * @author Vladimir Shevchenko
 */
public class RawCurrentUsage {
    public RawAccountBalance balance;
    public RawAccountUsage usage;

    public class RawAccountUsage {
        public RawUsage cpu;
        public RawUsage dssd;
        public RawUsage ip;
        public RawUsage mem;
        public RawUsage msft_lwa_00135;
        public RawUsage msft_p73_04837;
        public RawUsage msft_tfa_00009;
        public RawUsage sms;
        public RawUsage ssd;
        public RawUsage tx;
        public RawUsage vlan;
    }

    public class RawUsage{
        public BigInteger burst;
        public BigInteger subscribed;
        public BigInteger using;
    }
}
