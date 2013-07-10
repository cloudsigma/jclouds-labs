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
package org.jclouds.cloudsigma2.domain;

import java.util.Date;

/**
 * @author Vladimir Shevchenko
 */
public class ProfileInfo {

    public static class Builder {
        protected String uuid;
        protected String email;
        protected String firstName;
        protected String lastName;
        protected ProfileType userType;
        protected boolean isAccessBlocked;
        protected String address;
        protected int affiliateId;
        protected int affiliateType;
        protected boolean isApiDisabled;
        protected boolean isApiHttpsOnly;
        protected String autotopupAmount;
        protected String company;
        protected String country;
        protected boolean isCourtesyEmailSent;
        protected int creationIp;
        protected Date dateJoined;
        protected int failedLogins;
        protected boolean isFtpDisabled;
        protected boolean hasAutotopup;
        protected long id;
        protected boolean isActive;
        protected boolean isBanned;
        protected boolean isSandbox;
        protected boolean isStaff;
        protected boolean isSuperuser;
        protected boolean isTrial;
        protected boolean isKeyAuth;
        protected String language;
        protected Date signupTime;
        protected boolean isLoginSmsEnabled;
        protected boolean isMailingListEnabled;
        protected String myNotes;
        protected String nickname;
        protected long offerId;
        protected String phone;
        protected String postcode;
        protected String referer;
        protected String registerPhone;
        protected String reseller;
        protected String title;
        protected String town;
        protected int trialExpired;
        protected String username;
        protected String vat;
        protected int warnedInactivity;
        protected int warnedNotLoggedIn;
        protected int warnedStorageToBeDeleted;
        protected String wdyhau;

