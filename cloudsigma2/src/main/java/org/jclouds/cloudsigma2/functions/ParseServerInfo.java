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
import org.jclouds.cloudsigma2.beans.RawServerInfo;
import org.jclouds.cloudsigma2.domain.ServerInfo;
import org.jclouds.http.HttpResponse;
import org.jclouds.http.functions.ParseJson;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Vladimir Shevchenko
 */
@Singleton
public class ParseServerInfo implements Function<HttpResponse, ServerInfo> {

    private final ParseJson<RawResponse> parseJson;
    private final JsonToServerInfo jsonToDrive;

    @Inject
    public ParseServerInfo(ParseJson<RawResponse> parseJson, JsonToServerInfo jsonToDrive) {
        this.parseJson = parseJson;
        this.jsonToDrive = jsonToDrive;
    }

    @Override
    public ServerInfo apply(HttpResponse input) {
        RawServerInfo rawServerInfo = null;
        RawResponse response = parseJson.apply(input);
        if(response.objects != null && response.objects.iterator().hasNext()){
            rawServerInfo = response.objects.iterator().next();
        } else{
            rawServerInfo = response;
        }

        if(rawServerInfo == null){
            return null;
        }

        return jsonToDrive.apply(rawServerInfo);
    }

    protected class RawResponse extends RawServerInfo{
        Iterable<RawServerInfo> objects;
    }
}
