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
import com.google.gson.JsonObject;
import org.jclouds.cloudsigma2.beans.RawDrive;
import org.jclouds.cloudsigma2.domain.Drive;
import org.jclouds.cloudsigma2.domain.DriveStatus;
import org.jclouds.cloudsigma2.domain.Owner;

import javax.inject.Singleton;

/**
 * @author Vladimir Shevchenko
 */
@Singleton
public class JsonToDrive implements Function<RawDrive, Drive> {
    @Override
    public Drive apply(RawDrive input) {
        if(input == null){
            return null;
        }

        Drive.Builder builder = new Drive.Builder();
        if(input.owner != null){
            Owner.Builder ownerBuilder = new Owner.Builder();
            if(input.owner.uuid != null){
                ownerBuilder.uuid(input.owner.uuid);
            }
            if(input.owner.resource_uri != null){
                ownerBuilder.resourceUri(input.owner.resource_uri);
            }
            builder.owner(ownerBuilder.build());
        }

        if(input.resource_uri != null){
            builder.resourceUri(input.resource_uri);
        }

        if(input.status != null){
            builder.status(DriveStatus.fromValue(input.status));
        }

        if(input.uuid != null){
            builder.uuid(input.uuid);
        }
        return builder.build();
    }
}
