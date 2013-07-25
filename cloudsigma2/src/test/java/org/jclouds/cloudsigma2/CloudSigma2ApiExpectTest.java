package org.jclouds.cloudsigma2;

import com.google.common.collect.ImmutableList;
import org.jclouds.cloudsigma2.domain.AccountBalance;
import org.jclouds.cloudsigma2.domain.CurrentUsage;
import org.jclouds.cloudsigma2.domain.Discount;
import org.jclouds.cloudsigma2.domain.Drive;
import org.jclouds.cloudsigma2.domain.DriveInfo;
import org.jclouds.cloudsigma2.domain.FirewallPolicy;
import org.jclouds.cloudsigma2.domain.IPInfo;
import org.jclouds.cloudsigma2.domain.LibraryDrive;
import org.jclouds.cloudsigma2.domain.License;
import org.jclouds.cloudsigma2.domain.Pricing;
import org.jclouds.cloudsigma2.domain.ProfileInfo;
import org.jclouds.cloudsigma2.domain.Server;
import org.jclouds.cloudsigma2.domain.ServerAvailabilityGroup;
import org.jclouds.cloudsigma2.domain.ServerInfo;
import org.jclouds.cloudsigma2.domain.Subscription;
import org.jclouds.cloudsigma2.domain.Tag;
import org.jclouds.cloudsigma2.domain.VLANInfo;
import org.jclouds.http.HttpRequest;
import org.jclouds.http.HttpResponse;
import org.jclouds.rest.internal.BaseRestApiExpectTest;
import org.testng.annotations.Test;

import javax.ws.rs.core.MediaType;
import java.math.BigInteger;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * @author Vladimir Shevchenko
 */
@Test(groups = "unit")
public class CloudSigma2ApiExpectTest extends BaseRestApiExpectTest<CloudSigma2Api> {

    protected String endpoint = "https://zrh.cloudsigma.com/api/2.0/";

    public CloudSigma2ApiExpectTest() {
        provider = "cloudsigma2";
    }

    protected HttpRequest.Builder<?> getBuilder() {
        return HttpRequest.builder()
                .method("GET")
                .addHeader("Accept", MediaType.APPLICATION_JSON)
                .addHeader("Authorization", "Basic aWRlbnRpdHk6Y3JlZGVudGlhbA==");
    }

    protected HttpRequest.Builder<?> postBuilder() {
        return HttpRequest.builder()
                .method("POST")
                .addHeader("Accept", MediaType.APPLICATION_JSON)
                .addHeader("Authorization", "Basic aWRlbnRpdHk6Y3JlZGVudGlhbA==");
    }

    protected HttpRequest.Builder<?> deleteBuilder() {
        return HttpRequest.builder()
                .method("DELETE")
                .addHeader("Accept", MediaType.APPLICATION_JSON)
                .addHeader("Authorization", "Basic aWRlbnRpdHk6Y3JlZGVudGlhbA==");
    }

    protected HttpRequest.Builder<?> putBuilder() {
        return HttpRequest.builder()
                .method("PUT")
                .addHeader("Accept", MediaType.APPLICATION_JSON)
                .addHeader("Authorization", "Basic aWRlbnRpdHk6Y3JlZGVudGlhbA==");
    }

    protected HttpResponse.Builder<?> responseBuilder() {
        return HttpResponse.builder()
                .statusCode(200)
                .message("OK");
    }

    @Test
    public void testListDrives() throws Exception {
        CloudSigma2Api api = requestSendsResponse(
                getBuilder()
                        .endpoint(endpoint + "drives/")
                        .build()
                , responseBuilder()
                .payload(payloadFromResourceWithContentType("/drives.json", MediaType.APPLICATION_JSON))
                .build());

        List<Drive> result = api.listDrives();
        assertNotNull(result);
    }

