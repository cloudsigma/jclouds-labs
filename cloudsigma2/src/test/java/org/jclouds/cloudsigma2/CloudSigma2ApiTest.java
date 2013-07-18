package org.jclouds.cloudsigma2;

import com.google.common.collect.ImmutableList;
import org.jclouds.apis.BaseApiLiveTest;
import org.jclouds.cloudsigma2.domain.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigInteger;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Vladimir Shevchenko
 */
@Test(groups = "live")
public class CloudSigma2ApiTest extends BaseApiLiveTest<CloudSigma2Api> {

    public CloudSigma2ApiTest() {
        provider = "cloudsigma2";
    }

    @Test
    public void testListDrives() throws Exception {
        Assert.assertNotNull(api.listDrives());
    }

    @Test
    public void testListDrivesInfo() throws Exception {
        Assert.assertNotNull(api.listDrivesInfo());
    }

    @Test
    public void testGetDriveInfo() throws Exception {
        for(Drive driveInfo : api.listDrives()){
            Assert.assertNotNull(api.getDriveInfo(driveInfo.getUuid()));
        }
    }

    @Test
    public void testCreateDrive() throws Exception {
        DriveInfo newDrive = new DriveInfo.Builder()
                .name("test drive")
                .size(new BigInteger("2073741824"))
                .media(MediaType.DISK)
                .build();
        DriveInfo created = api.createDrive(newDrive);
        checkDrive(newDrive, created);
    }

    @Test
    public void testCreateDrives() throws Exception {
        List<DriveInfo> newDrives = ImmutableList.of(new DriveInfo.Builder()
                .name("New Drive")
                .size(new BigInteger("2073741824"))
                .media(MediaType.DISK)
                .build()
                , new DriveInfo.Builder()
                .name("Test Drive")
                .size(new BigInteger("6073741824"))
                .media(MediaType.DISK)
                .build());

        List<DriveInfo> createdDrives = api.createDrives(newDrives);
        Assert.assertEquals(newDrives.size(), createdDrives.size());

        for(int i = 0; i < newDrives.size(); i++){
            checkDrive(newDrives.get(i), createdDrives.get(i));
        }
    }

    @Test
    public void testEditDrive() throws Exception {
        DriveInfo editedDrive = new DriveInfo.Builder()
                .name("Edited Drive")
                .size(new BigInteger("4073741824"))
                .media(MediaType.DISK)
                .build();

        checkDrive(editedDrive, api.editDrive(api.listDrives().get(0).getUuid(), editedDrive));
    }

    @Test
    public void testListLibraryDrives() throws Exception {
        Assert.assertNotNull(api.listLibraryDrives());
    }

    @Test
    public void testGetLibraryDrive() throws Exception {
        for(LibraryDrive libraryDrive : api.listLibraryDrives()){
            Assert.assertNotNull(libraryDrive.getUuid());
        }
    }

    @Test
    public void testListServers() throws Exception {
        Assert.assertNotNull(api.listServers());
    }

    @Test
    public void testListServersInfo() throws Exception {
        Assert.assertNotNull(api.listServersInfo());
    }

    @Test
    public void testCreateServer() throws Exception {
        ServerInfo serverInfo = new ServerInfo.Builder()
                .name("New Server")
                .memory(new BigInteger("5368709120"))
                .cpu(3000)
                .vncPassword("new_password")
                .drives(ImmutableList.of(api.listDrives().get(0).toServerDrive(1, "0:1", DeviceEmulationType.IDE)))
                .build();

        checkServer(serverInfo, api.createServer(serverInfo));
    }

    @Test
    public void testCreateServers() throws Exception {
        List<ServerInfo> newServerList = ImmutableList.of(
                new ServerInfo.Builder()
                        .name("New Server")
                        .memory(new BigInteger("5368709120"))
                        .cpu(3000)
                        .vncPassword("new_password")
                        .build()
                , new ServerInfo.Builder()
                .name("Test Server")
                .memory(new BigInteger("5368709120"))
                .cpu(3000)
                .vncPassword("test_password")
                .build());

        List<ServerInfo> createsServerList = api.createServers(newServerList);
        Assert.assertEquals(newServerList.size(), createsServerList.size());

        for(int i = 0; i < newServerList.size(); i++){
            checkServer(newServerList.get(i), createsServerList.get(i));
        }
    }