        public Builder uuid(String uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder userType(String userType) {
            this.userType = ProfileType.valueOf(userType);
            return this;
        }

        public Builder isAccessBlocked(boolean isAccessBlocked) {
            this.isAccessBlocked = isAccessBlocked;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder affiliateId(int affiliateId) {
            this.affiliateId = affiliateId;
            return this;
        }

        public Builder affiliateType(int affiliateType) {
            this.affiliateType = affiliateType;
            return this;
        }

        public Builder isApiDisabled(boolean isApiDisabled) {
            this.isApiDisabled = isApiDisabled;
            return this;
        }

        public Builder isApiHttpsOnly(boolean isApiHttpsOnly) {
            this.isApiHttpsOnly = isApiHttpsOnly;
            return this;
        }

        public Builder autotopupAmount(String autotopupAmount) {
            this.autotopupAmount = autotopupAmount;
            return this;
        }

        public Builder company(String company) {
            this.company = company;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Builder isCourtesyEmailSent(boolean isCourtesyEmailSent) {
            this.isCourtesyEmailSent = isCourtesyEmailSent;
            return this;
        }

        public Builder creationIp(int creationIp) {
            this.creationIp = creationIp;
            return this;
        }

        public Builder dateJoined(Date dateJoined) {
            this.dateJoined = dateJoined;
            return this;
        }

        public Builder failedLogins(int failedLogins) {
            this.failedLogins = failedLogins;
            return this;
        }

        public Builder isFtpDisabled(boolean isFtpDisabled) {
            this.isFtpDisabled = isFtpDisabled;
            return this;
        }

        public Builder hasAutotopup(boolean hasAutotopup) {
            this.hasAutotopup = hasAutotopup;
            return this;
        }

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder isActive(boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public Builder isBanned(boolean isBanned) {
            this.isBanned = isBanned;
            return this;
        }

        public Builder isSandbox(boolean isSandbox) {
            this.isSandbox = isSandbox;
            return this;
        }

        public Builder isStaff(boolean isStaff) {
            this.isStaff = isStaff;
            return this;
        }

        public Builder isSuperuser(boolean isSuperuser) {
            this.isSuperuser = isSuperuser;
            return this;
        }

        public Builder isTrial(boolean isTrial) {
            this.isTrial = isTrial;
            return this;
        }

        public Builder isKeyAuth(boolean isKeyAuth) {
            this.isKeyAuth = isKeyAuth;
            return this;
        }

        public Builder language(String language) {
            this.language = language;
            return this;
        }

        public Builder signupTime(Date signupTime) {
            this.signupTime = signupTime;
            return this;
        }

        public Builder isLoginSmsEnabled(boolean isLoginSmsEnabled) {
            this.isLoginSmsEnabled = isLoginSmsEnabled;
            return this;
        }

        public Builder isMailingListEnabled(boolean isMailingListEnabled) {
            this.isMailingListEnabled = isMailingListEnabled;
            return this;
        }

        public Builder myNotes(String myNotes) {
            this.myNotes = myNotes;
            return this;
        }

        public Builder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public Builder offerId(long offerId) {
            this.offerId = offerId;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder postcode(String postcode) {
            this.postcode = postcode;
            return this;
        }

        public Builder referer(String referer) {
            this.referer = referer;
            return this;
        }

        public Builder registerPhone(String registerPhone) {
            this.registerPhone = registerPhone;
            return this;
        }

        public Builder reseller(String reseller) {
            this.reseller = reseller;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder town(String town) {
            this.town = town;
            return this;
        }

        public Builder trialExpired(int trialExpired) {
            this.trialExpired = trialExpired;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder vat(String vat) {
            this.vat = vat;
            return this;
        }

        public Builder warnedInactivity(int warnedInactivity) {
            this.warnedInactivity = warnedInactivity;
            return this;
        }

        public Builder warnedNotLoggedIn(int warnedNotLoggedIn) {
            this.warnedNotLoggedIn = warnedNotLoggedIn;
            return this;
        }

        public Builder warnedStorageToBeDeleted(int warnedStorageToBeDeleted) {
            this.warnedStorageToBeDeleted = warnedStorageToBeDeleted;
            return this;
        }

        public Builder wdyhau(String wdyhau) {
            this.wdyhau = wdyhau;
            return this;
        }

        public ProfileInfo build() {
            return new ProfileInfo(uuid, email, firstName, lastName, userType, isAccessBlocked, address, affiliateId
                    , affiliateType, isApiDisabled, isApiHttpsOnly, autotopupAmount, company, country, isCourtesyEmailSent
                    , creationIp, dateJoined, failedLogins, isFtpDisabled, hasAutotopup, id, isActive, isBanned, isSandbox
                    , isStaff, isSuperuser, isTrial, isKeyAuth, language, signupTime, isLoginSmsEnabled, isMailingListEnabled
                    , myNotes, nickname, offerId, phone, postcode, referer, registerPhone, reseller, title, town
                    , trialExpired, username, vat, warnedInactivity, warnedNotLoggedIn, warnedStorageToBeDeleted, wdyhau);
        }

    }

    protected final String uuid;
    protected final String email;
    protected final String firstName;
    protected final String lastName;
    protected final ProfileType userType;
    protected final boolean isAccessBlocked;
    protected final String address;
    protected final int affiliateId;
    protected final int affiliateType;
    protected final boolean isApiDisabled;
    protected final boolean isApiHttpsOnly;
    protected final String autotopupAmount;
    protected final String company;
    protected final String country;
    protected final boolean isCourtesyEmailSent;
    protected final int creationIp;
    protected final Date dateJoined;
    protected final int failedLogins;
    protected final boolean isFtpDisabled;
    protected final boolean hasAutotopup;
    protected final long id;
    protected final boolean isActive;
    protected final boolean isBanned;
    protected final boolean isSandbox;
    protected final boolean isStaff;
    protected final boolean isSuperuser;
    protected final boolean isTrial;
    protected final boolean isKeyAuth;
    protected final String language;
    protected final Date signupTime;
    protected final boolean isLoginSmsEnabled;
    protected final boolean isMailingListEnabled;
    protected final String myNotes;
    protected final String nickname;
    protected final long offerId;
    protected final String phone;
    protected final String postcode;
    protected final String referer;
    protected final String registerPhone;
    protected final String reseller;
    protected final String title;
    protected final String town;
    protected final int trialExpired;
    protected final String username;
    protected final String vat;
    protected final int warnedInactivity;
    protected final int warnedNotLoggedIn;
    protected final int warnedStorageToBeDeleted;
    protected final String wdyhau;

    public ProfileInfo(String uuid, String email, String firstName, String lastName, ProfileType userType
            , boolean accessBlocked, String address, int affiliateId, int affiliateType, boolean apiDisabled
            , boolean apiHttpsOnly, String autotopupAmount, String company, String country, boolean courtesyEmailSent
            , int creationIp, Date dateJoined, int failedLogins, boolean ftpDisabled, boolean hasAutotopup, long id
            , boolean active, boolean banned, boolean sandbox, boolean staff, boolean superuser, boolean trial
            , boolean keyAuth, String language, Date signupTime, boolean loginSmsEnabled, boolean mailingListEnabled
            , String myNotes, String nickname, long offerId, String phone, String postcode, String referer
            , String registerPhone, String reseller, String title, String town, int trialExpired, String username
            , String vat, int warnedInactivity, int warnedNotLoggedIn, int warnedStorageToBeDeleted, String wdyhau) {
        this.uuid = uuid;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userType = userType;
        isAccessBlocked = accessBlocked;
        this.address = address;
        this.affiliateId = affiliateId;
        this.affiliateType = affiliateType;
        isApiDisabled = apiDisabled;
        isApiHttpsOnly = apiHttpsOnly;
        this.autotopupAmount = autotopupAmount;
        this.company = company;
        this.country = country;
        isCourtesyEmailSent = courtesyEmailSent;
        this.creationIp = creationIp;
        this.dateJoined = dateJoined;
        this.failedLogins = failedLogins;
        isFtpDisabled = ftpDisabled;
        this.hasAutotopup = hasAutotopup;
        this.id = id;
        isActive = active;
        isBanned = banned;
        isSandbox = sandbox;
        isStaff = staff;
        isSuperuser = superuser;
        isTrial = trial;
        isKeyAuth = keyAuth;
        this.language = language;
        this.signupTime = signupTime;
        isLoginSmsEnabled = loginSmsEnabled;
        isMailingListEnabled = mailingListEnabled;
        this.myNotes = myNotes;
        this.nickname = nickname;
        this.offerId = offerId;
        this.phone = phone;
        this.postcode = postcode;
        this.referer = referer;
        this.registerPhone = registerPhone;
        this.reseller = reseller;
        this.title = title;
        this.town = town;
        this.trialExpired = trialExpired;
        this.username = username;
        this.vat = vat;
        this.warnedInactivity = warnedInactivity;
        this.warnedNotLoggedIn = warnedNotLoggedIn;
        this.warnedStorageToBeDeleted = warnedStorageToBeDeleted;
        this.wdyhau = wdyhau;
    }

    public String getAddress() {
        return address;
    }

    public int getAffiliateId() {
        return affiliateId;
    }

    public int getAffiliateType() {
        return affiliateType;
    }

    public String getAutotopupAmount() {
        return autotopupAmount;
    }

    public String getCompany() {
        return company;
    }

    public String getCountry() {
        return country;
    }

    public int getCreationIp() {
        return creationIp;
    }

    public Date getDateJoined() {
        return dateJoined;
    }

    public String getEmail() {
        return email;
    }

    public int getFailedLogins() {
        return failedLogins;
    }

    public String getFirstName() {
        return firstName;
    }

    public boolean isHasAutotopup() {
        return hasAutotopup;
    }

    public long getId() {
        return id;
    }

    public boolean isAccessBlocked() {
        return isAccessBlocked;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isApiDisabled() {
        return isApiDisabled;
    }

    public boolean isApiHttpsOnly() {
        return isApiHttpsOnly;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public boolean isCourtesyEmailSent() {
        return isCourtesyEmailSent;
    }

    public boolean isFtpDisabled() {
        return isFtpDisabled;
    }

    public boolean isKeyAuth() {
        return isKeyAuth;
    }

    public boolean isLoginSmsEnabled() {
        return isLoginSmsEnabled;
    }

    public boolean isMailingListEnabled() {
        return isMailingListEnabled;
    }

    public boolean isSandbox() {
        return isSandbox;
    }

    public boolean isStaff() {
        return isStaff;
    }

    public boolean isSuperuser() {
        return isSuperuser;
    }

    public boolean isTrial() {
        return isTrial;
    }

    public String getLanguage() {
        return language;
    }

    public Date getSignupTime() {
        return signupTime;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMyNotes() {
        return myNotes;
    }

    public String getNickname() {
        return nickname;
    }

    public long getOfferId() {
        return offerId;
    }

    public String getPhone() {
        return phone;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getReferer() {
        return referer;
    }

    public String getRegisterPhone() {
        return registerPhone;
    }

    public String getReseller() {
        return reseller;
    }

    public String getTitle() {
        return title;
    }

    public String getTown() {
        return town;
    }

    public int getTrialExpired() {
        return trialExpired;
    }

    public String getUsername() {
        return username;
    }

    public ProfileType getUserType() {
        return userType;
    }

    public String getUuid() {
        return uuid;
    }

    public String getVat() {
        return vat;
    }

    public int getWarnedInactivity() {
        return warnedInactivity;
    }

    public int getWarnedNotLoggedIn() {
        return warnedNotLoggedIn;
    }

    public int getWarnedStorageToBeDeleted() {
        return warnedStorageToBeDeleted;
    }

    public String getWdyhau() {
        return wdyhau;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProfileInfo)) return false;

        ProfileInfo that = (ProfileInfo) o;

        if (affiliateId != that.affiliateId) return false;
        if (affiliateType != that.affiliateType) return false;
        if (creationIp != that.creationIp) return false;
        if (failedLogins != that.failedLogins) return false;
        if (hasAutotopup != that.hasAutotopup) return false;
        if (id != that.id) return false;
        if (isAccessBlocked != that.isAccessBlocked) return false;
        if (isActive != that.isActive) return false;
        if (isApiDisabled != that.isApiDisabled) return false;
        if (isApiHttpsOnly != that.isApiHttpsOnly) return false;
        if (isBanned != that.isBanned) return false;
        if (isCourtesyEmailSent != that.isCourtesyEmailSent) return false;
        if (isFtpDisabled != that.isFtpDisabled) return false;
        if (isKeyAuth != that.isKeyAuth) return false;
        if (isLoginSmsEnabled != that.isLoginSmsEnabled) return false;
        if (isMailingListEnabled != that.isMailingListEnabled) return false;
        if (isSandbox != that.isSandbox) return false;
        if (isStaff != that.isStaff) return false;
        if (isSuperuser != that.isSuperuser) return false;
        if (isTrial != that.isTrial) return false;
        if (offerId != that.offerId) return false;
        if (trialExpired != that.trialExpired) return false;
        if (warnedInactivity != that.warnedInactivity) return false;
        if (warnedNotLoggedIn != that.warnedNotLoggedIn) return false;
        if (warnedStorageToBeDeleted != that.warnedStorageToBeDeleted) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (autotopupAmount != null ? !autotopupAmount.equals(that.autotopupAmount) : that.autotopupAmount != null)
            return false;
        if (company != null ? !company.equals(that.company) : that.company != null) return false;
        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        if (dateJoined != null ? !dateJoined.equals(that.dateJoined) : that.dateJoined != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (language != null ? !language.equals(that.language) : that.language != null) return false;
        if (signupTime != null ? !signupTime.equals(that.signupTime) : that.signupTime != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (myNotes != null ? !myNotes.equals(that.myNotes) : that.myNotes != null) return false;
        if (nickname != null ? !nickname.equals(that.nickname) : that.nickname != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (postcode != null ? !postcode.equals(that.postcode) : that.postcode != null) return false;
        if (referer != null ? !referer.equals(that.referer) : that.referer != null) return false;
        if (registerPhone != null ? !registerPhone.equals(that.registerPhone) : that.registerPhone != null)
            return false;
        if (reseller != null ? !reseller.equals(that.reseller) : that.reseller != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (town != null ? !town.equals(that.town) : that.town != null) return false;
        if (userType != that.userType) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) return false;
        if (vat != null ? !vat.equals(that.vat) : that.vat != null) return false;
        if (wdyhau != null ? !wdyhau.equals(that.wdyhau) : that.wdyhau != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (userType != null ? userType.hashCode() : 0);
        result = 31 * result + (isAccessBlocked ? 1 : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + affiliateId;
        result = 31 * result + affiliateType;
        result = 31 * result + (isApiDisabled ? 1 : 0);
        result = 31 * result + (isApiHttpsOnly ? 1 : 0);
        result = 31 * result + (autotopupAmount != null ? autotopupAmount.hashCode() : 0);
        result = 31 * result + (company != null ? company.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (isCourtesyEmailSent ? 1 : 0);
        result = 31 * result + creationIp;
        result = 31 * result + (dateJoined != null ? dateJoined.hashCode() : 0);
        result = 31 * result + failedLogins;
        result = 31 * result + (isFtpDisabled ? 1 : 0);
        result = 31 * result + (hasAutotopup ? 1 : 0);
        result = 31 * result + (int) (id ^ (id >>> 32));
        result = 31 * result + (isActive ? 1 : 0);
        result = 31 * result + (isBanned ? 1 : 0);
        result = 31 * result + (isSandbox ? 1 : 0);
        result = 31 * result + (isStaff ? 1 : 0);
        result = 31 * result + (isSuperuser ? 1 : 0);
        result = 31 * result + (isTrial ? 1 : 0);
        result = 31 * result + (isKeyAuth ? 1 : 0);
        result = 31 * result + (language != null ? language.hashCode() : 0);
        result = 31 * result + (signupTime != null ? signupTime.hashCode() : 0);
        result = 31 * result + (isLoginSmsEnabled ? 1 : 0);
        result = 31 * result + (isMailingListEnabled ? 1 : 0);
        result = 31 * result + (myNotes != null ? myNotes.hashCode() : 0);
        result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
        result = 31 * result + (int) (offerId ^ (offerId >>> 32));
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (postcode != null ? postcode.hashCode() : 0);
        result = 31 * result + (referer != null ? referer.hashCode() : 0);
        result = 31 * result + (registerPhone != null ? registerPhone.hashCode() : 0);
        result = 31 * result + (reseller != null ? reseller.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (town != null ? town.hashCode() : 0);
        result = 31 * result + trialExpired;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (vat != null ? vat.hashCode() : 0);
        result = 31 * result + warnedInactivity;
        result = 31 * result + warnedNotLoggedIn;
        result = 31 * result + warnedStorageToBeDeleted;
        result = 31 * result + (wdyhau != null ? wdyhau.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "[" +
                "address='" + address + '\'' +
                ", uuid='" + uuid + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userType=" + userType +
                ", isAccessBlocked=" + isAccessBlocked +
                ", affiliateId=" + affiliateId +
                ", affiliateType=" + affiliateType +
                ", isApiDisabled=" + isApiDisabled +
                ", isApiHttpsOnly=" + isApiHttpsOnly +
                ", autotopupAmount='" + autotopupAmount + '\'' +
                ", company='" + company + '\'' +
                ", country='" + country + '\'' +
                ", isCourtesyEmailSent=" + isCourtesyEmailSent +
                ", creationIp=" + creationIp +
                ", dateJoined=" + dateJoined +
                ", failedLogins=" + failedLogins +
                ", isFtpDisabled=" + isFtpDisabled +
                ", hasAutotopup=" + hasAutotopup +
                ", id=" + id +
                ", isActive=" + isActive +
                ", isBanned=" + isBanned +
                ", isSandbox=" + isSandbox +
                ", isStaff=" + isStaff +
                ", isSuperuser=" + isSuperuser +
                ", isTrial=" + isTrial +
                ", isKeyAuth=" + isKeyAuth +
                ", language='" + language + '\'' +
                ", signupTime=" + signupTime +
                ", isLoginSmsEnabled=" + isLoginSmsEnabled +
                ", isMailingListEnabled=" + isMailingListEnabled +
                ", myNotes='" + myNotes + '\'' +
                ", nickname='" + nickname + '\'' +
                ", offerId=" + offerId +
                ", phone='" + phone + '\'' +
                ", postcode='" + postcode + '\'' +
                ", referer='" + referer + '\'' +
                ", registerPhone='" + registerPhone + '\'' +
                ", reseller='" + reseller + '\'' +
                ", title='" + title + '\'' +
                ", town='" + town + '\'' +
                ", trialExpired=" + trialExpired +
                ", username='" + username + '\'' +
                ", vat='" + vat + '\'' +
                ", warnedInactivity=" + warnedInactivity +
                ", warnedNotLoggedIn=" + warnedNotLoggedIn +
                ", warnedStorageToBeDeleted=" + warnedStorageToBeDeleted +
                ", wdyhau='" + wdyhau + '\'' +
                "]";
    }
}
