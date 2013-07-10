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
import org.jclouds.cloudsigma2.beans.RawCurrentUsage;
import org.jclouds.cloudsigma2.domain.CurrentUsage;
import org.jclouds.http.HttpResponse;
import org.jclouds.http.functions.ParseJson;
import org.jclouds.javax.annotation.Nullable;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Vladimir Shevchenko
 */
@Singleton
public class ParseCurrentUsage implements Function<HttpResponse, CurrentUsage>{

    private final ParseJson<RawCurrentUsage> currentUsageParseJson;
    private final JsonToCurrentUsage jsonToCurrentUsage;

    @Inject
    public ParseCurrentUsage(ParseJson<RawCurrentUsage> currentUsageParseJson, JsonToCurrentUsage jsonToCurrentUsage) {
        this.currentUsageParseJson = currentUsageParseJson;
        this.jsonToCurrentUsage = jsonToCurrentUsage;
    }

    @Override
    public CurrentUsage apply(@Nullable HttpResponse input) {
        RawCurrentUsage rawCurrentUsage = currentUsageParseJson.apply(input);
        return jsonToCurrentUsage.apply(rawCurrentUsage);
    }
}