    @Test
    public void testEditServer() throws Exception {
        ServerInfo serverInfo = new ServerInfo.Builder()
                .name("Edited Server")
                .memory(new BigInteger("5368709120"))
                .cpu(2000)
                .vncPassword("edited_password")
                .build();

        checkServer(serverInfo, api.editServer(api.listServers().get(0).getUuid(), serverInfo));
    }

    @Test
    public void testGetServerInfo() throws Exception {
        for(Server server : api.listServers()){
            Assert.assertNotNull(server.getUuid());
        }
    }

    @Test
    public void testListFirewallPolicies() throws Exception {
        Assert.assertNotNull(api.listFirewallPolicies());
    }

    @Test
    public void testListFirewallPoliciesInfo() throws Exception {
        Assert.assertNotNull(api.listFirewallPoliciesInfo());
    }

    @Test
    public void testCreateFirewallPolicies() throws Exception {
        List<FirewallPolicy> newFirewallPolicies = ImmutableList.of(
                new FirewallPolicy.Builder()
                        .name("My awesome policy")
                        .rules(ImmutableList.of(
                                new FirewallRule.Builder()
                                        .action(FirewallAction.DROP)
                                        .comment("Drop traffic from the VM to IP address 23.0.0.0/32")
                                        .direction(FirewallDirection.OUT)
                                        .destinationIp("23.0.0.0/32")
                                        .build()
                                , new FirewallRule.Builder()
                                .action(FirewallAction.ACCEPT)
                                .comment("Allow SSH traffic to the VM from our office in Dubai")
                                .direction(FirewallDirection.IN)
                                .destinationPort("22")
                                .ipProtocol(FirewallIpProtocol.TCP)
                                .sourceIp("172.66.32.0/24")
                                .build()
                                , new FirewallRule.Builder()
                                .action(FirewallAction.DROP)
                                .comment("Drop all other SSH traffic to the VM")
                                .direction(FirewallDirection.IN)
                                .destinationPort("22")
                                .ipProtocol(FirewallIpProtocol.TCP)
                                .build()
                                , new FirewallRule.Builder()
                                .action(FirewallAction.DROP)
                                .comment("Drop all UDP traffic to the VM, not originating from 172.66.32.55")
                                .direction(FirewallDirection.IN)
                                .ipProtocol(FirewallIpProtocol.UDP)
                                .sourceIp("!172.66.32.55/32")
                                .build()
                                , new FirewallRule.Builder()
                                .action(FirewallAction.DROP)
                                .comment("Drop any traffic, to the VM with destination port not between 1-1024")
                                .direction(FirewallDirection.IN)
                                .destinationPort("!1:1024")
                                .ipProtocol(FirewallIpProtocol.TCP)
                                .build()
                        ))
                        .build()
                , new FirewallPolicy.Builder()
                .name("New policy")
                .rules(ImmutableList.of(
                        new FirewallRule.Builder()
                                .action(FirewallAction.ACCEPT)
                                .comment("Test comment")
                                .direction(FirewallDirection.IN)
                                .destinationIp("192.168.1.132/32")
                                .destinationPort("1233")
                                .ipProtocol(FirewallIpProtocol.TCP)
                                .sourceIp("255.255.255.12/32")
                                .sourcePort("321")
                                .build()
                ))
                .build());

        List<FirewallPolicy> createdFirewallPolicyList = api.createFirewallPolicies(newFirewallPolicies);
        Assert.assertEquals(newFirewallPolicies.size(), createdFirewallPolicyList.size());

        for(int i = 0; i < newFirewallPolicies.size(); i++){
            checkFirewallPolicy(newFirewallPolicies.get(i), createdFirewallPolicyList.get(i));
        }
    }

