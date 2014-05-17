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

import jp.uphy.maven.svg.model.AndroidScreenResolution;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.util.List;


/**
 * Goal which touches a timestamp file.
 */
@Mojo(name = "rasterize-android", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class AndroidRasterizeMojo extends AbstractRasterizeMojo {

    static final String DEFAULT_RESOLUTIONS = "LDPI,MDPI,HDPI,XHDPI,XXHDPI";
    static final String DEFAULT_RES_DIRECTORY = "res";
    /**
     * SVG file input.
     */
    @Parameter(required = true)
    private File input;
    /**
     * The maximum width of the output image.
     */
    @Parameter(required = true)
    private int width;
    /**
     * The maximum width of the output image.
     */
    @Parameter(required = true)
    private int height;
    /**
     * Android "res" directory.
     */
    @Parameter(defaultValue = DEFAULT_RES_DIRECTORY)
    private File resDirectory;
    /**
     * Output file format(png,jpg,pdf,tiff).
     */
    @Parameter(defaultValue = DEFAULT_FORMAT)
    private String format;
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
    @Parameter(defaultValue = DEFAULT_RESOLUTIONS)
    private List<String> resolutions;

    @Override
    protected void initialize() throws MojoFailureException {
        assertIsExistingFile("input", this.input);
        for (String resolution : resolutions) {
            addRasterization(this, resolution, width, height, input, resDirectory, getFilenameOf(this.input), this.format);
        }
    }

    static void addRasterization(AbstractRasterizeMojo mojo, String resolutionString, int width, int height, File input, File resDirectory, String outputName, String format) {
        final AndroidScreenResolution resolution = AndroidScreenResolution.valueOf(resolutionString);
        final double scale = resolution.getScale();
        final int maximumWidth = (int)Math.ceil(width * scale);
        final int maximumHeight = (int)Math.ceil(height * scale);

        final File drawableDirectory = new File(resDirectory, "drawable-" + resolution.name().toLowerCase()); //$NON-NLS-1$
        final File outputFile = new File(drawableDirectory, outputName + ".png"); //$NON-NLS-1$

        mojo.addRasterization(input, outputFile, maximumWidth, maximumHeight, format);
    }

}
