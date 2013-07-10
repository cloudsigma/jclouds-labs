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
import org.jclouds.cloudsigma2.beans.RawLibraryDrive;
import org.jclouds.cloudsigma2.domain.LibraryDrive;
import org.jclouds.javax.annotation.Nullable;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Vladimir Shevchenko
 */
@Singleton
public class JsonToLibraryDrive implements Function<RawLibraryDrive, LibraryDrive> {

    private final JsonToDriveInfo jsonToDriveInfo;

    @Inject
    public JsonToLibraryDrive(JsonToDriveInfo jsonToDriveInfo) {
        this.jsonToDriveInfo = jsonToDriveInfo;
    }

    @Override
    public LibraryDrive apply(@Nullable RawLibraryDrive input) {
        if(input == null){
            return null;
        }

        LibraryDrive.Builder libraryDriveBuilder = new LibraryDrive.Builder().fromDriveInfo(jsonToDriveInfo.apply(input));

        if(input.arch != null){
            libraryDriveBuilder.arch(input.arch);
        }

        if(input.category != null){
            libraryDriveBuilder.category(input.category);
        }

        if(input.description != null){
            libraryDriveBuilder.description(input.description);
        }

        if(input.favorite != null){
            libraryDriveBuilder.isFavorite(input.favorite);
        }

        if(input.image_type != null){
            libraryDriveBuilder.imageType(input.image_type);
        }
        if(input.install_notes != null){
            libraryDriveBuilder.installNotes(input.install_notes);
        }

        if(input.os != null){
            libraryDriveBuilder.os(input.os);
        }

        if(input.paid != null){
            libraryDriveBuilder.isPaid(input.paid);
        }

        if(input.url != null){
            libraryDriveBuilder.url(input.url);
        }

        return libraryDriveBuilder.build();
    }
}
