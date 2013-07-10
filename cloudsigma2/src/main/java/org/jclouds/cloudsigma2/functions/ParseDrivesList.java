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
import org.jclouds.cloudsigma2.domain.Drive;
import org.jclouds.http.HttpResponse;
import org.jclouds.http.functions.ParseJson;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class ParseDrivesList implements Function<HttpResponse, Iterable<Drive>> {

    private final ParseJson<Response> parseJson;
    private final JsonToDrive jsonToDrive;

    @Inject
    ParseDrivesList(ParseJson<Response> parseJson, JsonToDrive jsonToDrive){
        this.parseJson = parseJson;
        this.jsonToDrive = jsonToDrive;
    }

    @Override
    public List<Drive> apply(HttpResponse response) {
        Response rawResponse = parseJson.apply(response);

        return toDriveInfo(rawResponse.objects);
    }

    private List<Drive> toDriveInfo(Iterable<RawDrive> list){
        List<Drive> returnList = new ArrayList<Drive>();

        for(RawDrive drive : list){
            returnList.add(jsonToDrive.apply(drive));
        }

        return returnList;
    }

    private static class Response{
        List<RawDrive> objects;
    }
}
