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
import org.jclouds.cloudsigma2.beans.RawProfileInfo;
import org.jclouds.cloudsigma2.domain.ProfileInfo;
import org.jclouds.date.internal.SimpleDateFormatDateService;
import org.jclouds.javax.annotation.Nullable;

import javax.inject.Singleton;

/**
 * @author Vladimir Shevchenko
 */
@Singleton
public class JsonToProfileInfo implements Function<RawProfileInfo, ProfileInfo> {
    @Override
    public ProfileInfo apply(@Nullable RawProfileInfo input) {
        if(input == null){
            return null;
        }

        ProfileInfo.Builder profileBuilder = new ProfileInfo.Builder();

        if(input.address != null){
            profileBuilder.address(input.address);
        }

        if(input.api_https_only != null){
            profileBuilder.isApiHttpsOnly(input.api_https_only);
        }

        if(input.autotopup_amount != null){
            profileBuilder.autotopupAmount(input.autotopup_amount);
        }

        if(input.autotopup_threshold != null){
            profileBuilder.autotopupThreshold(input.autotopup_threshold);
        }

        if(input.bank_reference != null){
            profileBuilder.bankReference(input.bank_reference);
        }

        if(input.company != null){
            profileBuilder.company(input.company);
        }

        if(input.country != null){
            profileBuilder.country(input.country);
        }

        if(input.currency != null){
            profileBuilder.currency(input.currency);
        }

        if(input.email != null){
            profileBuilder.email(input.email);
        }

        if(input.first_name != null){
            profileBuilder.firstName(input.first_name);
        }

        if(input.has_autotopup != null){
            profileBuilder.hasAutotopup(input.has_autotopup);
        }

        if(input.invoicing != null){
            profileBuilder.invoicing(input.invoicing);
        }

        if(input.key_auth != null){
            profileBuilder.isKeyAuth(input.key_auth);
        }

        if(input.language != null){
            profileBuilder.language(input.language);
        }

        if(input.signup_time != null){
            profileBuilder.signupTime(new SimpleDateFormatDateService().iso8601SecondsDateParse(input.signup_time));
        }

        if(input.last_name != null){
            profileBuilder.lastName(input.last_name);
        }

        if(input.mailing_list != null){
            profileBuilder.isMailingListEnabled(input.mailing_list);
        }

        if(input.meta != null){
            profileBuilder.meta(input.meta);
        }

        if(input.my_notes != null){
            profileBuilder.myNotes(input.my_notes);
        }

        if(input.nickname != null){
            profileBuilder.nickname(input.nickname);
        }

        if(input.phone != null){
            profileBuilder.phone(input.phone);
        }

        if(input.postcode != null){
            profileBuilder.postcode(input.postcode);
        }

        if(input.reseller != null){
            profileBuilder.reseller(input.reseller);
        }

        if(input.state != null){
            profileBuilder.state(input.state);
        }

        if(input.tax_name != null){
            profileBuilder.taxName(input.tax_name);
        }

        if(input.tax_rate != null){
            profileBuilder.taxRate(input.tax_rate);
        }

        if(input.title != null){
            profileBuilder.title(input.title);
        }

        if(input.town != null){
            profileBuilder.town(input.town);
        }

        if(input.uuid != null){
            profileBuilder.uuid(input.uuid);
        }

        if(input.vat != null){
            profileBuilder.vat(input.vat);
        }

        return profileBuilder.build();
    }
}
