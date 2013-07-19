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
package org.jclouds.cloudsigma2.config;

import com.google.common.base.Function;
import com.google.gson.JsonObject;
import com.google.inject.TypeLiteral;
import org.jclouds.cloudsigma2.CloudSigma2Api;
import org.jclouds.cloudsigma2.domain.CreateSubscriptionRequest;
import org.jclouds.cloudsigma2.domain.DriveInfo;
import org.jclouds.cloudsigma2.domain.FirewallPolicy;
import org.jclouds.cloudsigma2.domain.IPInfo;
import org.jclouds.cloudsigma2.domain.LibraryDrive;
import org.jclouds.cloudsigma2.domain.ProfileInfo;
import org.jclouds.cloudsigma2.domain.ServerInfo;
import org.jclouds.cloudsigma2.domain.Tag;
import org.jclouds.cloudsigma2.domain.VLANInfo;
import org.jclouds.cloudsigma2.functions.CreateSubscriptionRequestToJson;
import org.jclouds.cloudsigma2.functions.DriveToJson;
import org.jclouds.cloudsigma2.functions.FirewallPolicyToJson;
import org.jclouds.cloudsigma2.functions.IPInfoToJson;
import org.jclouds.cloudsigma2.functions.LibraryDriveToJson;
import org.jclouds.cloudsigma2.functions.ProfileInfoToJson;
import org.jclouds.cloudsigma2.functions.ServerInfoToJson;
import org.jclouds.cloudsigma2.functions.TagToJson;
import org.jclouds.cloudsigma2.functions.VLANInfoToJson;
import org.jclouds.cloudsigma2.handlers.CloudSigmaErrorHandler;
import org.jclouds.http.HttpErrorHandler;
import org.jclouds.http.annotation.ClientError;
import org.jclouds.http.annotation.Redirection;
import org.jclouds.http.annotation.ServerError;
import org.jclouds.rest.ConfiguresHttpApi;
import org.jclouds.rest.config.HttpApiModule;

/**
 * Configures the CloudSigma connection.
 *
 * @author Vladimir Shevchenko
 */
@ConfiguresHttpApi
public class CloudSigma2HttpApiModule extends HttpApiModule<CloudSigma2Api> {

    @Override
    protected void bindErrorHandlers() {
        bind(HttpErrorHandler.class).annotatedWith(Redirection.class).to(CloudSigmaErrorHandler.class);
        bind(HttpErrorHandler.class).annotatedWith(ClientError.class).to(CloudSigmaErrorHandler.class);
        bind(HttpErrorHandler.class).annotatedWith(ServerError.class).to(CloudSigmaErrorHandler.class);
    }

    @Override
    protected void configure() {
        super.configure();
        bind(new TypeLiteral<Function<DriveInfo, JsonObject>>() {
        }).to(DriveToJson.class);
        bind(new TypeLiteral<Function<ServerInfo, JsonObject>>() {
        }).to(ServerInfoToJson.class);
        bind(new TypeLiteral<Function<Tag, JsonObject>>() {
        }).to(TagToJson.class);
        bind(new TypeLiteral<Function<ProfileInfo, JsonObject>>() {
        }).to(ProfileInfoToJson.class);
        bind(new TypeLiteral<Function<LibraryDrive, JsonObject>>() {
        }).to(LibraryDriveToJson.class);
        bind(new TypeLiteral<Function<FirewallPolicy, JsonObject>>() {
        }).to(FirewallPolicyToJson.class);
        bind(new TypeLiteral<Function<IPInfo, JsonObject>>() {
        }).to(IPInfoToJson.class);
        bind(new TypeLiteral<Function<VLANInfo, JsonObject>>() {
        }).to(VLANInfoToJson.class);
        bind(new TypeLiteral<Function<CreateSubscriptionRequest, JsonObject>>() {
        }).to(CreateSubscriptionRequestToJson.class);
    }

    @Override
    protected void bindRetryHandlers() {
        // TODO
    }

}
