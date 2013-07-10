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
package org.jclouds.cloudsigma2.binders;

import com.google.common.base.Function;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jclouds.cloudsigma2.domain.CreateSubscriptionRequest;
import org.jclouds.http.HttpRequest;
import org.jclouds.rest.Binder;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.core.MediaType;

/**
 * @author Vladimir Shevchenko
 */
@Singleton
public class BindCreateSubscriptionRequestList implements Binder {

    private final Function<CreateSubscriptionRequest, JsonObject> subscriptionRequestJsonObjectFunction;

    @Inject
    public BindCreateSubscriptionRequestList(Function<CreateSubscriptionRequest, JsonObject> subscriptionRequestJsonObjectFunction) {
        this.subscriptionRequestJsonObjectFunction = subscriptionRequestJsonObjectFunction;
    }

    @Override
    public <R extends HttpRequest> R bindToRequest(R request, Object input) {
        Iterable<CreateSubscriptionRequest> createSubscriptionRequests = (Iterable<CreateSubscriptionRequest>) input;
        JsonArray subscriptionsJsonArray = new JsonArray();

        for(CreateSubscriptionRequest createSubscriptionRequest : createSubscriptionRequests){
            JsonObject sunbscriptionRequestObject = subscriptionRequestJsonObjectFunction.apply(createSubscriptionRequest);
            subscriptionsJsonArray.add(sunbscriptionRequestObject);
        }

        JsonObject json = new JsonObject();
        json.add("objects", subscriptionsJsonArray);

        request.setPayload(json.toString());
        request.getPayload().getContentMetadata().setContentType(MediaType.APPLICATION_JSON);
        return request;
    }
}