    @Test
    public void testCreateFirewallPolicy() throws Exception {
        FirewallPolicy newFirewallPolicy = new FirewallPolicy.Builder()
                .name("My awesome policy")
                .rules(ImmutableList.of(
                        new FirewallRule.Builder()
                                .action(FirewallAction.DROP)
                                .comment("Drop traffic from the VM to IP address 23.0.0.0/32")
                                .direction(FirewallDirection.OUT)
                                .destinationIp("23.0.0.0/32")
                                .build()
                        , new FirewallRule.Builder()
                        .action(FirewallAction.ACCEPT)
                        .comment("Allow SSH traffic to the VM from our office in Dubai")
                        .direction(FirewallDirection.IN)
                        .destinationPort("22")
                        .ipProtocol(FirewallIpProtocol.TCP)
                        .sourceIp("172.66.32.0/24")
                        .build()
                        , new FirewallRule.Builder()
                        .action(FirewallAction.DROP)
                        .comment("Drop all other SSH traffic to the VM")
                        .direction(FirewallDirection.IN)
                        .destinationPort("22")
                        .ipProtocol(FirewallIpProtocol.TCP)
                        .build()
                        , new FirewallRule.Builder()
                        .action(FirewallAction.DROP)
                        .comment("Drop all UDP traffic to the VM, not originating from 172.66.32.55")
                        .direction(FirewallDirection.IN)
                        .ipProtocol(FirewallIpProtocol.UDP)
                        .sourceIp("!172.66.32.55/32")
                        .build()
                        , new FirewallRule.Builder()
                        .action(FirewallAction.DROP)
                        .comment("Drop any traffic, to the VM with destination port not between 1-1024")
                        .direction(FirewallDirection.IN)
                        .destinationPort("!1:1024")
                        .ipProtocol(FirewallIpProtocol.TCP)
                        .build()
                ))
                .build();

        checkFirewallPolicy(newFirewallPolicy, api.createFirewallPolicy(newFirewallPolicy));
    }

    @Test
    public void testEditFirewallPolicy() throws Exception {
        FirewallPolicy editedPolicy = new FirewallPolicy.Builder()
                .name("Edited policy")
                .rules(ImmutableList.of(
                        new FirewallRule.Builder()
                                .action(FirewallAction.ACCEPT)
                                .comment("Edited policy rule comment")
                                .direction(FirewallDirection.IN)
                                .destinationIp("192.168.1.132/32")
                                .destinationPort("1233")
                                .ipProtocol(FirewallIpProtocol.TCP)
                                .sourceIp("255.255.255.12/32")
                                .sourcePort("321")
                                .build()
                ))
                .build();

        checkFirewallPolicy(editedPolicy, api.editFirewallPolicy(api.listFirewallPolicies().get(0).getUuid(), editedPolicy));
    }

    @Test
    public void testListVLANs() throws Exception {
        Assert.assertNotNull(api.listVLANs());
    }

    @Test
    public void testListVLANInfo() throws Exception {
        Assert.assertNotNull(api.listVLANInfo());
    }

    @Test
    public void testGetVLANInfo() throws Exception {
        for(VLANInfo vlanInfo : api.listVLANs()){
            Assert.assertNotNull(vlanInfo.getUuid());
        }
    }

    @Test
    public void testEditVLAN() throws Exception {
        Map<String, String> meta = new HashMap<String, String>();
        meta.put("test", "test data");

        VLANInfo vlanInfo = new VLANInfo.Builder()
                                        .meta(meta)
                                        .build();

        if(api.listVLANs().size() > 0){
            checkVlAN(vlanInfo, api.editVLAN(api.listVLANs().get(0).getUuid(), vlanInfo));
        }
    }

    @Test
    public void testListIPs() throws Exception {
        Assert.assertNotNull(api.listIPs());
    }

    @Test
    public void testListIPInfo() throws Exception {
        Assert.assertNotNull(api.listIPInfo());
    }

    @Test
    public void testGetIPInfo() throws Exception {
        for(IP ip : api.listIPs()){
            Assert.assertNotNull(api.getIPInfo(ip.getUuid()));
        }
    }

    @Test
    public void testEditIP() throws Exception {
        Map<String, String> meta = new HashMap<String, String>();
        meta.put("test", "test data");

        IPInfo ip = new IPInfo.Builder()
                .meta(meta)
                .build();

        if(api.listIPs().size() > 0){
            checkIP(ip, api.editIP(api.listIPs().get(0).getUuid(), ip));
        }
    }

    @Test
    public void testListTags() throws Exception {
        Assert.assertNotNull(api.listTags());
    }