    @Test
    public void testListDrivesInfo() throws Exception {
        CloudSigma2Api api = requestSendsResponse(
                getBuilder()
                        .endpoint(endpoint + "drives/detail/")
                        .build()
                , responseBuilder()
                .payload(payloadFromResourceWithContentType("/drives-detail.json", MediaType.APPLICATION_JSON))
                .build());

        List<DriveInfo> result = api.listDrivesInfo();
        assertNotNull(result);
    }

    @Test
    public void testGetDriveInfo() throws Exception {
        String uuid = "e96f3c63-6f50-47eb-9401-a56c5ccf6b32";
        CloudSigma2Api api = requestSendsResponse(
                getBuilder()
                        .endpoint(endpoint + "drives/" + uuid + "/")
                        .build()
                , responseBuilder()
                .payload(payloadFromResourceWithContentType("/drives-detail.json", MediaType.APPLICATION_JSON))
                .build());

        DriveInfo result = api.getDriveInfo(uuid);
        assertNotNull(result);
    }

    @Test
    public void testCreateDrive() throws Exception {
        CloudSigma2Api api = requestSendsResponse(
                postBuilder()
                    .payload(payloadFromResourceWithContentType("/drives-create-request.json", MediaType.APPLICATION_JSON))
                    .endpoint(endpoint + "drives/")
                    .build()
                , responseBuilder()
                    .payload(payloadFromResourceWithContentType("/drives-detail.json", MediaType.APPLICATION_JSON))
                    .build());

        DriveInfo result = api.createDrive(new DriveInfo.Builder()
                .media(org.jclouds.cloudsigma2.domain.MediaType.DISK)
                .name("test_drive_0")
                .size(new BigInteger("1024000000"))
                .allowMultimount(false)
                .build());
        assertNotNull(result);
    }

    @Test
    public void testCreateDrives() throws Exception {
        CloudSigma2Api api = requestSendsResponse(
                postBuilder()
                        .payload(payloadFromResourceWithContentType("/drives-create-multiple-request.json", MediaType.APPLICATION_JSON))
                        .endpoint(endpoint + "drives/")
                        .build()
                , responseBuilder()
                .payload(payloadFromResourceWithContentType("/drives-detail.json", MediaType.APPLICATION_JSON))
                .build());

        List<DriveInfo> result = api.createDrives(ImmutableList.of(
                new DriveInfo.Builder()
                        .media(org.jclouds.cloudsigma2.domain.MediaType.DISK)
                        .name("test_drive_0")
                        .size(new BigInteger("1024000000"))
                        .allowMultimount(false)
                        .build()
                , new DriveInfo.Builder()
                .media(org.jclouds.cloudsigma2.domain.MediaType.DISK)
                .name("test_drive_1")
                .size(new BigInteger("1024000000"))
                .allowMultimount(false)
                .build()
                , new DriveInfo.Builder()
                .media(org.jclouds.cloudsigma2.domain.MediaType.DISK)
                .name("test_drive_2")
                .size(new BigInteger("1024000000"))
                .allowMultimount(false)
                .build()));
        assertNotNull(result);
        assertEquals(result.size(), 3);
    }

    @Test
    public void testDeleteDrive() throws Exception {
        String uuid = "e96f3c63-6f50-47eb-9401-a56c5ccf6b32";
        CloudSigma2Api api = requestSendsResponse(
                deleteBuilder()
                        .endpoint(endpoint + "drives/" + uuid + "/")
                        .build()
                , responseBuilder()
                .build());

        api.deleteDrive(uuid);
    }

    @Test
    public void testDeleteDrives() throws Exception {
        List<String> deleteList = ImmutableList.of(
                "b137e217-42b6-4ecf-8575-d72efc2d3dbd"
                ,"e035a488-8587-4a15-ab25-9b7343236bc9"
                ,"feded33c-106f-49fa-a1c4-be5c718ad1b5");

        CloudSigma2Api api = requestSendsResponse(
                deleteBuilder()
                        .endpoint(endpoint + "drives/")
                        .build()
                , responseBuilder()
                .build());

        api.deleteDrives(deleteList);
    }

