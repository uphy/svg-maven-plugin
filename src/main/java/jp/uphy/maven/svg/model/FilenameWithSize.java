/**
 * Copyright (C) 2013 uphy.jp
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

import org.apache.maven.plugin.MojoFailureException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author Yuhi Ishikura
 */
public class FilenameWithSize {

    private String name;
    private int width;
    private int height;
    private String extension;

    private FilenameWithSize(String name, int width, int height, String extension) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.extension = extension;
    }

    public static FilenameWithSize parse(String fileName) {
        final Pattern p = Pattern.compile("(.*?)_(\\d+?)x(\\d+?)\\.(.+?)"); //$NON-NLS-1$
        final Matcher m = p.matcher(fileName);

        if (m.find() == false) {
            throw new IllegalArgumentException("Unsupported filename format.  Should be [name]_[width]x[height].[extension] : " + fileName); //$NON-NLS-1$
        }

        final String name = m.group(1);
        final int width = Integer.parseInt(m.group(2));
        final int height = Integer.parseInt(m.group(3));
        final String extension = m.group(4);
        return new FilenameWithSize(name, width, height, extension);
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getExtension() {
        return extension;
    }
}