    @Test
    public void testListTagsInfo() throws Exception {
        Assert.assertNotNull(api.listTagsInfo());
    }

    @Test
    public void testGetTagInfo() throws Exception {
        for(Tag tag : api.listTags()){
            Assert.assertNotNull(api.getTagInfo(tag.getUuid()));
        }
    }

    @Test
    public void testEditTag() throws Exception {
        Map<String, String> meta = new HashMap<String, String>();
        meta.put("description", "test tag");

        Tag editedTag = new Tag.Builder()
                .meta(meta)
                .name("EditedTag")
                .resources(ImmutableList.of(
                        new TagResource.Builder()
                                .uuid("96537817-f4b6-496b-a861-e74192d3ccb0")
                                .build()
                        , new TagResource.Builder()
                                .uuid("61bcc398-c034-42f1-81c9-f6d7f62c4ea0")
                                .build()
                        , new TagResource.Builder()
                                .uuid("3610d935-514a-4552-acf3-a40dd0a5f961")
                                .build()
                        , new TagResource.Builder()
                                .uuid("185.12.6.183")
                                .build()
                ))
                .build();

        if(api.listTags().size() > 0){
            checkTag(editedTag, api.editTag(api.listTags().get(0).getUuid(), editedTag));
        }
    }

    @Test
    public void testCreateTag() throws Exception {
        Map<String, String> meta = new HashMap<String, String>();
        meta.put("description", "test tag");

        Tag newTag = new Tag.Builder()
                .meta(meta)
                .name("EditedTag")
                .resources(ImmutableList.of(
                        new TagResource.Builder()
                                .uuid("96537817-f4b6-496b-a861-e74192d3ccb0")
                                .build()
                        , new TagResource.Builder()
                        .uuid("61bcc398-c034-42f1-81c9-f6d7f62c4ea0")
                        .build()
                        , new TagResource.Builder()
                        .uuid("3610d935-514a-4552-acf3-a40dd0a5f961")
                        .build()
                        , new TagResource.Builder()
                        .uuid("185.12.6.183")
                        .build()
                ))
                .build();

        checkTag(newTag, api.createTag(newTag));
    }

    @Test
    public void testCreateTags() throws Exception {
        List<Tag> newTagsList = ImmutableList.of(
                new Tag.Builder()
                        .name("new tag")
                        .build()
                , new Tag.Builder()
                        .name("TagCreatedWithResource")
                        .resourceUri(new URI("/api/2.0/tags/68bb0cfc-0c76-4f37-847d-7bb705c5ae46/"))
                        .resources(ImmutableList.of(
                                new TagResource.Builder()
                                        .uuid("96537817-f4b6-496b-a861-e74192d3ccb0")
                                        .build()
                                , new TagResource.Builder()
                                .uuid("61bcc398-c034-42f1-81c9-f6d7f62c4ea0")
                                .build()
                                , new TagResource.Builder()
                                .uuid("3610d935-514a-4552-acf3-a40dd0a5f961")
                                .build()
                                , new TagResource.Builder()
                                .uuid("185.12.6.183")
                                .build()
                ))
                .build());

        List<Tag> createdTagList = api.createTags(newTagsList);
        Assert.assertEquals(newTagsList.size(), createdTagList.size());

        for(int i = 0; i < newTagsList.size(); i++){
            checkTag(newTagsList.get(i), createdTagList.get(i));
        }
    }

    @Test
    public void testGetProfileInfo() throws Exception {
        Assert.assertNotNull(api.getProfileInfo());
    }

    @Test
    public void testEditProfileInfo() throws Exception {
        ProfileInfo profileInfo = new ProfileInfo.Builder()
                                                .address("edited address")
                .bankReference("sigma111")
                .company("Awesome company")
                .country("USA")
                .email("user@example.com")
                .firstName("Tim")
                .lastName("Testersson")
                .build();

        checkProfileInfo(profileInfo, api.editProfileInfo(profileInfo));
    }

    @Test
    public void testGetAccountBalance() throws Exception {
        Assert.assertNotNull(api.getAccountBalance());
    }

    @Test
    public void testGetCurrentUsage() throws Exception {
        Assert.assertNotNull(api.getCurrentUsage());
    }

    @Test
    public void testListSubscriptions() throws Exception {
        Assert.assertNotNull(api.listSubscriptions());
    }