    @Test
    public void testEditDrive() throws Exception {
        String uuid = "e96f3c63-6f50-47eb-9401-a56c5ccf6b32";
        CloudSigma2Api api = requestSendsResponse(
                putBuilder()
                    .payload(payloadFromResourceWithContentType("/drives-create-request.json", MediaType.APPLICATION_JSON))
                    .endpoint(endpoint + "drives/" + uuid + "/")
                    .build()
                , responseBuilder()
                    .payload(payloadFromResourceWithContentType("/drives-detail.json", MediaType.APPLICATION_JSON))
                    .build());

        DriveInfo result = api.editDrive(uuid, new DriveInfo.Builder()
                .media(org.jclouds.cloudsigma2.domain.MediaType.DISK)
                .name("test_drive_0")
                .size(new BigInteger("1024000000"))
                .allowMultimount(false)
                .build());
        assertNotNull(result);
    }

    @Test
    public void testCloneDrive() throws Exception {
        String uuid = "e96f3c63-6f50-47eb-9401-a56c5ccf6b32";
        CloudSigma2Api api = requestSendsResponse(
                postBuilder()
                        .payload(payloadFromResourceWithContentType("/drives-create-request.json", MediaType.APPLICATION_JSON))
                        .endpoint(endpoint + "drives/" + uuid + "/action/%3Fdo=clone")
                        .build()
                , responseBuilder()
                .payload(payloadFromResourceWithContentType("/drives-detail.json", MediaType.APPLICATION_JSON))
                .build());

        DriveInfo result = api.cloneDrive(uuid, new DriveInfo.Builder()
                .media(org.jclouds.cloudsigma2.domain.MediaType.DISK)
                .name("test_drive_0")
                .size(new BigInteger("1024000000"))
                .allowMultimount(false)
                .build());
        assertNotNull(result);
    }

    @Test
    public void testListLibraryDrives() throws Exception {
        CloudSigma2Api api = requestSendsResponse(
                getBuilder()
                        .endpoint(endpoint + "libdrives/")
                        .build()
                , responseBuilder()
                .payload(payloadFromResourceWithContentType("/libdrives.json", MediaType.APPLICATION_JSON))
                .build());

        List<LibraryDrive> result = api.listLibraryDrives();
        assertNotNull(result);
    }

    @Test
    public void testGetLibraryDrive() throws Exception {
        String uuid = "6d53b92c-42dc-472b-a7b6-7021f45f377a";
        CloudSigma2Api api = requestSendsResponse(
                getBuilder()
                        .endpoint(endpoint + "libdrives/" + uuid + "/")
                        .build()
                , responseBuilder()
                .payload(payloadFromResourceWithContentType("/libdrives-single.json", MediaType.APPLICATION_JSON))
                .build());

        LibraryDrive result = api.getLibraryDrive(uuid);
        assertNotNull(result);
    }

    @Test
    public void testCloneLibraryDrive() throws Exception {
        String uuid = "e96f3c63-6f50-47eb-9401-a56c5ccf6b32";
        CloudSigma2Api api = requestSendsResponse(
                postBuilder()
                        .payload(payloadFromResourceWithContentType("/drives-create-request.json", MediaType.APPLICATION_JSON))
                        .endpoint(endpoint + "libdrives/" + uuid + "/action/%3Fdo=clone")
                        .build()
                , responseBuilder()
                .payload(payloadFromResourceWithContentType("/libdrives-single.json", MediaType.APPLICATION_JSON))
                .build());

        DriveInfo result = api.cloneLibraryDrive(uuid, new LibraryDrive.Builder()
                .media(org.jclouds.cloudsigma2.domain.MediaType.DISK)
                .name("test_drive_0")
                .size(new BigInteger("1024000000"))
                .allowMultimount(false)
                .build());
        assertNotNull(result);
    }

