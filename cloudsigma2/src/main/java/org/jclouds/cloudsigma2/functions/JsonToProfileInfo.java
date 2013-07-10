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

        if(input.access_blocked != null){
            profileBuilder.isAccessBlocked(input.access_blocked);
        }

        if(input.address != null){
            profileBuilder.address(input.address);
        }

        if(input.affiliate_id != null){
            profileBuilder.affiliateId(input.affiliate_id);
        }

        if(input.affiliate_type != null){
            profileBuilder.affiliateType(input.affiliate_type);
        }

        if(input.api_disabled != null){
            profileBuilder.isApiDisabled(input.api_disabled);
        }

        if(input.api_https_only != null){
            profileBuilder.isApiHttpsOnly(input.api_https_only);
        }

        if(input.autotopup_amount != null){
            profileBuilder.autotopupAmount(input.autotopup_amount);
        }

        if(input.company != null){
            profileBuilder.company(input.company);
        }

        if(input.country != null){
            profileBuilder.country(input.country);
        }

        if(input.courtesy_email_sent != null){
            profileBuilder.isCourtesyEmailSent(input.courtesy_email_sent);
        }

        if(input.creation_ip != null){
            profileBuilder.creationIp(input.creation_ip);
        }

        if(input.date_joined != null){
            profileBuilder.dateJoined(new SimpleDateFormatDateService().iso8601DateParse(input.date_joined));
        }

        if(input.email != null){
            profileBuilder.email(input.email);
        }

        if(input.failed_logins != null){
            profileBuilder.failedLogins(input.failed_logins);
        }

        if(input.first_name != null){
            profileBuilder.firstName(input.first_name);
        }

        if(input.ftp_disabled != null){
            profileBuilder.isFtpDisabled(input.ftp_disabled);
        }

        if(input.has_autotopup != null){
            profileBuilder.hasAutotopup(input.has_autotopup);
        }

        if(input.id != null){
            profileBuilder.id(input.id);
        }

        if(input.is_active != null){
            profileBuilder.isActive(input.is_active);
        }

        if(input.is_banned != null){
            profileBuilder.isBanned(input.is_banned);
        }

        if(input.is_sandbox != null){
            profileBuilder.isSandbox(input.is_sandbox);
        }

        if(input.is_staff != null){
            profileBuilder.isStaff(input.is_staff);
        }

        if(input.is_superuser != null){
            profileBuilder.isSuperuser(input.is_superuser);
        }

        if(input.is_trial != null){
            profileBuilder.isTrial(input.is_trial);
        }

        if(input.key_auth != null){
            profileBuilder.isKeyAuth(input.key_auth);
        }

        if(input.language != null){
            profileBuilder.language(input.language);
        }

        if(input.signup_time != null){
            profileBuilder.signupTime(new SimpleDateFormatDateService().iso8601DateParse(input.signup_time));
        }

        if(input.last_name != null){
            profileBuilder.lastName(input.last_name);
        }

        if(input.login_sms != null){
            profileBuilder.isLoginSmsEnabled(input.login_sms);
        }

        if(input.mailing_list != null){
            profileBuilder.isMailingListEnabled(input.mailing_list);
        }

        if(input.my_notes != null){
            profileBuilder.myNotes(input.my_notes);
        }

        if(input.nickname != null){
            profileBuilder.nickname(input.nickname);
        }

        if(input.offer_id != null){
            profileBuilder.offerId(input.offer_id);
        }

        if(input.phone != null){
            profileBuilder.phone(input.phone);
        }

        if(input.postcode != null){
            profileBuilder.postcode(input.postcode);
        }

        if(input.referer != null){
            profileBuilder.referer(input.referer);
        }

        if(input.register_phone != null){
            profileBuilder.registerPhone(input.register_phone);
        }

        if(input.reseller != null){
            profileBuilder.reseller(input.reseller);
        }

        if(input.title != null){
            profileBuilder.title(input.title);
        }

        if(input.town != null){
            profileBuilder.town(input.town);
        }

        if(input.trial_expired != null){
            profileBuilder.trialExpired(input.trial_expired);
        }

        if(input.user_type != null){
            profileBuilder.userType(input.user_type);
        }

        if(input.username != null){
            profileBuilder.username(input.username);
        }

        if(input.uuid != null){
            profileBuilder.uuid(input.uuid);
        }

        if(input.vat != null){
            profileBuilder.vat(input.vat);
        }

        if(input.warned_inactivity != null){
            profileBuilder.warnedInactivity(input.warned_inactivity);
        }

        if(input.warned_not_logged_in != null){
            profileBuilder.warnedNotLoggedIn(input.warned_not_logged_in);
        }

        if(input.warned_storage_to_be_deleted != null){
            profileBuilder.warnedStorageToBeDeleted(input.warned_storage_to_be_deleted);
        }

        if(input.wdyhau != null){
            profileBuilder.wdyhau(input.wdyhau);
        }

        return profileBuilder.build();
    }
}
