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
package org.jclouds.cloudsigma2;

import com.google.common.util.concurrent.ListenableFuture;
import org.jclouds.Fallbacks.NullOnNotFoundOr404;
import org.jclouds.Fallbacks.VoidOnNotFoundOr404;
import org.jclouds.cloudsigma2.binders.*;
import org.jclouds.cloudsigma2.domain.*;
import org.jclouds.cloudsigma2.functions.*;
import org.jclouds.http.filters.BasicAuthentication;
import org.jclouds.javax.annotation.Nullable;
import org.jclouds.rest.annotations.BinderParam;
import org.jclouds.rest.annotations.Fallback;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.ResponseParser;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.Closeable;
import java.util.List;

/**
 * Provides asynchronous access to CloudSigma via their REST API.
 * <p/>
 *
 * @see CloudSigma2Client
 * @see <a href="http://cloudsigma.com/en/platform-details/the-api" />
 * @author Adrian Cole
 * @deprecated please use {@code org.jclouds.ContextBuilder#buildApi(CloudSigma2Client.class)} as
 *             {@link CloudSigma2AsyncClient} interface will be removed in jclouds 1.7.
 */
@Deprecated
@RequestFilters(BasicAuthentication.class)
@Consumes(MediaType.TEXT_PLAIN)
public interface CloudSigma2AsyncClient extends Closeable {

    /**
     * @see CloudSigma2Client#listDrives
     */
    @GET
    @Path("/drives/")
    @ResponseParser(ParseDrivesList.class)
    ListenableFuture<List<Drive>> listDrives();

    /**
     * @see CloudSigma2Client#listDrives(DrivesListRequestFieldsGroup fields, int limit)
     */
    @GET
    @Path("/drives/?fields={fields}&limit={limit}")
    @ResponseParser(ParseDrivesList.class)
    ListenableFuture<List<DriveInfo>> listDrives(@PathParam("fields") DrivesListRequestFieldsGroup fields
                                            , @DefaultValue("0") @PathParam("limit") int limit);

    /**
     * @see CloudSigma2Client#listDrivesInfo
     */
    @GET
    @Path("/drives/detail/")
    @ResponseParser(ParseDrivesInfoList.class)
    ListenableFuture<List<DriveInfo>> listDrivesInfo();

    /**
     * @see CloudSigma2Client#getDriveInfo
     */
    @GET
    @Path("/drives/{uuid}/")
    @Fallback(NullOnNotFoundOr404.class)
    @ResponseParser(ParseDriveInfo.class)
    ListenableFuture<DriveInfo> getDriveInfo(@PathParam("uuid") String uuid);

    /**
     * @see CloudSigma2Client#createDrive
     */
    @POST
    @Fallback(NullOnNotFoundOr404.class)
    @ResponseParser(ParseDriveInfo.class)
    @Path("/drives/")
    ListenableFuture<DriveInfo> createDrive(@BinderParam(BindDriveToJson.class) DriveInfo createDrive);

    /**
     * @see CloudSigma2Client#createDrives
     */
    @POST
    @Fallback(NullOnNotFoundOr404.class)
    @ResponseParser(ParseDrivesInfoList.class)
    @Path("/drives/")
    ListenableFuture<DriveInfo> createDrives(@BinderParam(BindDrivesToJson.class) List<DriveInfo> createDrives);

    /**
     * @see CloudSigma2Client#deleteDrive
     */
    @DELETE
    @Path("/drives/{uuid}/")
    @Fallback(VoidOnNotFoundOr404.class)
    ListenableFuture<Void> deleteDrive(@PathParam("uuid") String uuid);

    /**
     * @see CloudSigma2Client#deleteDrives
     */
    @DELETE
    @Path("/drives/")
    @Fallback(VoidOnNotFoundOr404.class)
    ListenableFuture<Void> deleteDrives(@BinderParam(BindUuidStringsToJsonArray.class) Iterable<String> driveUuids);

    /**
     * @see CloudSigma2Client#editDrive
     */
    @PUT
    @Path("/drives/{uuid}/")
    @ResponseParser(ParseDriveInfo.class)
    @Fallback(VoidOnNotFoundOr404.class)
    ListenableFuture<DriveInfo> editDrive(@PathParam("uuid") String sourceUuid
            , @BinderParam(BindDriveToJson.class) DriveInfo drive);