    @Test
    public void testListServers() throws Exception {
        CloudSigma2Api api = requestSendsResponse(
                getBuilder()
                        .endpoint(endpoint + "servers/")
                        .build()
                , responseBuilder()
                .payload(payloadFromResourceWithContentType("/servers.json", MediaType.APPLICATION_JSON))
                .build());

        List<Server> result = api.listServers();
        assertNotNull(result);
    }

    @Test
    public void testListServersInfo() throws Exception {
        CloudSigma2Api api = requestSendsResponse(
                getBuilder()
                        .endpoint(endpoint + "servers/detail/")
                        .build()
                , responseBuilder()
                .payload(payloadFromResourceWithContentType("/server-detail.json", MediaType.APPLICATION_JSON))
                .build());

        List<ServerInfo> result = api.listServersInfo();
        assertNotNull(result);
    }

    @Test
    public void testCreateServer() throws Exception {
        CloudSigma2Api api = requestSendsResponse(
                postBuilder()
                        .endpoint(endpoint + "servers/")
                        .payload(payloadFromResourceWithContentType("/servers-create-request.json", MediaType.APPLICATION_JSON))
                        .build()
                , responseBuilder()
                .payload(payloadFromResourceWithContentType("/server-single.json", MediaType.APPLICATION_JSON))
                .build());

        ServerInfo result = api.createServer(new ServerInfo.Builder()
                .cpu(100)
                .memory(new BigInteger("536870912"))
                .name("testServerAcc")
                .vncPassword("testserver")
                .build());
        assertNotNull(result);
    }

    @Test
    public void testCreateServers() throws Exception {
        //TODO add payoad with list of servers
    }

    @Test
    public void testEditServer() throws Exception {
        String uuid = "a19a425f-9e92-42f6-89fb-6361203071bb";
        CloudSigma2Api api = requestSendsResponse(
                putBuilder()
                        .endpoint(endpoint + "servers/" + uuid + "/")
                        .build()
                , responseBuilder()
                .payload(payloadFromResourceWithContentType("/server-single.json", MediaType.APPLICATION_JSON))
                .build());

        ServerInfo result = api.editServer(uuid, new ServerInfo.Builder().build());
        assertNotNull(result);
    }

    @Test
    public void testDeleteServer() throws Exception {
        String uuid = "a19a425f-9e92-42f6-89fb-6361203071bb";
        CloudSigma2Api api = requestSendsResponse(
                deleteBuilder()
                        .endpoint(endpoint + "servers/" + uuid + "/")
                        .build()
                , responseBuilder()
                .build());

        api.deleteServer(uuid);
    }

    @Test
    public void testDeleteServers() throws Exception {
        //TODO add multiple servers uuids to payload
        CloudSigma2Api api = requestSendsResponse(
                deleteBuilder()
                        .endpoint(endpoint + "servers")
                        .build()
                , responseBuilder()
                .build());

        api.deleteServers(null);
    }

    @Test
    public void testCloneServer() throws Exception {

    }

    @Test
    public void testGetServerInfo() throws Exception {
        String uuid = "61d61337-884b-4c87-b4de-f7f48f9cfc84";
        CloudSigma2Api api = requestSendsResponse(
                getBuilder()
                        .endpoint(endpoint + "servers/" + uuid)
                        .build()
                , responseBuilder()
                .payload(payloadFromResourceWithContentType("/servers-single.json", MediaType.APPLICATION_JSON))
                .build());

        api.getServerInfo(uuid);
    }

    @Test
    public void testStartServer() throws Exception {
        String uuid = "61d61337-884b-4c87-b4de-f7f48f9cfc84";
        CloudSigma2Api api = requestSendsResponse(
                postBuilder()
                        .endpoint(endpoint + "servers/" + uuid + "/action/?do=start")
                        .build()
                , responseBuilder()
                .build());

        api.startServer(uuid);
    }