    @Test
    public void testListGroupedSubscriptions() throws Exception {
        Assert.assertNotNull(api.listGroupedSubscriptions());
    }

    @Test
    public void testListSubscriptionsCalculator() throws Exception {
        Assert.assertNotNull(api.listSubscriptionsCalculator());
    }

    @Test
    public void testGetPricing() throws Exception {
        Assert.assertNotNull(api.getPricing());
    }

    @Test
    public void testListDiscounts() throws Exception {
        Assert.assertNotNull(api.listDiscounts());
    }

    @Test
    public void testListTransactions() throws Exception {
        Assert.assertNotNull(api.listTransactions());
    }

    private void checkDrive(DriveInfo newDrive, DriveInfo createdDrive){
        Assert.assertEquals(newDrive.getName(), createdDrive.getName());
        Assert.assertEquals(newDrive.getMedia(), createdDrive.getMedia());
    }

    private void checkServer(ServerInfo newServer, ServerInfo createdServer){
        Assert.assertEquals(newServer.getName(), createdServer.getName());
        Assert.assertEquals(newServer.getMemory(), createdServer.getMemory());
        Assert.assertEquals(newServer.getVncPassword(), createdServer.getVncPassword());
        Assert.assertEquals(newServer.getCpu(), createdServer.getCpu());

        Assert.assertEquals(newServer.getDrives().size(), createdServer.getDrives().size());

        for(int i = 0; i < newServer.getDrives().size(); i++){
            checkServerDrive(newServer.getDrives().get(i), createdServer.getDrives().get(i));
        }
    }

    private void checkServerDrive(ServerDrive newServerDrive, ServerDrive createdServerDrive){
        Assert.assertEquals(newServerDrive.getBootOrder(), createdServerDrive.getBootOrder());
        Assert.assertEquals(newServerDrive.getDeviceChannel(), createdServerDrive.getDeviceChannel());
        Assert.assertEquals(newServerDrive.getDeviceEmulationType(), createdServerDrive.getDeviceEmulationType());
    }

    private void checkFirewallPolicy(FirewallPolicy newFirewallPolicy, FirewallPolicy createdFirewallPolicy){
        Assert.assertEquals(newFirewallPolicy.getName(), createdFirewallPolicy.getName());
        Assert.assertEquals(newFirewallPolicy.getRules(), createdFirewallPolicy.getRules());
    }

    private void checkVlAN(VLANInfo newVLAN, VLANInfo createdVLAN){
        Assert.assertEquals(newVLAN.getMeta(), createdVLAN.getMeta());
    }

    private void checkIP(IPInfo newIP, IPInfo createdIP){
        Assert.assertEquals(newIP.getMeta(), createdIP.getMeta());
    }

    private void checkTag(Tag newTag, Tag createdTag){
        Assert.assertEquals(newTag.getName(), createdTag.getName());
        Assert.assertEquals(newTag.getMeta(), createdTag.getMeta());

        Assert.assertEquals(newTag.getResources().size(), createdTag.getResources().size());

        for(int i = 0; i < newTag.getResources().size(); i++){
            checkTagRes(newTag.getResources().get(i), createdTag.getResources().get(i));
        }
    }

    private void checkTagRes(TagResource newTagResource, TagResource createdTagResource){
        Assert.assertEquals(newTagResource.getUuid(), createdTagResource.getUuid());
    }

    private void checkProfileInfo(ProfileInfo newProfileInfo, ProfileInfo createdProfileInfo){
        Assert.assertEquals(newProfileInfo.getAddress(), createdProfileInfo.getAddress());
        Assert.assertEquals(newProfileInfo.getBankReference(), createdProfileInfo.getBankReference());
        Assert.assertEquals(newProfileInfo.getCompany(), createdProfileInfo.getCompany());
        Assert.assertEquals(newProfileInfo.getCountry(), createdProfileInfo.getCountry());
        Assert.assertEquals(newProfileInfo.getEmail(), createdProfileInfo.getEmail());
        Assert.assertEquals(newProfileInfo.getFirstName(), createdProfileInfo.getFirstName());
        Assert.assertEquals(newProfileInfo.getLastName(), createdProfileInfo.getLastName());
    }
}
