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

import com.google.common.collect.ImmutableList;

import java.net.URI;
import java.util.Date;
import java.util.List;

/**
 * @author Vladimir Shevchenko
 */
public class Subscription {

    public static class Builder{
        protected String amount;
        protected boolean isAutoRenewEnabled;
        protected List<Subscription> descendants;
        protected double discountAmount;
        protected double discountPercent;
        protected Date endTime;
        protected String id;
        protected Date lastNotification;
        protected String period;
        protected double price;
        protected String remaining;
        protected SubscriptionResource resource;
        protected URI resourceUri;
        protected Date startTime;
        protected String status;
        protected String subscribedObject;
        protected String uuid;

        /**
         * @param amount
         * @return Subscription Binder
         */
        public Builder amount(String amount){
            this.amount = amount;
            return this;
        }

        /**
         * @param isAutoRenewEnabled States if the subscription will auto renew on expire
         * @return Subscription Binder
         */
        public Builder isAutoRenewEnabled(boolean isAutoRenewEnabled){
            this.isAutoRenewEnabled = isAutoRenewEnabled;
            return this;
        }

        /**
         * @param descendants Subscriptions that have been extended from the current one
         * @return Subscription Binder
         */
        public Builder descendants(List<Subscription> descendants){
            this.descendants = ImmutableList.copyOf(descendants);
            return this;
        }

        /**
         * @param discountAmount Amount of discount
         * @return Subscription Binder
         */
        public Builder discountAmount(double discountAmount){
            this.discountAmount = discountAmount;
            return this;
        }

        /**
         * @param discountPercent Percent of discount
         * @return Subscription Binder
         */
        public Builder discountPercent(double discountPercent){
            this.discountPercent = discountPercent;
            return this;
        }

        /**
         * @param endTime End time of subscription
         * @return Subscription Binder
         */
        public Builder endTime(Date endTime){
            this.endTime = endTime;
            return this;
        }

        /**
         * @param id unique id
         * @return Subscription Binder
         */
        public Builder id(String id){
            this.id = id;
            return this;
        }

        /**
         * @param lastNotification A date & time of last notification
         * @return Subscription Binder
         */
        public Builder lastNotification(Date lastNotification){
            this.lastNotification = lastNotification;
            return this;
        }

        /**
         * @param period Duration of the subscription
         * @return Subscription Binder
         */
        public Builder period(String period){
            this.period = period;
            return this;
        }

        /**
         * @param price Subscription price
         * @return Subscription Binder
         */
        public Builder price(double price){
            this.price = price;
            return this;
        }

        /**
         * @param remaining Amount remaining
         * @return Subscription Binder
         */
        public Builder remaining(String remaining){
            this.remaining = remaining;
            return this;
        }

        /**
         * @param resource Name of resource associated with the subscription
         * @return Subscription Binder
         */
        public Builder resource(SubscriptionResource resource){
            this.resource = resource;
            return this;
        }

        /**
         * @param resourceUri Resource URI
         * @return Subscription Binder
         */
        public Builder resourceUri(URI resourceUri){
            this.resourceUri = resourceUri;
            return this;
        }

        /**
         * @param startTime Start time of subscription
         * @return Subscription Binder
         */
        public Builder startTime(Date startTime){
            this.startTime = startTime;
            return this;
        }

        /**
         * @param status Status of the subscription
         * @return Subscription Binder
         */
        public Builder status(String status){
            this.status = status;
            return this;
        }

        /**
         * @param subscribedObject Subscribed object - the target of this subscription, if applicable
         * @return Subscription Binder
         */
        public Builder subscribedObject(String subscribedObject){
            this.subscribedObject = subscribedObject;
            return this;
        }

        /**
         * @param uuid Subscription uuid
         * @return Subscription Binder
         */
        public Builder uuid(String uuid){
            this.uuid = uuid;
            return this;
        }

        public Subscription build(){
            return new Subscription(amount, isAutoRenewEnabled, descendants, discountAmount, discountPercent, endTime
                    , id, lastNotification, period, price, remaining, resource, resourceUri, startTime, status
                    , subscribedObject, uuid);
        }
    }

    protected final String amount;
    protected final boolean isAutoRenewEnabled;
    protected final List<Subscription> descendants;
    protected final double discountAmount;
    protected final double discountPercent;
    protected final Date endTime;
    protected final String id;
    protected final Date lastNotification;
    protected final String period;
    protected final double price;
    protected final String remaining;
    protected final SubscriptionResource resource;
    protected final URI resourceUri;
    protected final Date startTime;
    protected final String status;
    protected final String subscribedObject;
    protected final String uuid;