    @Test
    public void testStopServer() throws Exception {
        String uuid = "61d61337-884b-4c87-b4de-f7f48f9cfc84";
        CloudSigma2Api api = requestSendsResponse(
                postBuilder()
                        .endpoint(endpoint + "servers/" + uuid + "/action/?do=stop")
                        .build()
                , responseBuilder()
                .build());

        api.stopServer(uuid);
    }

    @Test
    public void testStartServerInSeparateAvailabilityGroup() throws Exception {
        String uuid = "61d61337-884b-4c87-b4de-f7f48f9cfc84";
        ServerAvailabilityGroup uuidGroup = new ServerAvailabilityGroup(ImmutableList.of(
                "313e73a4-592f-48cf-81c4-a6c079d005a5",
                "e035a488-8587-4a15-ab25-9b7343236bc9"));
        CloudSigma2Api api = requestSendsResponse(
                postBuilder()
                        .endpoint(endpoint + "servers/" + uuid + "/action/?do=start&avoid=" + uuidGroup)
                        .build()
                , responseBuilder()
                .build());

        api.startServerInSeparateAvailabilityGroup(uuid, uuidGroup);
    }

    @Test
    public void testOpenServerVNCTunnel() throws Exception {
        String uuid = "61d61337-884b-4c87-b4de-f7f48f9cfc84";
        CloudSigma2Api api = requestSendsResponse(
                postBuilder()
                        .endpoint(endpoint + "servers/" + uuid + "/action/?do=open_vnc")
                        .build()
                , responseBuilder()
                .build());

        api.openServerVNCTunnel(uuid);
    }

    @Test
    public void testCloseServerVCNTunnel() throws Exception {
        String uuid = "61d61337-884b-4c87-b4de-f7f48f9cfc84";
        CloudSigma2Api api = requestSendsResponse(
                postBuilder()
                        .endpoint(endpoint + "servers/" + uuid + "/action/?do=close_vnc")
                        .build()
                , responseBuilder()
                .build());

        api.closeServerVCNTunnel(uuid);
    }

    @Test
    public void testListServerAvailabilityGroup() throws Exception {
        CloudSigma2Api api = requestSendsResponse(
                getBuilder()
                        .endpoint(endpoint + "servers/availability_groups/")
                        .build()
                , responseBuilder()
                .payload(payloadFromResourceWithContentType("/servers-availability-groups.json", MediaType.APPLICATION_JSON))
                .build());

        List<ServerAvailabilityGroup> result = api.listServerAvailabilityGroup();
        assertNotNull(result);
    }

    @Test
    public void testGetServerAvailabilityGroup() throws Exception {
        CloudSigma2Api api = requestSendsResponse(
                getBuilder()
                        .endpoint(endpoint + "fwpolicies/")
                        .build()
                , responseBuilder()
                .payload(payloadFromResourceWithContentType("/fwpolicies-detail.json", MediaType.APPLICATION_JSON))
                .build());

        List<FirewallPolicy> result = api.listFirewallPolicies();
        assertNotNull(result);
    }

    @Test
    public void testListFirewallPolicies() throws Exception {
        CloudSigma2Api api = requestSendsResponse(
                getBuilder()
                        .endpoint(endpoint + "fwpolicies/")
                        .build()
                , responseBuilder()
                .payload(payloadFromResourceWithContentType("/fwpolicies-detail.json", MediaType.APPLICATION_JSON))
                .build());

        List<FirewallPolicy> result = api.listFirewallPolicies();
        assertNotNull(result);
    }

    @Test
    public void testListFirewallPoliciesInfo() throws Exception {
        CloudSigma2Api api = requestSendsResponse(
                getBuilder()
                        .endpoint(endpoint + "fwpolicies/detail/")
                        .build()
                , responseBuilder()
                .payload(payloadFromResourceWithContentType("/fwpolicies-detail.json", MediaType.APPLICATION_JSON))
                .build());

        List<FirewallPolicy> result = api.listFirewallPoliciesInfo();
        assertNotNull(result);
    }

