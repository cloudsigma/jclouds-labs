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
import org.jclouds.cloudsigma2.beans.RawCurrentUsage;
import org.jclouds.cloudsigma2.domain.AccountUsage;
import org.jclouds.cloudsigma2.domain.CurrentUsage;
import org.jclouds.cloudsigma2.domain.Usage;
import org.jclouds.javax.annotation.Nullable;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Vladimir Shevchenko
 */
@Singleton
public class JsonToCurrentUsage implements Function<RawCurrentUsage, CurrentUsage> {

    private final JsonToAccountBalance jsonToAccountBalance;

    @Inject
    public JsonToCurrentUsage(JsonToAccountBalance jsonToAccountBalance) {
        this.jsonToAccountBalance = jsonToAccountBalance;
    }

    @Override
    public CurrentUsage apply(@Nullable RawCurrentUsage input) {
        if(input == null){
            return null;
        }

        CurrentUsage.Builder currentUsageBuilder = new CurrentUsage.Builder();

        if(input.balance != null){
            currentUsageBuilder.balance(jsonToAccountBalance.apply(input.balance));
        }

        if(input.usage != null){
            AccountUsage.Builder accountUsageBuilder = new AccountUsage.Builder();

            if(input.usage.cpu != null){
                accountUsageBuilder.cpu(parseUsage(input.usage.cpu));
            }

            if(input.usage.dssd != null){
                accountUsageBuilder.dssd(parseUsage(input.usage.dssd));
            }

            if(input.usage.ip != null){
                accountUsageBuilder.ip(parseUsage(input.usage.ip));
            }

            if(input.usage.mem != null){
                accountUsageBuilder.mem(parseUsage(input.usage.mem));
            }

            if(input.usage.msft_lwa_00135 != null){
                accountUsageBuilder.windowsWebServer2008(parseUsage(input.usage.msft_lwa_00135));
            }

            if(input.usage.msft_p73_04837 != null){
                accountUsageBuilder.windowsServer2008Standard(parseUsage(input.usage.msft_p73_04837));
            }

            if(input.usage.msft_tfa_00009 != null){
                accountUsageBuilder.sqlServerStandard2008(parseUsage(input.usage.msft_tfa_00009));
            }

            if(input.usage.sms != null){
                accountUsageBuilder.sms(parseUsage(input.usage.sms));
            }

            if(input.usage.ssd != null){
                accountUsageBuilder.ssd(parseUsage(input.usage.ssd));
            }

            if(input.usage.tx != null){
                accountUsageBuilder.tx(parseUsage(input.usage.tx));
            }

            if(input.usage.vlan != null){
                accountUsageBuilder.vlan(parseUsage(input.usage.vlan));
            }

            currentUsageBuilder.usage(accountUsageBuilder.build());
        }

        return currentUsageBuilder.build();
    }

    private Usage parseUsage(RawCurrentUsage.RawUsage rawUsage){
        Usage.Builder usageBuilder = new Usage.Builder();
        if(rawUsage.burst != null){
            usageBuilder.burst(rawUsage.burst);
        }

        if(rawUsage.subscribed != null){
            usageBuilder.subscribed(rawUsage.subscribed);
        }

        if(rawUsage.using != null){
            usageBuilder.using(rawUsage.using);
        }

        return usageBuilder.build();
    }
}
