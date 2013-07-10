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

import org.jclouds.cloudsigma2.domain.*;
import org.jclouds.javax.annotation.Nullable;

import java.io.Closeable;
import java.util.List;

/**
 * Provides synchronous access to CloudSigma.
 * <p/>
 *
 * @author Adrian Cole
 * @see CloudSigma2AsyncClient
 * @see <a href="https://zrh.cloudsigma.com/docs/" />
 */
public interface CloudSigma2Client extends Closeable {

    /**
     * Get profile info
     *
     * @return info or null, if not found
     */
    ProfileInfo getProfileInfo();

    /**
     * Change your profile info
     *
     * @param profile data to change
     * @return info or null, if not found
     */
    ProfileInfo editProfileInfo(ProfileInfo profile);

    /**
     * Get current account balance
     *
     * @return current account balance
     */
    AccountBalance getAccountBalance();

    /**
     * Get current account usage
     *
     * @return current account usage
     */
    CurrentUsage getCurrentUsage();

    /**
     * Gets the list of servers to which the authenticated user has access.
     *
     * @return list of servers or empty list if no servers are found
     */
    List<Server> listServers();

    /**
     * Gets the detailed list of servers to which the authenticated user has access.
     *
     * @return list of servers or empty list if no servers are found
     */
    List<ServerInfo> listServersInfo();

    /**
     * Gets detailed information for server identified by server uuid.
     *
     * @param uuid server uuid
     * @return null, if not found
     */
    ServerInfo getServerInfo(String uuid);

    /**
     * Creates a new virtual server or multiple servers.
     * The minimial amount of information you need to set is:
     *      cpu
     *      memory
     *      name
     *      vncPassword
     *
     * @param server equired parameters: cpu, memory, name, vncPassword
     * @return newly created server
     */
    ServerInfo createServer(ServerInfo server);

    /**
     * create a new servers
     *
     * @param servers
     * @return newly created servers
     */
    List<ServerInfo> createServers(Iterable<ServerInfo> servers);

    /**
     * Edits a server. Used also for attaching NIC’s and drives to servers.
     *
     * @param uuid server uuid
     * @param server data to change
     * @return modified server
     */
    ServerInfo editServer(String uuid, ServerInfo server);

    /**
     * Deletes a single server.
     *
     * @param uuid uuid of server to destroy
     */
    void deleteServer(String uuid);

    /**
     * Deletes multiple servers specified by their UUID’s.
     *
     * @param uuids server uuids to delete
     */
    void deleteServers(Iterable<String> uuids);

    /**
     * Clones a server. Empty body.
     * Does cascading clone of server drives.
     * IPs of the cloned server are set to DHCP.
     * All other properties of the clone are equal to the original.
     *
     * @param uuid server what to clone
     */
    void cloneServer(String uuid);

    /**
     * Starts a server with specific UUID.
     *
     * @param uuid uuid of server to start
     */
    void startServer(String uuid);

    /**
     * Stops a server with specific UUID.
     *
     * @param uuid uuid of server to stop
     */
    void stopServer(String uuid);

    /**
     * Starts a server with specific UUID assuring that it is started on a different physical infrastructure host
     * from the other servers specified in the avoid argument which is
     * a single server UUID or a comma-separated list of server UUIDs.
     * This way the server specified by uuid is run in a distinct availability group from the other listed servers.
     * Note that it might not always be possible to run a server in a different availability group,
     * therefore the order of the avoid list also signifies the priority of avoiding other servers.
     *
     * @param uuid      uuid of server to start
     * @param uuidGroup availability group to avoid
     */
    void startServerInSeparateAvailabilityGroup(String uuid, ServerAvailabilityGroup uuidGroup);

    /**
     * @return which servers share same physical infrastructure host.
     */
    List<ServerAvailabilityGroup> listServerAvailabilityGroup();

    /**
     * Queries which other servers share same physical host with the given one.
     *
     * @return an array holding server UUIDs. The response includes also the UUID of the queried server.
     */
    ServerAvailabilityGroup getServerAvailabilityGroup(String uuid);

    /**
     * Opens a VNC tunnel to a server with specific UUID.
     *
     * @param uuid uuid of server to open VNC tunnel
     */
    void openServerVNCTunnel(String uuid);

    /**
     * Closes a VNC tunnel to a server with specific UUID.
     *
     * @param uuid uuid of server to close VNC tunnel
     */
    void closeServerVCNTunnel(String uuid);

    /**
     * Gets the list of drives to which the authenticated user has access.
     *
     * @return or empty set if no drives are found
     */
    List<Drive> listDrives();

    /**
     * Gets the list of drives to which the authenticated user has access.
     *
     * @param fields A set of field names specifying the returned fields
     * @param limit number of drives to show
     * @return or empty set if no drives are found
     */
    List<Drive> listDrives(DrivesListRequestFieldsGroup fields, int limit);

    /**
     * Gets the detailed list of drives with additional information to which the authenticated user has access.
     *
     * @return list of drives or empty list if no drives are found
     */
    List<DriveInfo> listDrivesInfo();

