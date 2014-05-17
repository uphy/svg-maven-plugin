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
package jp.uphy.maven.svg.mojo;

import jp.uphy.maven.svg.model.FilenameWithSize;
import jp.uphy.maven.svg.model.ImageFormat;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.text.MessageFormat;
import java.util.List;


/**
 * Goal which touches a timestamp file.
 */
@Mojo(name = "rasterize-android-batch", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class AndroidRasterizeBatchMojo extends AbstractRasterizeMojo {

    /**
     * SVG files directory.
     */
    @Parameter(required = true)
    private File inputDirectory;
    /**
     * Output file format(png,jpg,pdf,tiff).
     */
    @Parameter(defaultValue = DEFAULT_FORMAT)
    private String format;
    /**
     * Android "res" directory.
     */
    @Parameter(defaultValue = AndroidRasterizeMojo.DEFAULT_RES_DIRECTORY)
    private File resDirectory;
    /**
     * Output resolutions.
     * <ul>
     * <li>LDPI</li>
     * <li>MDPI</li>
     * <li>HDPI</li>
     * <li>XHDPI</li>
     * <li>XXHDPI</li>
     * </ul>
     */
    @Parameter(defaultValue = AndroidRasterizeMojo.DEFAULT_RESOLUTIONS)
    private List<String> resolutions;

    @Override
    protected void initialize() throws MojoExecutionException, MojoFailureException {
        assertIsExistingDirectory("inputDirectory", inputDirectory);
        createDirectory("outputDirectory", resDirectory);
        for (File input : inputDirectory.listFiles()) {
            final FilenameWithSize filenameWithSize = FilenameWithSize.parse(input.getName());
            final String outputName = filenameWithSize.getName();
            final int maximumWidth = filenameWithSize.getWidth();
            final int maximumHeight = filenameWithSize.getHeight();
            final String extension = ImageFormat.fromExtension(this.format).getPrimaryExtension();
            for (String resolution : this.resolutions) {
                AndroidRasterizeMojo.addRasterization(this, resolution, maximumWidth, maximumHeight, input, this.resDirectory, outputName, this.format);
            }
        }
    }

    public static class Output {
        @Parameter(required = true)
        File path;
        @Parameter(required = true)
        int width;
        @Parameter(required = true)
        int height;
        @Parameter(defaultValue = "png")
        String format;

        @Override
        public String toString() {
            return MessageFormat.format("Output'{'path=''{0}'', size=''{1}x{2}'', format=''{3}'''}'", path, width, height, format);
        }
    }

}