    /**
     * @see CloudSigma2Client#cloneDrive
     */
    @POST
    @Path("/drives/{uuid}/action/?do=clone")
    @ResponseParser(ParseDriveInfo.class)
    @Fallback(VoidOnNotFoundOr404.class)
    ListenableFuture<DriveInfo> cloneDrive(@PathParam("uuid") String sourceUuid
                                        , @Nullable @BinderParam(BindDriveToJson.class) DriveInfo drive);

    /**
     * @see CloudSigma2Client#listLibraryDrives
     */
    @GET
    @Path("/libdrives/")
    @ResponseParser(ParseLibraryDrivesList.class)
    ListenableFuture<List<LibraryDrive>> listLibraryDrives();

    /**
     * @see CloudSigma2Client#getLibraryDrive
     */
    @GET
    @Path("/libdrives/{uuid}")
    @ResponseParser(ParseLibraryDrive.class)
    @Fallback(NullOnNotFoundOr404.class)
    ListenableFuture<List<LibraryDrive>> getLibraryDrive(@PathParam("uuid") String uuid);

    /**
     * @see CloudSigma2Client#cloneLibraryDrive
     */
    @POST
    @Path("/libdrives/{uuid}/action/?do=clone")
    @ResponseParser(ParseLibraryDrive.class)
    @Fallback(VoidOnNotFoundOr404.class)
    ListenableFuture<DriveInfo> cloneLibraryDrive(@PathParam("uuid") String uuid
            , @Nullable @BinderParam(BindLibraryDriveToJson.class) LibraryDrive libraryDrive);

    /**
     * @see CloudSigma2Client#listServers
     */
    @GET
    @Path("/servers/")
    @ResponseParser(ParseServerList.class)
    ListenableFuture<List<Server>> listServers();

    /**
     * @see CloudSigma2Client#listServersInfo
     */
    @GET
    @Path("/servers/detail/")
    @ResponseParser(ParseServerInfoList.class)
    ListenableFuture<List<ServerInfo>> listServersInfo();

    /**
     * @see CloudSigma2Client#createServer
     */
    @POST
    @Path("/servers/")
    @ResponseParser(ParseServerInfo.class)
    ListenableFuture<ServerInfo> createServer(@BinderParam(BindServerInfoToJsonRequest.class) ServerInfo createServer);

    /**
     * @see CloudSigma2Client#createServers
     */
    @POST
    @Path("/servers/")
    @ResponseParser(ParseServerInfoList.class)
    ListenableFuture<List<ServerInfo>> createServers(@BinderParam(BindServerInfoListToJsonRequest.class) Iterable<ServerInfo> createServer);

    /**
     * @see CloudSigma2Client#editServer
     */
    @PUT
    @Path("/servers/{uuid}/")
    @ResponseParser(ParseServerInfo.class)
    @Fallback(VoidOnNotFoundOr404.class)
    ListenableFuture<ServerInfo> editServer(@PathParam("uuid") String uuid
                                            , @BinderParam(BindServerInfoToJsonRequest.class) ServerInfo server);

    /**
     * @see CloudSigma2Client#deleteServer
     */
    @DELETE
    @Path("/servers/{uuid}/")
    @Fallback(VoidOnNotFoundOr404.class)
    ListenableFuture<Void> deleteServer(@PathParam("uuid") String uuid);

    /**
     * @see CloudSigma2Client#deleteServers
     */
    @DELETE
    @Path("/servers/")
    @Fallback(VoidOnNotFoundOr404.class)
    ListenableFuture<Void> deleteServers(@BinderParam(BindUuidStringsToJsonArray.class) Iterable<String> uuid);

    /**
     * @see CloudSigma2Client#cloneServer
     */
    @POST
    @Path("/servers/{uuid}/action/?do=clone")
    @Fallback(VoidOnNotFoundOr404.class)
    ListenableFuture<Void> cloneServer(@PathParam("uuid") String uuid);

    /**
     * @see CloudSigma2Client#getServerInfo
     */
    @GET
    @Path("/servers/{uuid}/")
    @ResponseParser(ParseServerInfo.class)
    @Fallback(NullOnNotFoundOr404.class)
    ListenableFuture<ServerInfo> getServerInfo(@PathParam("uuid") String uuid);

