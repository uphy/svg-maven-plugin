package jp.uphy.maven.svg.mojo;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import jp.uphy.maven.svg.model.FilenameWithSize;
import jp.uphy.maven.svg.model.ImageFormat;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Goal which touches a timestamp file.
 */
@Mojo(name = "rasterize-batch", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class RasterizeBatchMojo extends AbstractRasterizeMojo {

    /**
     * SVG file input directory.
     */
    @Parameter(required = true)
    private File inputDirectory;
    /**
     * Output file format(png,jpg,pdf,tiff).
     */
    @Parameter(defaultValue = DEFAULT_FORMAT)
    private String format;
    /**
     * Output file directory.
     */
    @Parameter(required = true)
    private File outputDirectory;

    @Override
    protected void initialize() throws MojoExecutionException, MojoFailureException {
        assertIsExistingDirectory("inputDirectory", inputDirectory);
        createDirectory("outputDirectory", outputDirectory);
        for (File input : inputDirectory.listFiles()) {
            final FilenameWithSize filenameWithSize = FilenameWithSize.parse(input.getName());
            final String outputName = filenameWithSize.getName();
            final int maximumWidth = filenameWithSize.getWidth();
            final int maximumHeight = filenameWithSize.getHeight();
            final String extension = ImageFormat.fromExtension(this.format).getPrimaryExtension();
            final File output = new File(this.outputDirectory, outputName + "." + extension);
            addRasterization(input, output, maximumWidth, maximumHeight, this.format);
        }
    }

}
