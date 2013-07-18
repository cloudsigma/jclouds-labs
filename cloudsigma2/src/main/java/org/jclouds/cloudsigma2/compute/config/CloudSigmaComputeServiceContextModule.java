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
package org.jclouds.cloudsigma2.compute.config;

import com.google.common.base.Function;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;
import org.jclouds.cloudsigma2.CloudSigma2Api;
import org.jclouds.cloudsigma2.compute.CloudSigmaComputeServiceAdapter;
import org.jclouds.cloudsigma2.compute.CloudSigmaTemplateBuilderImpl;
import org.jclouds.cloudsigma2.compute.functions.ParseOsFamilyVersion64BitFromImageName;
import org.jclouds.cloudsigma2.compute.functions.PreinstalledDiskToImage;
import org.jclouds.cloudsigma2.compute.functions.ServerInfoToNodeMetadata;
import org.jclouds.cloudsigma2.compute.functions.ServerInfoToNodeMetadata.DeviceToVolume;
import org.jclouds.cloudsigma2.compute.functions.ServerInfoToNodeMetadata.GetImageIdFromServer;
import org.jclouds.cloudsigma2.compute.options.CloudSigmaTemplateOptions;
import org.jclouds.cloudsigma2.domain.Device;
import org.jclouds.cloudsigma2.domain.DriveInfo;
import org.jclouds.cloudsigma2.domain.Server;
import org.jclouds.cloudsigma2.domain.ServerInfo;
import org.jclouds.compute.ComputeServiceAdapter;
import org.jclouds.compute.config.ComputeServiceAdapterContextModule;
import org.jclouds.compute.domain.*;
import org.jclouds.compute.options.TemplateOptions;
import org.jclouds.domain.Location;
import org.jclouds.functions.IdentityFunction;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 
 * @author Adrian Cole
 */
public class CloudSigmaComputeServiceContextModule extends
         ComputeServiceAdapterContextModule<ServerInfo, Hardware, DriveInfo, Location> {

   @SuppressWarnings({ "unchecked", "rawtypes" })
   @Override
   protected void configure() {
      super.configure();
      bind(new TypeLiteral<ComputeServiceAdapter<ServerInfo, Hardware, DriveInfo, Location>>() {
      }).to(CloudSigmaComputeServiceAdapter.class);
      bind(new TypeLiteral<Function<ServerInfo, NodeMetadata>>() {
      }).to(ServerInfoToNodeMetadata.class);
      bind(new TypeLiteral<Function<Hardware, Hardware>>() {
      }).to(Class.class.cast(IdentityFunction.class));
      bind(new TypeLiteral<Function<DriveInfo, Image>>() {
      }).to(PreinstalledDiskToImage.class);
      bind(new TypeLiteral<Function<Location, Location>>() {
      }).to(Class.class.cast(IdentityFunction.class));
      bind(new TypeLiteral<Function<Device, Volume>>() {
      }).to(DeviceToVolume.class);
      bind(new TypeLiteral<Function<Server, String>>() {
      }).to(GetImageIdFromServer.class);
      bind(new TypeLiteral<Function<String, OsFamilyVersion64Bit>>() {
      }).to(ParseOsFamilyVersion64BitFromImageName.class);
      bind(TemplateBuilder.class)
      .to(CloudSigmaTemplateBuilderImpl.class);
   }

   @Provides
   @Singleton
   protected LoadingCache<String, DriveInfo> cache(GetDrive getDrive) {
      return CacheBuilder.newBuilder().build(getDrive);
   }

   @Singleton
   public static class GetDrive extends CacheLoader<String, DriveInfo> {
      private final CloudSigma2Api api;

      @Inject
      public GetDrive(CloudSigma2Api api) {
         this.api = api;
      }

      @Override
      public DriveInfo load(String input) {
         return api.getDriveInfo(input);
      }
   }

   @Provides
   @Singleton
   protected TemplateOptions templateOptions() {
      return new CloudSigmaTemplateOptions();
   }
}