    @Test
    public void testCreateFirewallPolicies() throws Exception {
        //TODO get payload from docs
    }

    @Test
    public void testCreateFirewallPolicy() throws Exception {
        //TODO get payload from docs
    }

    @Test
    public void testEditFirewallPolicy() throws Exception {
        //TODO get payload from docs
    }

    @Test
    public void testGetVLANInfo() throws Exception {
        String uuid = "96537817-f4b6-496b-a861-e74192d3ccb0";
        CloudSigma2Api api = requestSendsResponse(
                getBuilder()
                        .endpoint(endpoint + "vlans/" + uuid + "/")
                        .build()
                , responseBuilder()
                .payload(payloadFromResourceWithContentType("/vlan-single.json", MediaType.APPLICATION_JSON))
                .build());

        VLANInfo result = api.getVLANInfo(uuid);
        assertNotNull(result);
    }

    @Test
    public void testListVLANs() throws Exception {
        CloudSigma2Api api = requestSendsResponse(
                getBuilder()
                        .endpoint(endpoint + "vlans/")
                        .build()
                , responseBuilder()
                .payload(payloadFromResourceWithContentType("/vlans.json", MediaType.APPLICATION_JSON))
                .build());

        List<VLANInfo> result = api.listVLANs();
        assertNotNull(result);
    }

    @Test
    public void testListVLANInfo() throws Exception {
        CloudSigma2Api api = requestSendsResponse(
                getBuilder()
                        .endpoint(endpoint + "vlans/detail/")
                        .build()
                , responseBuilder()
                .payload(payloadFromResourceWithContentType("/vlans.json", MediaType.APPLICATION_JSON))
                .build());

        List<VLANInfo> result = api.listVLANInfo();
        assertNotNull(result);
    }

    @Test
    public void testEditVLAN() throws Exception {
        //TODO get payload from docs
    }

    @Test
    public void testListIPs() throws Exception {
        CloudSigma2Api api = requestSendsResponse(
                getBuilder()
                        .endpoint(endpoint + "ips/")
                        .build()
                , responseBuilder()
                .payload(payloadFromResourceWithContentType("/ips.json", MediaType.APPLICATION_JSON))
                .build());

        List<IPInfo> result = api.listIPInfo();
        assertNotNull(result);
    }

    @Test
    public void testListIPInfo() throws Exception {
        CloudSigma2Api api = requestSendsResponse(
                getBuilder()
                        .endpoint(endpoint + "ips/detail/")
                        .build()
                , responseBuilder()
                .payload(payloadFromResourceWithContentType("/ips.json", MediaType.APPLICATION_JSON))
                .build());

        List<IPInfo> result = api.listIPInfo();
        assertNotNull(result);
    }

    @Test
    public void testGetIPInfo() throws Exception {
        String uuid = "185.12.6.183";
        CloudSigma2Api api = requestSendsResponse(
                getBuilder()
                        .endpoint(endpoint + "ips/" + uuid + "/")
                        .build()
                , responseBuilder()
                .payload(payloadFromResourceWithContentType("/ips-single.json", MediaType.APPLICATION_JSON))
                .build());

        IPInfo result = api.getIPInfo(uuid);
        assertNotNull(result);
    }

    @Test
    public void testEditIP() throws Exception {
        //TODO get payload from docs
    }

    @Test
    public void testListTags() throws Exception {
        CloudSigma2Api api = requestSendsResponse(
                getBuilder()
                        .endpoint(endpoint + "tags/")
                        .build()
                , responseBuilder()
                .payload(payloadFromResourceWithContentType("/tags-detail.json", MediaType.APPLICATION_JSON))
                .build());

        List<Tag> result = api.listTags();
        assertNotNull(result);
    }

