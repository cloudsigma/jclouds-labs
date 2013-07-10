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
import org.jclouds.cloudsigma2.beans.RawDrive;
import org.jclouds.cloudsigma2.domain.License;
import org.jclouds.javax.annotation.Nullable;

import javax.inject.Singleton;

/**
 * @author Vladimir Shevchenko
 */
@Singleton
public class JsonToLicense implements Function<RawDrive.RawLicense, License> {
    @Override
    public License apply(@Nullable RawDrive.RawLicense input) {
        if(input == null){
            return null;
        }

        License.Builder licenseInfoBuilder = new License.Builder();

        if(input.burstable != null){
            licenseInfoBuilder.isBurstable(input.burstable);
        }

        if(input.long_name != null){
            licenseInfoBuilder.longName(input.long_name);
        }

        if(input.name != null){
            licenseInfoBuilder.name(input.name);
        }

        if(input.resource_uri != null){
            licenseInfoBuilder.resourceUri(input.resource_uri);
        }

        if(input.type != null){
            licenseInfoBuilder.type(input.type);
        }

        if(input.user_metric != null){
            licenseInfoBuilder.userMetric(input.user_metric);
        }

        return licenseInfoBuilder.build();
    }
}
