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
import org.jclouds.cloudsigma2.beans.RawSubscription;
import org.jclouds.cloudsigma2.domain.Subscription;
import org.jclouds.cloudsigma2.domain.SubscriptionResource;
import org.jclouds.date.internal.SimpleDateFormatDateService;
import org.jclouds.javax.annotation.Nullable;

import javax.inject.Singleton;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Shevchenko
 */
@Singleton
public class JsonToSubscription implements Function<RawSubscription, Subscription>{

    @Override
    public Subscription apply(@Nullable RawSubscription input) {
        if(input == null){
            return null;
        }

        Subscription.Builder subscriptionBuilder = new Subscription.Builder();

        if(input.amount != null){
            subscriptionBuilder.amount(input.amount);
        }

        if(input.auto_renew != null){
            subscriptionBuilder.isAutoRenewEnabled(input.auto_renew);
        }

        if(input.descendants != null){
            List<Subscription> subscriptionList = new ArrayList<Subscription>();

            for(RawSubscription subscription : input.descendants){
                subscriptionList.add(apply(subscription));
            }

            subscriptionBuilder.descendants(subscriptionList);
        }

        if(input.discount_amount != null){
            subscriptionBuilder.discountAmount(input.discount_amount);
        }

        if(input.discount_percent != null){
            subscriptionBuilder.discountPercent(input.discount_percent);
        }

        if(input.end_time != null){
            subscriptionBuilder.endTime(new SimpleDateFormatDateService().iso8601SecondsDateParse(input.end_time));
        }

        if(input.id != null){
            subscriptionBuilder.id(input.id);
        }

        if(input.last_notification != null){
            subscriptionBuilder.lastNotification(new SimpleDateFormatDateService().iso8601SecondsDateParse(input.last_notification));
        }

        if(input.period != null){
            subscriptionBuilder.period(input.period);
        }

        if(input.price != null){
            subscriptionBuilder.price(input.price);
        }

        if(input.remaining != null){
            subscriptionBuilder.remaining(input.remaining);
        }

        if(input.resource != null){
            subscriptionBuilder.resource(SubscriptionResource.fromValue(input.resource));
        }

        if(input.resource_uri != null){
            try {
                subscriptionBuilder.resourceUri(new URI(input.resource_uri));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        if(input.start_time != null){
            subscriptionBuilder.startTime(new SimpleDateFormatDateService().iso8601SecondsDateParse(input.start_time));
        }

        if(input.status != null){
            subscriptionBuilder.status(input.status);
        }

        if(input.subscribed_object != null){
            subscriptionBuilder.subscribedObject(input.subscribed_object);
        }

        if(input.uuid != null){
            subscriptionBuilder.uuid(input.uuid);
        }

        return subscriptionBuilder.build();
    }
}