    @Test
    public void testListTagsInfo() throws Exception {
        CloudSigma2Api api = requestSendsResponse(
                getBuilder()
                        .endpoint(endpoint + "tags/detail/")
                        .build()
                , responseBuilder()
                .payload(payloadFromResourceWithContentType("/tags-detail.json", MediaType.APPLICATION_JSON))
                .build());

        List<Tag> result = api.listTagsInfo();
        assertNotNull(result);
    }

    @Test
    public void testGetTagInfo() throws Exception {
        String uuid = "68bb0cfc-0c76-4f37-847d-7bb705c5ae46";
        CloudSigma2Api api = requestSendsResponse(
                getBuilder()
                        .endpoint(endpoint + "tags/" + uuid + "/")
                        .build()
                , responseBuilder()
                .payload(payloadFromResourceWithContentType("/tags-single.json", MediaType.APPLICATION_JSON))
                .build());

        Tag result = api.getTagInfo(uuid);
        assertNotNull(result);
    }

    @Test
    public void testEditTag() throws Exception {
        //TODO get payload from docs
    }

    @Test
    public void testCreateTag() throws Exception {
        //TODO get payload from docs
    }

    @Test
    public void testDeleteTag() throws Exception {
        String uuid = "956e2ca0-dee3-4b3f-a1be-a6e86f90946f";
        CloudSigma2Api api = requestSendsResponse(
                deleteBuilder()
                        .endpoint(endpoint + "/tags/" + uuid + "/")
                        .build()
                , responseBuilder()
                .payload(payloadFromResourceWithContentType("/profile.json", MediaType.APPLICATION_JSON))
                .build());

        api.deleteTag(uuid);
    }

    @Test
    public void testDeleteTags() throws Exception {
        //TODO get payload from docs
    }

    @Test
    public void testCreateTags() throws Exception {
        //TODO get payload from docs
    }

    @Test
    public void testGetProfileInfo() throws Exception {
        CloudSigma2Api api = requestSendsResponse(
                getBuilder()
                        .endpoint(endpoint + "profile/")
                        .build()
                , responseBuilder()
                .payload(payloadFromResourceWithContentType("/profile.json", MediaType.APPLICATION_JSON))
                .build());

        ProfileInfo result = api.getProfileInfo();
        assertNotNull(result);
    }

    @Test
    public void testEditProfileInfo() throws Exception {
        //TODO get payload from docs
    }

    @Test
    public void testGetAccountBalance() throws Exception {
        CloudSigma2Api api = requestSendsResponse(
                getBuilder()
                        .endpoint(endpoint + "balance/")
                        .build()
                , responseBuilder()
                .payload(payloadFromResourceWithContentType("/balance.json", MediaType.APPLICATION_JSON))
                .build());

        AccountBalance result = api.getAccountBalance();
        assertNotNull(result);
    }

    @Test
    public void testGetCurrentUsage() throws Exception {
        CloudSigma2Api api = requestSendsResponse(
                getBuilder()
                        .endpoint(endpoint + "currentusage/")
                        .build()
                , responseBuilder()
                .payload(payloadFromResourceWithContentType("/currentusage.json", MediaType.APPLICATION_JSON))
                .build());

        CurrentUsage result = api.getCurrentUsage();
        assertNotNull(result);
    }

    @Test
    public void testListSubscriptions() throws Exception {
        CloudSigma2Api api = requestSendsResponse(
                getBuilder()
                        .endpoint(endpoint + "subscriptions/")
                        .build()
                , responseBuilder()
                .payload(payloadFromResourceWithContentType("/subscriptions.json", MediaType.APPLICATION_JSON))
                .build());

        List<Subscription> result = api.listSubscriptions();
        assertNotNull(result);
    }