    /**
     * @see CloudSigma2Client#startServer
     */
    @POST
    @Path("/servers/{uuid}/action/?do=start")
    @Fallback(NullOnNotFoundOr404.class)
    ListenableFuture<Void> startServer(@PathParam("uuid") String uuid);

    /**
     * @see CloudSigma2Client#stopServer
     */
    @POST
    @Path("/servers/{uuid}/action/?do=stop")
    @Fallback(NullOnNotFoundOr404.class)
    ListenableFuture<Void> stopServer(@PathParam("uuid") String uuid);

    /**
     * @see CloudSigma2Client#startServerInSeparateAvailabilityGroup
     */
    @POST
    @Path("/servers/{uuid}/action/?do=start&avoid={group}")
    @Fallback(NullOnNotFoundOr404.class)
    ListenableFuture<Void> startServerInSeparateAvailabilityGroup(@PathParam("uuid") String uuid
                                                                , @PathParam("group") ServerAvailabilityGroup uuidGroup);

    /**
     * @see CloudSigma2Client#openServerVNCTunnel
     */
    @POST
    @Path("/servers/{uuid}/action/?do=open_vnc")
    @Fallback(NullOnNotFoundOr404.class)
    ListenableFuture<Void> openServerVNCTunnel(@PathParam("uuid") String uuid);

    /**
     * @see CloudSigma2Client#closeServerVCNTunnel
     */
    @POST
    @Path("/servers/{uuid}/action/?do=close_vnc")
    @Fallback(NullOnNotFoundOr404.class)
    ListenableFuture<Void> closeServerVCNTunnel(@PathParam("uuid") String uuid);

    /**
     * @see CloudSigma2Client#listServerAvailabilityGroup
     */
    @GET
    @Path("/servers/availability_groups/")
    @ResponseParser(ParseAvailabilityGroupList.class)
    ListenableFuture<List<ServerAvailabilityGroup>> listServerAvailabilityGroup();

    /**
     * @see CloudSigma2Client#getServerAvailabilityGroup
     */
    @GET
    @PathParam("/servers/availability_groups/{uuid}/")
    @ResponseParser(ParseAvailabilityGroup.class)
    @Fallback(NullOnNotFoundOr404.class)
    ListenableFuture<ServerAvailabilityGroup> getServerAvailabilityGroup(@PathParam("uuid") String uuid);

    /**
     * @see CloudSigma2Client#listFirewallPolicies
     */
    @GET
    @Path("/fwpolicies/")
    @ResponseParser(ParseFirewallPoliciesList.class)
    ListenableFuture<List<FirewallPolicy>> listFirewallPolicies();

    /**
     * @see CloudSigma2Client#listFirewallPoliciesInfo
     */
    @GET
    @Path("/fwpolicies/detail/")
    @ResponseParser(ParseFirewallPoliciesList.class)
    ListenableFuture<List<FirewallPolicy>> listFirewallPoliciesInfo();

    /**
     * @see CloudSigma2Client#createFirewallPolicies
     */
    @POST
    @Path("/fwpolicies/")
    @ResponseParser(ParseFirewallPoliciesList.class)
    ListenableFuture<List<FirewallPolicy>> createFirewallPolicies(
            @BinderParam(BindFirewallPoliciesListToJsonRequest.class) List<FirewallPolicy> firewallPolicies);

    /**
     * @see CloudSigma2Client#createFirewallPolicy
     */
    @POST
    @Path("/fwpolicies/")
    @ResponseParser(ParseFirewallPolicy.class)
    ListenableFuture<FirewallPolicy> createFirewallPolicy(
            @BinderParam(BindFirewallPolicyToJsonRequest.class) FirewallPolicy firewallPolicy);

    /**
     * @see CloudSigma2Client#editFirewallPolicy
     */
    @PUT
    @Path("/fwpolicies/{uuid}/")
    @ResponseParser(ParseFirewallPolicy.class)
    ListenableFuture<FirewallPolicy> editFirewallPolicy(@PathParam("uuid") String uuid
            , @BinderParam(BindFirewallPolicyToJsonRequest.class) FirewallPolicy firewallPolicy);

