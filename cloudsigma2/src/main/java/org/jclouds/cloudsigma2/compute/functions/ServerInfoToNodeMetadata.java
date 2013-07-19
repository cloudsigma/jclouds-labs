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
package org.jclouds.cloudsigma2.compute.functions;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.util.concurrent.UncheckedExecutionException;
import org.jclouds.cloudsigma2.domain.Device;
import org.jclouds.cloudsigma2.domain.DriveInfo;
import org.jclouds.cloudsigma2.domain.ServerDrive;
import org.jclouds.cloudsigma2.domain.ServerInfo;
import org.jclouds.cloudsigma2.domain.ServerStatus;
import org.jclouds.collect.Memoized;
import org.jclouds.compute.domain.HardwareBuilder;
import org.jclouds.compute.domain.Image;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.domain.NodeMetadata.Status;
import org.jclouds.compute.domain.NodeMetadataBuilder;
import org.jclouds.compute.domain.Volume;
import org.jclouds.compute.domain.VolumeBuilder;
import org.jclouds.compute.functions.GroupNamingConvention;
import org.jclouds.domain.Location;
import org.jclouds.logging.Logger;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.jclouds.compute.predicates.ImagePredicates.idEquals;

/**
 * @author Adrian Cole
 */
@Singleton
public class ServerInfoToNodeMetadata implements Function<ServerInfo, NodeMetadata> {
   public static final Map<ServerStatus, Status> serverStatusToNodeStatus = ImmutableMap
         .<ServerStatus, Status> builder()
         .put(ServerStatus.RUNNING, Status.RUNNING)//
         .put(ServerStatus.STOPPED, Status.SUSPENDED)//
         .put(ServerStatus.PAUSED, Status.SUSPENDED)//
         .put(ServerStatus.UNAVAILABLE, Status.TERMINATED)//
         .put(ServerStatus.UNRECOGNIZED, Status.UNRECOGNIZED)//
         .build();

   private final Function<ServerInfo, String> getImageIdFromServer;
   private final Supplier<Set<? extends Image>> images;
   private final Supplier<Location> locationSupplier;
   private final Function<Device, Volume> deviceToVolume;
   private final GroupNamingConvention nodeNamingConvention;

   @Inject
   ServerInfoToNodeMetadata(Function<ServerInfo, String> getImageIdFromServer, @Memoized Supplier<Set<? extends Image>> images,
         Function<Device, Volume> deviceToVolume, Supplier<Location> locationSupplier,
         GroupNamingConvention.Factory namingConvention) {
      this.nodeNamingConvention = checkNotNull(namingConvention, "namingConvention").createWithoutPrefix();
      this.locationSupplier = checkNotNull(locationSupplier, "locationSupplier");
      this.deviceToVolume = checkNotNull(deviceToVolume, "deviceToVolume");
      this.images = checkNotNull(images, "images");
      this.getImageIdFromServer = checkNotNull(getImageIdFromServer, "getImageIdFromServer");
   }

   @Override
   public NodeMetadata apply(ServerInfo from) {
      NodeMetadataBuilder builder = new NodeMetadataBuilder();
      builder.ids(from.getUuid());
      builder.name(from.getName());
      builder.location(locationSupplier.get());
      builder.group(nodeNamingConvention.groupInUniqueNameOrNull(from.getName()));

      String imageId = getImageIdFromServer.apply(from);
      if (imageId != null) {
         Optional<? extends Image> image = FluentIterable.from(images.get()).firstMatch(idEquals(imageId));
         if (image.isPresent()) {
            builder.operatingSystem(image.get().getOperatingSystem());
         }
      }
      builder.hardware(new HardwareBuilder().ids(from.getUuid()).hypervisor("kvm")
//            .processors(ImmutableList.of(new Processor(1, from.getCpu()))).ram(from.getMem())
//            .volumes(Iterables.transform(from.getDevices().values(), deviceToVolume))
              .build());
      builder.status(serverStatusToNodeStatus.get(from.getStatus()));
//      builder.publicAddresses(ImmutableSet.<String> of(from.getVnc().getIp()));
      builder.privateAddresses(ImmutableSet.<String> of());
      return builder.build();
   }

   @Singleton
   public static final class DeviceToVolume implements Function<Device, Volume> {
      @Resource
      protected Logger logger = Logger.NULL;

      private final LoadingCache<String, DriveInfo> cache;

      @Inject
      public DeviceToVolume(LoadingCache<String, DriveInfo> cache) {
         this.cache = checkNotNull(cache, "cache");
      }

      @Override
      public Volume apply(Device input) {
         VolumeBuilder builder = new VolumeBuilder();
         builder.id(input.getId());
         try {
            DriveInfo drive = cache.getUnchecked(input.getDriveUuid());
            if(drive != null){
               builder.size(drive.getSize().floatValue());
            }
         } catch (UncheckedExecutionException e) {
            logger.warn(e, "error finding drive %s: %s", input.getDriveUuid(), e.getMessage());
         }
         return builder.durable(true).type(Volume.Type.NAS).build();
      }
   }

   /**
    * When we create the boot drive of the server, by convention we set the name
    * to the image it came from.
    * 
    * @author Adrian Cole
    * 
    */
   @Singleton
   public static class GetImageIdFromServer implements Function<ServerInfo, String> {
      @Resource
      protected Logger logger = Logger.NULL;

      private final LoadingCache<String, DriveInfo> cache;

      @Inject
      public GetImageIdFromServer(LoadingCache<String, DriveInfo> cache) {
         this.cache = cache;
      }

      @Override
      public String apply(ServerInfo from) {
         ServerDrive bootDrive = new ServerDrive.Builder().bootOrder(-1).build();

         for(ServerDrive serverDrive : from.getDrives()){
            if(serverDrive.getBootOrder() > bootDrive.getBootOrder()){
                bootDrive = serverDrive;
            }
         }
         if (bootDrive.getBootOrder() != -1) {
            try {
               DriveInfo drive = cache.getUnchecked(bootDrive.getDriveUuid());
               return drive.getName();
            } catch (NullPointerException e) {
               logger.debug("drive %s not found", bootDrive.getDriveUuid());
            } catch (UncheckedExecutionException e) {
               logger.warn(e, "error finding drive %s: %s", bootDrive.getDriveUuid(), e.getMessage());
            }
         }

         return null;
      }
   }
}