    public Subscription(String amount, boolean autoRenewEnabled, List<Subscription> descendants, double discountAmount
            , double discountPercent, Date endTime, String id, Date lastNotification, String period, double price
            , String remaining, SubscriptionResource resource, URI resourceUri, Date startTime, String status
            , String subscribedObject, String uuid) {
        this.amount = amount;
        isAutoRenewEnabled = autoRenewEnabled;
        this.descendants = descendants;
        this.discountAmount = discountAmount;
        this.discountPercent = discountPercent;
        this.endTime = endTime;
        this.id = id;
        this.lastNotification = lastNotification;
        this.period = period;
        this.price = price;
        this.remaining = remaining;
        this.resource = resource;
        this.resourceUri = resourceUri;
        this.startTime = startTime;
        this.status = status;
        this.subscribedObject = subscribedObject;
        this.uuid = uuid;
    }

    /**
     * @return Subscription amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     * @return States if the subscription will auto renew on expire
     */
    public boolean isAutoRenewEnabled() {
        return isAutoRenewEnabled;
    }

    /**
     * @return Subscriptions that have been extended from the current one
     */
    public List<Subscription> getDescendants() {
        return descendants;
    }

    /**
     * @return Amount of discount
     */
    public double getDiscountAmount() {
        return discountAmount;
    }

    /**
     * @return Percent of discount
     */
    public double getDiscountPercent() {
        return discountPercent;
    }

    /**
     * @return End time of subscription
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * @return "Unicode id
     */
    public String getId() {
        return id;
    }

    /**
     * @return A date & time of last notification
     */
    public Date getLastNotification() {
        return lastNotification;
    }

    /**
     * @return Duration of the subscription
     */
    public String getPeriod() {
        return period;
    }

    /**
     * @return Subscription price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @return Amount remaining
     */
    public String getRemaining() {
        return remaining;
    }

    /**
     * @return Name of resource associated with the subscription
     */
    public SubscriptionResource getResource() {
        return resource;
    }

    /**
     * @return resource uri
     */
    public URI getResourceUri() {
        return resourceUri;
    }

    /**
     * @return Start time of subscription
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * @return "Status of the subscription
     */
    public String getStatus() {
        return status;
    }

    /**
     * @return Subscribed object - the target of this subscription, if applicable
     */
    public String getSubscribedObject() {
        return subscribedObject;
    }

    /**
     * @return subscription uuid
     */
    public String getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subscription)) return false;

        Subscription that = (Subscription) o;

        if (Double.compare(that.discountAmount, discountAmount) != 0) return false;
        if (Double.compare(that.discountPercent, discountPercent) != 0) return false;
        if (isAutoRenewEnabled != that.isAutoRenewEnabled) return false;
        if (Double.compare(that.price, price) != 0) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (descendants != null ? !descendants.equals(that.descendants) : that.descendants != null) return false;
        if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (lastNotification != null ? !lastNotification.equals(that.lastNotification) : that.lastNotification != null)
            return false;
        if (period != null ? !period.equals(that.period) : that.period != null) return false;
        if (remaining != null ? !remaining.equals(that.remaining) : that.remaining != null) return false;
        if (resource != null ? !resource.equals(that.resource) : that.resource != null) return false;
        if (resourceUri != null ? !resourceUri.equals(that.resourceUri) : that.resourceUri != null) return false;
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (subscribedObject != null ? !subscribedObject.equals(that.subscribedObject) : that.subscribedObject != null)
            return false;
        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = amount != null ? amount.hashCode() : 0;
        result = 31 * result + (isAutoRenewEnabled ? 1 : 0);
        result = 31 * result + (descendants != null ? descendants.hashCode() : 0);
        temp = Double.doubleToLongBits(discountAmount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(discountPercent);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (lastNotification != null ? lastNotification.hashCode() : 0);
        result = 31 * result + (period != null ? period.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (remaining != null ? remaining.hashCode() : 0);
        result = 31 * result + (resource != null ? resource.hashCode() : 0);
        result = 31 * result + (resourceUri != null ? resourceUri.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (subscribedObject != null ? subscribedObject.hashCode() : 0);
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "[" +
                "amount='" + amount + '\'' +
                ", isAutoRenewEnabled=" + isAutoRenewEnabled +
                ", descendants=" + descendants +
                ", discountAmount=" + discountAmount +
                ", discountPercent=" + discountPercent +
                ", endTime=" + endTime +
                ", id='" + id + '\'' +
                ", lastNotification=" + lastNotification +
                ", period='" + period + '\'' +
                ", price=" + price +
                ", remaining='" + remaining + '\'' +
                ", resource='" + resource + '\'' +
                ", resourceUri=" + resourceUri +
                ", startTime=" + startTime +
                ", status='" + status + '\'' +
                ", subscribedObject='" + subscribedObject + '\'' +
                ", uuid='" + uuid + '\'' +
                "]";
    }
}