    /**
     * @see CloudSigma2Client#getVLANInfo
     */
    @GET
    @Path("/vlans/{uuid}/")
    @ResponseParser(ParseVLANInfo.class)
    @Fallback(NullOnNotFoundOr404.class)
    ListenableFuture<VLANInfo> getVLANInfo(@PathParam("uuid") String uuid);

    /**
     * @see CloudSigma2Client#listVLANs
     */
    @GET
    @Path("/vlans/")
    @ResponseParser(ParseVLANInfoList.class)
    ListenableFuture<List<VLANInfo>> listVLANs();

    /**
     * @see CloudSigma2Client#listVLANInfo
     */
    @GET
    @Path("/vlans/detail/")
    @ResponseParser(ParseVLANInfoList.class)
    ListenableFuture<List<VLANInfo>> listVLANInfo();

    /**
     * @see CloudSigma2Client#editVLAN
     */
    @PUT
    @Path("/vlans/{uuid}/")
    @ResponseParser(ParseVLANInfoList.class)
    @Fallback(NullOnNotFoundOr404.class)
    ListenableFuture<VLANInfo> editVLAN(@PathParam("uuid") String uuid
                                            , @BinderParam(BindVLANToJsonRequest.class) VLANInfo vlanInfo);

    /**
     * @see CloudSigma2Client#listIPs
     */
    @GET
    @Path("/ips/")
    @ResponseParser(ParseIPInfoList.class)
    ListenableFuture<List<IP>> listIPs();

    /**
     * @see CloudSigma2Client#listIPInfo
     */
    @GET
    @Path("/ips/detail/")
    @ResponseParser(ParseIPInfoList.class)
    ListenableFuture<List<IPInfo>> listIPInfo();

    /**
     * @see CloudSigma2Client#getIPInfo
     */
    @GET
    @Path("/ips/{uuid}/")
    @ResponseParser(ParseIPInfo.class)
    @Fallback(NullOnNotFoundOr404.class)
    ListenableFuture<IPInfo> getIPInfo(@PathParam("uuid") String uuid);


    /**
     * @see CloudSigma2Client#editIP
     */
    @PUT
    @Path("/ips/{uuid}/")
    @ResponseParser(ParseIPInfo.class)
    @Fallback(NullOnNotFoundOr404.class)
    ListenableFuture<IPInfo> editIP(@PathParam("uuid") String uuid
            , @BinderParam(BindIPInfoToJsonRequest.class) IPInfo ipInfo);

    /**
     * @see CloudSigma2Client#listTags
     */
    @GET
    @Path("/tags/")
    @ResponseParser(ParseTagList.class)
    ListenableFuture<List<Tag>> listTags();

    /**
     * @see CloudSigma2Client#listTagsInfo
     */
    @GET
    @Path("/tags/detail/")
    @ResponseParser(ParseTagList.class)
    ListenableFuture<List<Tag>> listTagsInfo();

    /**
     * @see CloudSigma2Client#getTagInfo
     */
    @GET
    @Path("/tags/{uuid}/")
    @ResponseParser(ParseTag.class)
    @Fallback(NullOnNotFoundOr404.class)
    ListenableFuture<Tag> getTagInfo(@PathParam("uuid") String uuid);

    /**
     * @see CloudSigma2Client#editTag
     */
    @PUT
    @Path("/tags/{uuid}/")
    @ResponseParser(ParseTag.class)
    @Fallback(NullOnNotFoundOr404.class)
    ListenableFuture<Tag> editTag(@PathParam("uuid") String uuid, @BinderParam(BindTagToJsonRequest.class) Tag tag);

    /**
     * @see CloudSigma2Client#createTag
     */
    @POST
    @Path("/tags/")
    @ResponseParser(ParseTag.class)
    ListenableFuture<Tag> createTag(@BinderParam(BindTagToJsonRequest.class) Tag tag);

    /**
     * @see CloudSigma2Client#deleteTag
     */
    @DELETE
    @Path("/tags/{uuid}/")
    @Fallback(NullOnNotFoundOr404.class)
    ListenableFuture<Void> deleteTag(@PathParam("uuid")String uuid);

    /**
     * @see CloudSigma2Client#deleteTags
     */
    @DELETE
    @Path("/tags/")
    @Fallback(NullOnNotFoundOr404.class)
    ListenableFuture<Void> deleteTags(@BinderParam(BindUuidStringsToJsonArray.class) List<String> uuids);