    /**
     * Gets detailed information for drive identified by drive uuid
     *
     * @param uuid drive uuid to get
     * @return null, if not found
     */
    DriveInfo getDriveInfo(String uuid);

    /**
     * Creates a new drive
     *
     * @param createDrive required parameters: name, size, media
     * @return newly created drive
     */
    DriveInfo createDrive(DriveInfo createDrive);

    /**
     * Creates multiple new drives
     *
     * @param createDrives required parameters: name, size, media
     * @return newly created drives
     */
    List<DriveInfo> createDrives(List<DriveInfo> createDrives);

    /**
     * Deletes a single mounted or unmounted drive.
     *
     * @param uuid what to delete
     */
    void deleteDrive(String uuid);

    /**
     * Deletes multiple mounted or unmounted drives specified by their UUID’s.
     *
     * @param uuids what drives to delete
     */
    void deleteDrives(Iterable<String> uuids);

    /**
     * Clones a drive. Request body is optional and any or all of the key/value pairs can be omitted.
     *
     * @param sourceUuid source drive to clone
     * @param driveInfo drive parameters to change
     * @return new drive
     */
    DriveInfo cloneDrive(String sourceUuid, @Nullable DriveInfo driveInfo);

    /**
     * Edits a mounted or unmounted drive. If mounted, the server mounted on should be stopped.
     *
     * @param sourceUuid source drive to edit
     * @param driveInfo drive parameters to change
     * @return changed drive
     */
    DriveInfo editDrive(String sourceUuid, DriveInfo driveInfo);

    /**
     * Gets the list of library drives to which the authenticated user has access.
     *
     * @return list of library drives to which the authenticated user has access
     */
    List<LibraryDrive> listLibraryDrives();

    /**
     * Gets detailed information for library drive identified by uuid.
     *
     * @param uuid uuid of library drive to be listed
     * @return
     */
    LibraryDrive getLibraryDrive(String uuid);

    /**
     * If a library drive is not a CDROM, you have to clone it in your account in order to use it.
     *
     * @param uuid uuid of library drive to clone
     * @param libraryDrive cloned drive
     * @return
     */
    LibraryDrive cloneLibraryDrive(String uuid, @Nullable LibraryDrive libraryDrive);

    /**
     * Gets the list of VLANs to which the authenticated user has access.
     *
     * @return list of VLANs or empty list if no vlans are found
     */
    List<VLANInfo> listVLANs();

    /**
     * Gets the list of VLANs to which the authenticated user has access.
     *
     * @return list of VLANs or empty list if no vlans are found
     */
    List<VLANInfo> listVLANInfo();

    /**
     * Gets detailed information for VLAN identified by VLAN uuid.
     *
     * @param uuid uuid of VLAN to get
     * @return null, if not found
     */
    VLANInfo getVLANInfo(String uuid);


    /**
     * Currently only VLAN meta field can be edited.
     *
     * @param uuid uuid of VLAN to edit
     * @param vlanInfo data to change
     * @return changed VLAN
     */
    VLANInfo editVLAN(String uuid, VLANInfo vlanInfo);

    /**
     * Gets the list of IPs to which the authenticated user has access.
     *
     * @return list of IPs or empty list if no ips are found
     */
    List<IP> listIPs();

    /**
     * Gets the detailed list of IPs with additional information to which the authenticated user has access.
     *
     * @return list of IPs or empty list if no ips are found
     */
    List<IPInfo> listIPInfo();

    /**
     * Gets detailed information for IP identified by IP uuid.
     *
     * @param uuid uuid of IP to get
     * @return null, if not found
     */
    IPInfo getIPInfo(String uuid);

    /**
     * Currently only IP meta field can be edited.
     *
     * @param uuid uuid of IP to edit
     * @param ipInfo data to change
     * @return changed IP
     */
    IPInfo editIP(String uuid, IPInfo ipInfo);

    /**
     * Gets the list of tags to which the authenticated user has access.
     *
     * @return list of tags to which the authenticated user has access
     */
    List<Tag> listTags();

    /**
     * Gets the detailed list of tags with additional information to which the authenticated user has access
     * , like the tagged resource
     *
     * @return detailed listings of your tags
     */
    List<Tag> listTagsInfo();

    /**
     * Gets detailed information for tag identified by tag uuid.
     *
     * @param uuid tag uuid
     * @return detailed info of tag
     */
    Tag getTagInfo(String uuid);

    /**
     * Edits a tag.
     * It is possible to add or remove resources to a tag by replacing the resources list with a new one
     *
     * @param uuid tag uuid
     * @param tag info to change
     * @return detailed info of tag
     */
    Tag editTag(String uuid, Tag tag);

    /**
     * Creates a new tag
     *
     * @param tag tag to create
     * @return created tag
     */
    Tag createTag(Tag tag);

    /**
     * Creates a multiple new tags
     *
     * @param tags tags to create
     * @return created tags
     */
    List<Tag> createTags(List<Tag> tags);

