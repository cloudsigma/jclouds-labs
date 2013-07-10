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
import org.jclouds.cloudsigma2.beans.RawTag;
import org.jclouds.cloudsigma2.domain.Tag;
import org.jclouds.http.HttpResponse;
import org.jclouds.http.functions.ParseJson;
import org.jclouds.javax.annotation.Nullable;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

/**
 * @author Vladimir Shevchenko
 */
@Singleton
public class ParseTag implements Function<HttpResponse, Tag> {

    private final ParseJson<Response> rawTagParseJson;
    private final JsonToTag jsonToTag;

    @Inject
    public ParseTag(ParseJson<Response> rawTagParseJson, JsonToTag jsonToTag) {
        this.rawTagParseJson = rawTagParseJson;
        this.jsonToTag = jsonToTag;
    }

    @Override
    public Tag apply(@Nullable HttpResponse input) {
        RawTag rawTag = null;
        Response response = rawTagParseJson.apply(input);
        if(response.objects != null && response.objects.iterator().hasNext()){
            rawTag = response.objects.iterator().next();
        } else{
            rawTag = response;
        }

        if(rawTag == null){
            return null;
        }

        return jsonToTag.apply(rawTag);
    }

    private class Response extends RawTag{
        List<RawTag> objects;
    }
}
