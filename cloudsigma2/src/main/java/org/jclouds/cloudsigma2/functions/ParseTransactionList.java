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
import org.jclouds.cloudsigma2.beans.RawTransaction;
import org.jclouds.cloudsigma2.domain.Transaction;
import org.jclouds.http.HttpResponse;
import org.jclouds.http.functions.ParseJson;
import org.jclouds.javax.annotation.Nullable;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Shevchenko
 */
@Singleton
public class ParseTransactionList implements Function<HttpResponse, List<Transaction>> {

    private final ParseJson<Response> parseJson;
    private final JsonToTransaction jsonToTransaction;

    @Inject
    public ParseTransactionList(ParseJson<Response> parseJson, JsonToTransaction jsonToTransaction) {
        this.parseJson = parseJson;
        this.jsonToTransaction = jsonToTransaction;
    }

    @Override
    public List<Transaction> apply(@Nullable HttpResponse input) {
        Response response = parseJson.apply(input);

        List<Transaction> returnList = new ArrayList<Transaction>();

        for(RawTransaction transaction: response.objects){
            returnList.add(jsonToTransaction.apply(transaction));
        }
        return returnList;
    }

    private class Response{
        List<RawTransaction> objects;
    }
}