    /**
     * Deletes a single tag.
     *
     * @param uuid uuid of tag to delete
     */
    void deleteTag(String uuid);

    /**
     * Deletes multiple tags
     *
     * @param uuids uuids of tags to delete
     */
    void deleteTags(List<String> uuids);

    /**
     * Gets the list of firewall policies to which the authenticated user has access.
     *
     * @return list of firewall policies to which the authenticated user has access.
     */
    List<FirewallPolicy> listFirewallPolicies();

    /**
     * Gets a detailed list of firewall policies to which the authenticated user has access.
     *
     * @return list of firewall policies to which the authenticated user has access.
     */
    List<FirewallPolicy> listFirewallPoliciesInfo();

    /**
     * Creates firewall policies.
     *
     * @param firewallPolicies firewall policies to create
     * @return list of created firewall policies
     */
    List<FirewallPolicy> createFirewallPolicies(List<FirewallPolicy> firewallPolicies);

    /**
     *  Creates a firewall policy.
     *
     * @param firewallPolicy firewall policy to create
     * @return created firewall policy
     */
    FirewallPolicy createFirewallPolicy(FirewallPolicy firewallPolicy);

    /**
     * Update an existing firewall policy
     *
     * @param uuid uuid of policy to update
     * @param firewallPolicy firewall policy data to update
     * @return updated firewall policy
     */
    FirewallPolicy editFirewallPolicy(String uuid, FirewallPolicy firewallPolicy);

    /**
     * Gets the list of subscriptions of the user.
     *
     * @return list of subscriptions of the user.
     */
    List<Subscription> listSubscriptions();

    /**
     * The extensions are listed in the ‘descendants’ attribute and the end_time is that of the last subscription in the chain.
     *
     * @return only the first subscriptions from a chain of extended subscriptions.
     */
    List<Subscription> listGroupedSubscriptions();

    /**
     * This is identical to the listSubscriptions(), except that subscriptions are not actually bought.
     *
     * @return list of subscriptions that are not actually bought.
     */
    List<Subscription> listSubscriptionsCalculator();

    /**
     * Creates a new subscription.
     * Subscription times are rounded to noon UTC, using the following rules:
     *      - End time is always rounded to the next noon.
     *      - Start time is rounded to the maximum between the current time an the previous noon.
     *      This means that subscriptions bought for now do start now, but subscriptions for the future start at the previous noon.
     *
     * @param subscriptionRequest subscription request object
     * @return craeted subscription
     */
    Subscription createSubscription(CreateSubscriptionRequest subscriptionRequest);

    /**
     * Creates a new subscription. There is a limit of 500 subscriptions that can be purchased in one request.
     * Subscription times are rounded to noon UTC, using the following rules:
     *      - End time is always rounded to the next noon.
     *      - Start time is rounded to the maximum between the current time an the previous noon.
     *      This means that subscriptions bought for now do start now, but subscriptions for the future start at the previous noon.
     *
     * @param requestList
     * @return
     */
    List<Subscription> createSubscriptions(List<CreateSubscriptionRequest> requestList);

    /**
     * Extends the subscription. An extended subscription is actually just another subscription that is linked to the original
     * If a period or and end_time are specified in the request,they are used. If neither are specified,
     * the creation length of the subscription is used.
     *
     * A caveat to this is that a subscription created initially with an end_time, the exact interval is used.
     * Subscriptions that are created with a period have the period parsed again in the context of the new start_time.
     * An example would be a subscription created on the 1st of February with a period of ‘1 month’ will be extended for 31 days,
     * but one that was created with an end date of 1st of March will be extended for 28 days.
     *
     * If the specified subscription has actually been extended, it traverses and extends the last subscription in the chain.
     *
     * @param id id of subscription to extend
     */
    void extendSubscription(String id);

    /**
     * Toggles the autorenew flag of the subscription.
     *
     * @param id id of subscription to enable autorenew
     */
    void enableSubscriptionAutorenew(String id);

    /**
     * Gets the pricing information that are applicable to the cloud. Subscription prices use a burst level of 0.
     *
     * @return pricing information that are applicable to the cloud.
     */
    Pricing getPricing();

    /**
     * Get discount information.
     *
     * @return discount information.
     */
    List<Discount> listDiscounts();

    /**
     * Get the transactions for the account.
     *
     * @return transactions for the account.
     */
    List<Transaction> listTransactions();

    /**
     * Get the licenses available on the cloud. The type of the license can be one of:
     *      install - These licenses are billed per installation, regardless of whether it is attached to a running guests or not.
     *      instance - These licenses are billed per running instance of a guest. A license attached to a guest that’s stopped is not billed.
     *      stub - These licenses are billed per a metric specified by the customer (i.e. per number of users license)
     *
     * The user metric field specifies what attribute on the instance of the guest is used for determining the number of licenses.
     * For example, “smp” will count one license for each CPU/core in the virtual machine.
     *
     * @return licenses available on the cloud
     */
    List<License> listLicenses();
}
