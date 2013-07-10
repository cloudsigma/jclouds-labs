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
import org.jclouds.cloudsigma2.CloudSigma2AsyncClient;
import org.jclouds.cloudsigma2.CloudSigma2Client;
import org.jclouds.cloudsigma2.domain.*;
import org.jclouds.cloudsigma2.functions.*;
import org.jclouds.cloudsigma2.handlers.CloudSigmaErrorHandler;
import org.jclouds.http.HttpErrorHandler;
import org.jclouds.http.annotation.ClientError;
import org.jclouds.http.annotation.Redirection;
import org.jclouds.http.annotation.ServerError;
import org.jclouds.rest.ConfiguresRestClient;
import org.jclouds.rest.config.RestClientModule;

import java.util.List;
import java.util.Map;

/**
 * Configures the CloudSigma connection.
 *
 * @author Adrian Cole
 */
@ConfiguresRestClient
public class CloudSigma2RestClientModule extends RestClientModule<CloudSigma2Client, CloudSigma2AsyncClient> {

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
        }).to(DriveInfoToJson.class);
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
        bind(new TypeLiteral<Function<CreateSubscriptionRequest, JsonObject>>() {
        }).to(CreateSubscriptionRequestToJson.class);
    }

    @Override
    protected void bindRetryHandlers() {
        // TODO
    }

}