    @Test
    public void testListGroupedSubscriptions() throws Exception {
        CloudSigma2Api api = requestSendsResponse(
                getBuilder()
                        .endpoint(endpoint + "groupedsubscriptions/")
                        .build()
                , responseBuilder()
                .payload(payloadFromResourceWithContentType("/subscriptions.json", MediaType.APPLICATION_JSON))
                .build());

        List<Subscription> result = api.listGroupedSubscriptions();
        assertNotNull(result);
    }

    @Test
    public void testListSubscriptionsCalculator() throws Exception {
        CloudSigma2Api api = requestSendsResponse(
                getBuilder()
                        .endpoint(endpoint + "subscriptioncalculator/")
                        .build()
                , responseBuilder()
                .payload(payloadFromResourceWithContentType("/subscriptions.json", MediaType.APPLICATION_JSON))
                .build());

        List<Subscription> result = api.listSubscriptionsCalculator();
        assertNotNull(result);
    }

    @Test
    public void testCreateSubscription() throws Exception {
        //TODO get payload from documentation
    }

    @Test
    public void testCreateSubscriptions() throws Exception {
        //TODO get payload from documentation
    }

    @Test
    public void testExtendSubscription() throws Exception {
        String uuid = "509f8e27-1e64-49bb-aa5a-baec074b0210";
        CloudSigma2Api api = requestSendsResponse(
                postBuilder()
                        .endpoint(endpoint + "subscriptions/" + uuid + "/action/?do=extend")
                        .build()
                , responseBuilder()
                .payload(payloadFromResourceWithContentType("/subscriptions-single.json", MediaType.APPLICATION_JSON))
                .build());

        api.extendSubscription(uuid);
    }

    @Test
    public void testEnableSubscriptionAutorenew() throws Exception {
        String uuid = "509f8e27-1e64-49bb-aa5a-baec074b0210";
        CloudSigma2Api api = requestSendsResponse(
                postBuilder()
                        .endpoint(endpoint + "subscriptions/" +  uuid +"/action/?do=auto_renew")
                        .build()
                , responseBuilder()
                .payload(payloadFromResourceWithContentType("/pricing.json", MediaType.APPLICATION_JSON))
                .build());

        api.enableSubscriptionAutorenew(uuid);
    }

    @Test
    public void testGetPricing() throws Exception {
        CloudSigma2Api api = requestSendsResponse(
                getBuilder()
                        .endpoint(endpoint + "pricing/")
                        .build()
                , responseBuilder()
                .payload(payloadFromResourceWithContentType("/pricing.json", MediaType.APPLICATION_JSON))
                .build());

        Pricing result = api.getPricing();
        assertNotNull(result);
    }

    @Test
    public void testListDiscounts() throws Exception {
        CloudSigma2Api api = requestSendsResponse(
                getBuilder()
                        .endpoint(endpoint + "discount/")
                        .build()
                , responseBuilder()
                .payload(payloadFromResourceWithContentType("/discount.json", MediaType.APPLICATION_JSON))
                .build());

        List<Discount> result = api.listDiscounts();
        assertNotNull(result);
    }

    @Test
    public void testListTransactions() throws Exception {
        CloudSigma2Api api = requestSendsResponse(
                getBuilder()
                        .endpoint(endpoint + "transactions/")
                        .build()
                , responseBuilder()
                .payload(payloadFromResourceWithContentType("/transactions.json", MediaType.APPLICATION_JSON))
                .build());

        List<License> result = api.listLicenses();
        assertNotNull(result);
    }

    @Test
    public void testListLicenses() throws Exception {
        CloudSigma2Api api = requestSendsResponse(
                getBuilder()
                        .endpoint(endpoint + "licenses/")
                        .build()
                , responseBuilder()
                .payload(payloadFromResourceWithContentType("/licenses.json", MediaType.APPLICATION_JSON))
                .build());

        List<License> result = api.listLicenses();
        assertNotNull(result);
    }
}
