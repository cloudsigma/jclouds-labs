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
import org.jclouds.cloudsigma2.beans.RawPricing;
import org.jclouds.cloudsigma2.domain.BurstLevel;
import org.jclouds.cloudsigma2.domain.Price;
import org.jclouds.cloudsigma2.domain.Pricing;
import org.jclouds.cloudsigma2.domain.SubscriptionResource;
import org.jclouds.javax.annotation.Nullable;

import javax.inject.Singleton;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Shevchenko
 */
@Singleton
public class JsonToPricing implements Function<RawPricing, Pricing> {
    @Override
    public Pricing apply(@Nullable RawPricing input) {
        if(input == null){
            return null;
        }

        Pricing.Builder pricingBuilder = new Pricing.Builder();

        if(input.current != null){
            pricingBuilder.current(parseBurstLevel(input.current));
        }

        if(input.next != null){
            pricingBuilder.next(parseBurstLevel(input.next));
        }

        if(input.objects != null){
            List<Price> priceList = new ArrayList<Price>();

            for(RawPricing.RawPrice price : input.objects){
                Price.Builder priceBuilder = new Price.Builder();

                if(price.currency != null){
                    priceBuilder.currency(price.currency);
                }

                if(price.id != null){
                    priceBuilder.id(price.id);
                }

                if(price.level != null){
                    priceBuilder.level(price.level);
                }

                if(price.multiplier != null){
                    priceBuilder.multiplier(new BigInteger("" + price.multiplier));
                }

                if(price.price != null){
                    priceBuilder.price(price.price);
                }

                if(price.resource != null){
                    priceBuilder.resource(SubscriptionResource.fromValue(price.resource));
                }

                if(price.unit != null){
                    priceBuilder.unit(price.unit);
                }

                priceList.add(priceBuilder.build());
            }

            pricingBuilder.priceList(priceList);
        }

        return pricingBuilder.build();
    }

    private BurstLevel parseBurstLevel(RawPricing.RawBurstLevel rawBurstLevel){
        BurstLevel.Builder burstBuilder = new BurstLevel.Builder();

        if(rawBurstLevel.cpu != null){
            burstBuilder.cpu(rawBurstLevel.cpu);
        }

        if(rawBurstLevel.dssd != null){
            burstBuilder.dssd(rawBurstLevel.dssd);
        }

        if(rawBurstLevel.ip != null){
            burstBuilder.ip(rawBurstLevel.ip);
        }

        if(rawBurstLevel.mem != null){
            burstBuilder.mem(rawBurstLevel.mem);
        }

        if(rawBurstLevel.msft_lwa_00135 != null){
            burstBuilder.windowsWebServer2008(rawBurstLevel.msft_lwa_00135);
        }

        if(rawBurstLevel.msft_p73_04837 != null){
            burstBuilder.windowsServer2008Standard(rawBurstLevel.msft_p73_04837);
        }

        if(rawBurstLevel.msft_tfa_00009 != null){
            burstBuilder.sqlServerStandard2008(rawBurstLevel.msft_tfa_00009);
        }

        if(rawBurstLevel.sms != null){
            burstBuilder.sms(rawBurstLevel.sms);
        }

        if(rawBurstLevel.ssd != null){
            burstBuilder.ssd(rawBurstLevel.ssd);
        }

        if(rawBurstLevel.tx != null){
            burstBuilder.tx(rawBurstLevel.tx);
        }

        if(rawBurstLevel.vlan != null){
            burstBuilder.vlan(rawBurstLevel.vlan);
        }

        return burstBuilder.build();
    }
}
