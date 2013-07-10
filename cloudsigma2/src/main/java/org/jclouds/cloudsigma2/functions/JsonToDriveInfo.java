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
import org.jclouds.cloudsigma2.beans.RawServer;
import org.jclouds.cloudsigma2.domain.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Shevchenko
 */
@Singleton
public class JsonToDriveInfo implements Function<RawDrive, DriveInfo> {

    private final JsonToOwner jsonToOwner;
    private final JsonToServer jsonToServerInfo;
    private final JsonToLicense jsonToLicense;

    @Inject
    public JsonToDriveInfo(JsonToOwner jsonToOwner, JsonToServer jsonToServerInfo, JsonToLicense jsonToLicense) {
        this.jsonToOwner = jsonToOwner;
        this.jsonToServerInfo = jsonToServerInfo;
        this.jsonToLicense = jsonToLicense;
    }

    @Override
    public DriveInfo apply(RawDrive input) {
        if(input == null){
            return null;
        }

        DriveInfo.Builder builder = new DriveInfo.Builder();

        if(input.owner != null){
            builder.owner(jsonToOwner.apply(input.owner));
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

        if(input.affinities != null){
            builder.affinities(input.affinities);
        }

        if(input.jobs != null){
            builder.jobs(input.jobs);
        }

        if(input.licenses != null){
            List<DriveLicense> driveLicenseList = new ArrayList<DriveLicense>();
            for(RawDrive.RawDriveLicense driveLicense : input.licenses){
                DriveLicense.Builder licenseBuilder = new DriveLicense.Builder();

                if(driveLicense.amount != null){
                    licenseBuilder.amount(driveLicense.amount);
                }
                if(driveLicense.license != null){
                    licenseBuilder.license(jsonToLicense.apply(driveLicense.license));
                }
                if(driveLicense.user != null){
                    licenseBuilder.user(jsonToOwner.apply(driveLicense.user));
                }

                driveLicenseList.add(licenseBuilder.build());
            }

            builder.licenses(driveLicenseList);
        }

        if(input.media != null){
            builder.media(MediaType.fromValue(input.media));
        }

        if(input.meta != null){
            builder.meta(input.meta);
        }

        if(input.mounted_on != null){
            List<Server> serverInfos = new ArrayList<Server>();
            for(RawServer rawServer : input.mounted_on){
                Server.Builder serverBuilder = new Server.Builder();
                serverInfos.add(jsonToServerInfo.apply(rawServer));
            }
            builder.mountedOn(serverInfos);
        }

        if(input.name != null){
            builder.name(input.name);
        }

        if(input.tags != null){
            builder.tags(input.tags);
        }

        if(input.size != null){
            builder.size(input.size);
        }

        if(input.allow_multimount != null){
            builder.allowMultimount(input.allow_multimount);
        }
        return builder.build();
    }
}
