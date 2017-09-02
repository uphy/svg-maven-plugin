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

public enum AndroidScreenResolution {

    LDPI(0.75),
    MDPI(1),
    HDPI(1.5),
    XHDPI(2),
    XXHDPI(3);

    private final double scale;

    private AndroidScreenResolution(double scale) {
        this.scale = scale;
    }

    public double getScale() {
        return this.scale;
    }

}