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
import com.google.inject.Inject;
import org.jclouds.cloudsigma2.beans.RawDrive;
import org.jclouds.cloudsigma2.domain.DriveInfo;
import org.jclouds.http.HttpResponse;
import org.jclouds.http.functions.ParseJson;

import javax.inject.Singleton;

/**
 * Created by Vladimir Shevchenko shevchen55@gmail.com
 * Date: 6/13/13
 */
@Singleton
public class ParseDriveInfo implements Function<HttpResponse, DriveInfo> {

    private final ParseJson<RawDrive> parseJson;
    private final JsonToDriveInfo jsonToDrive;

    @Inject
    ParseDriveInfo(ParseJson<RawDrive> parseJson, JsonToDriveInfo jsonToDriveInfo){
        this.parseJson = parseJson;
        this.jsonToDrive = jsonToDriveInfo;
    }


    @Override
    public DriveInfo apply(HttpResponse response) {
        RawDrive rawDrive = parseJson.apply(response);
        if(rawDrive == null){
            return null;
        }

        return jsonToDrive.apply(rawDrive);
    }
}
