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

import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;
import org.jclouds.apis.ApiMetadata;
import org.jclouds.cloudsigma2.compute.config.CloudSigmaComputeServiceContextModule;
import org.jclouds.cloudsigma2.config.CloudSigma2HttpApiModule;
import org.jclouds.cloudsigma2.config.CloudSigma2ParserModule;
import org.jclouds.cloudsigma2.reference.CloudSigmaConstants;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.rest.internal.BaseHttpApiMetadata;

import java.net.URI;
import java.util.Properties;

import static org.jclouds.compute.config.ComputeServiceProperties.TEMPLATE;
import static org.jclouds.reflect.Reflection2.typeToken;

/**
 * Implementation of {@link BaseHttpApiMetadata} for the Cloud Sigma API
 * 
 * @author Vladimir Shevchenko
 */
public class CloudSigma2ApiMetadata extends BaseHttpApiMetadata {

   @Override
   public Builder toBuilder() {
      return new Builder().fromApiMetadata(this);
   }

   public CloudSigma2ApiMetadata() {
      this(new Builder());
   }

   protected CloudSigma2ApiMetadata(Builder builder) {
      super(builder);
   }

   public static Properties defaultProperties() {
      Properties properties = BaseHttpApiMetadata.defaultProperties();
      properties.setProperty(CloudSigmaConstants.PROPERTY_VNC_PASSWORD, "IL9vs34d");
      // passwords are set post-boot, so auth failures are possible
      // from a race condition applying the password set script
      properties.setProperty("jclouds.ssh.max-retries", "7");
      properties.setProperty("jclouds.ssh.retry-auth", "true");
      properties.setProperty(TEMPLATE, "osFamily=UBUNTU,imageNameMatches=.*[Aa]utomated SSH Access.*,os64Bit=true");
      return properties;
   }

   public static class Builder extends BaseHttpApiMetadata.Builder {

      protected Builder() {
         super(CloudSigma2Api.class);
         id("cloudsigma2")
         .name("CloudSigma API")
         .defaultIdentity("email")
         .identityName("Email")
         .defaultCredential("Password")
         .credentialName("Password")
         .documentation(URI.create("http://cloudsigma.com/en/platform-details/the-api"))
         .version("2.0")
         .defaultEndpoint("https://zrh.cloudsigma.com/api/2.0")
         .defaultProperties(CloudSigma2ApiMetadata.defaultProperties())
         .view(typeToken(ComputeServiceContext.class))
         .defaultModules(ImmutableSet.<Class<? extends Module>>of(
                CloudSigma2HttpApiModule.class,
                CloudSigmaComputeServiceContextModule.class,
                CloudSigma2ParserModule.class));
      }

      @Override
      public CloudSigma2ApiMetadata build() {
         return new CloudSigma2ApiMetadata(this);
      }

      @Override
      protected Builder self() {
         return this;
      }

      @Override
      public Builder fromApiMetadata(ApiMetadata in) {
          return this;
      }
   }
}