    /**
     * @see CloudSigma2Client#createTags
     */
    @POST
    @Path("/tags/")
    @ResponseParser(ParseTagList.class)
    ListenableFuture<Tag> createTags(@BinderParam(BindTagListToJsonRequest.class) List<Tag> tags);

    /**
     * @see CloudSigma2Client#getProfileInfo
     */
    @GET
    @Path("/profile/")
    @ResponseParser(ParseProfileInfo.class)
    ListenableFuture<ProfileInfo> getProfileInfo();

    /**
     * @see CloudSigma2Client#editProfileInfo
     */
    @PUT
    @Path("/profile/")
    @ResponseParser(ParseProfileInfo.class)
    ListenableFuture<ProfileInfo> editProfileInfo(@BinderParam(BindProfileInfoToJsonRequest.class) ProfileInfo profile);

    /**
     * @see CloudSigma2Client#getAccountBalance
     */
    @GET
    @Path("/balance/")
    @ResponseParser(ParseAccountBalance.class)
    ListenableFuture<AccountBalance> getAccountBalance();

    /**
     * @see CloudSigma2Client#getCurrentUsage
     */
    @GET
    @Path("/currentusage/")
    @ResponseParser(ParseCurrentUsage.class)
    ListenableFuture<CurrentUsage> getCurrentUsage();

    /**
     * @see CloudSigma2Client#listSubscriptions
     */
    @GET
    @Path("/subscriptions/")
    @ResponseParser(ParseSubscriptionsList.class)
    ListenableFuture<List<Subscription>> listSubscriptions();

    /**
     * @see CloudSigma2Client#listGroupedSubscriptions
     */
    @GET
    @Path("/groupedsubscriptions/")
    @ResponseParser(ParseSubscriptionsList.class)
    ListenableFuture<List<Subscription>> listGroupedSubscriptions();

    /**
     * @see CloudSigma2Client#listSubscriptionsCalculator
     */
    @GET
    @Path("/subscriptioncalculator/")
    @ResponseParser(ParseSubscriptionsList.class)
    ListenableFuture<List<Subscription>> listSubscriptionsCalculator();

    /**
     * @see CloudSigma2Client#createSubscription
     */
    @POST
    @Path("/subscriptions/")
    @ResponseParser(ParseSubscription.class)
    ListenableFuture<Subscription> createSubscription(
                @BinderParam(BindCreateSubscriptionRequest.class) CreateSubscriptionRequest subscriptionRequest);

    /**
     * @see CloudSigma2Client#createSubscriptions
     */
    @POST
    @Path("/subscriptions/")
    @ResponseParser(ParseSubscriptionsList.class)
    ListenableFuture<List<Subscription>> createSubscriptions(
            @BinderParam(BindCreateSubscriptionRequestList.class) List<CreateSubscriptionRequest> subscriptionRequest);

    /**
     * @see CloudSigma2Client#extendSubscription
     */
    @POST
    @Path("/subscriptions/{id}/action/?do=extend")
    @Fallback(NullOnNotFoundOr404.class)
    ListenableFuture<Void> extendSubscription(@PathParam("id") String id);

    /**
     * @see CloudSigma2Client#enableSubscriptionAutorenew
     */
    @POST
    @Path("/subscriptions/{id}/action/?do=auto_renew")
    @Fallback(NullOnNotFoundOr404.class)
    ListenableFuture<Void> enableSubscriptionAutorenew(@PathParam("id") String id);

    /**
     * @see CloudSigma2Client#getPricing
     */
    @GET
    @Path("/pricing/")
    @ResponseParser(ParsePricing.class)
    ListenableFuture<Pricing> getPricing();

    /**
     * @see CloudSigma2Client#listDiscounts
     */
    @GET
    @Path("/discount/")
    @ResponseParser(ParseDiscountsList.class)
    ListenableFuture<List<Discount>> listDiscounts();

    /**
     * @see CloudSigma2Client#listTransactions
     */
    @GET
    @Path("/ledger/")
    @ResponseParser(ParseTransactionList.class)
    ListenableFuture<List<Transaction>> listTransactions();

    /**
     * @see CloudSigma2Client#listLicenses
     */
    @GET
    @Path("/licenses/")
    @ResponseParser(ParseLicenseList.class)
    ListenableFuture<List<License>> listLicenses();
}
