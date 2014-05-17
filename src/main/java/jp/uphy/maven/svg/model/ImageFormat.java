/**
 * Copyright (C) 2014 uphy.jp
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.uphy.maven.svg.model;

import org.apache.batik.apps.rasterizer.DestinationType;

import java.text.MessageFormat;
import java.util.Arrays;


/**
 * @author Yuhi Ishikura
 */
public enum ImageFormat {
    PNG(DestinationType.PNG, "png"),
    JPEG(DestinationType.JPEG, "jpg", "jpeg"),
    TIFF(DestinationType.TIFF, "tiff", "tif"),
    PDF(DestinationType.PDF, "pdf");
    private final DestinationType destinationType;
    private final String[] extensions;

    private ImageFormat(DestinationType type, String... extensions) {
        this.destinationType = type;
        this.extensions = extensions;
    }

    public String getPrimaryExtension() {
        return extensions[0];
    }

    public static ImageFormat fromExtension(String extension) {
        for (ImageFormat f : values()) {
            for (String ext : f.extensions) {
                if (ext.equals(extension)) {
                    return f;
                }
            }
        }
        throw new IllegalArgumentException(MessageFormat.format("Unsupported format:{0} Specify {1}", extension, values()));
    }

    public DestinationType getDestinationType() {
        return destinationType;
    }

    @Override
    public String toString() {
        return "Format{" +
            "destinationType=" + destinationType +
            ", extensions=" + Arrays.toString(extensions) +
            '}';
    }
}
