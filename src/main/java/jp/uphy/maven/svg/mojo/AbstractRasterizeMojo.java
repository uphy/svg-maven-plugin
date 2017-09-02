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

import jp.uphy.maven.svg.model.ImageFormat;
import jp.uphy.maven.svg.model.SvgTool;
import org.apache.batik.apps.rasterizer.DestinationType;
import org.apache.batik.apps.rasterizer.SVGConverterException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Yuhi Ishikura
 */
abstract class AbstractRasterizeMojo extends AbstractMojo {

    static final String DEFAULT_FORMAT = "png";
    private final List<Rasterization> rasterizations = new ArrayList<Rasterization>();

    @Override
    public final void execute() throws MojoExecutionException, MojoFailureException {
        initialize();
        final SvgTool svgTool = new SvgTool();
        final Log log = getLog();
        for (final Rasterization rasterization : rasterizations) {
            log.info(MessageFormat.format("Rasterizing[{0}]", rasterization));
            createDirectory("outputDirectory", rasterization.output.getParentFile());
            rasterization.execute(svgTool);
        }
    }

    abstract void initialize() throws MojoFailureException;

    final void addRasterization(File svgFile, File output, int maximumWidth, int maximumHeight, String format) {
        this.rasterizations.add(new Rasterization(svgFile, output, maximumWidth, maximumHeight, format));
    }

    static void createDirectory(String parameterName, File directory) throws MojoFailureException {
        if (directory.exists()) {
            if (directory.isDirectory() == false) {
                throw new MojoFailureException(MessageFormat.format("''{0}'' already exist but is not a directory: {1}", parameterName, directory));
            }
            return;
        }
        if (directory.mkdirs() == false) {
            throw new MojoFailureException(MessageFormat.format("Failure to make ''{0}'' directory : ", parameterName, directory));
        }
    }

    static void assertIsExistingDirectory(String parameterName, File directory) throws MojoFailureException {
        if (directory == null) {
            throw new MojoFailureException(MessageFormat.format("''{0}'' is not specified.", parameterName));
        }
        if (directory.exists() == false) {
            throw new MojoFailureException(MessageFormat.format("''{0}'' doesn''t exist: {1}", parameterName, directory));
        }
        if (directory.isDirectory() == false) {
            throw new MojoFailureException(MessageFormat.format("{0} is not a directory: {1}", parameterName, directory));
        }
    }

    static void assertIsExistingFile(String parameterName, File file) throws MojoFailureException {
        if (file == null) {
            throw new MojoFailureException(MessageFormat.format("''{0}'' is not specified.", parameterName));
        }
        if (file.exists() == false) {
            throw new MojoFailureException(MessageFormat.format("''{0}'' doesn''t exist: {1}", parameterName, file));
        }
        if (file.isFile() == false) {
            throw new MojoFailureException(MessageFormat.format("{0} is not a file: {1}", parameterName, file));
        }
    }

    static String getExtensionOf(File file) {
        final int i = file.getName().lastIndexOf('.');
        return file.getName().substring(i + 1);
    }

    static String getFilenameOf(File file) {
        final int i = file.getName().lastIndexOf('.');
        return file.getName().substring(0, i);
    }

    private static class Rasterization {

        private static final Map<String, DestinationType> formatMap = new HashMap<String, DestinationType>();

        private final File svgFile;
        private final File output;
        private final int maximumWidth;
        private final int maximumHeight;
        private final String extension;

        public Rasterization(File svgFile, File output, int maximumWidth, int maximumHeight, String extension) {
            this.svgFile = svgFile;
            this.output = output;
            this.maximumWidth = maximumWidth;
            this.maximumHeight = maximumHeight;
            this.extension = extension;
        }

        void execute(SvgTool svgTool) throws MojoExecutionException {
            final ImageFormat imageFormat;
            try {
                imageFormat = ImageFormat.fromExtension(this.extension);
            } catch (IllegalArgumentException ex) {
                throw new MojoExecutionException(ex.getMessage());
            }
            try {
                svgTool.rasterize(this.svgFile, this.output, this.maximumWidth, this.maximumHeight, imageFormat.getDestinationType());
            } catch (SVGConverterException e) {
                throw new MojoExecutionException(MessageFormat.format("Failure to rasterize : {0}", output));
            }
        }

        @Override
        public String toString() {
            return "Rasterization{" +
                "svgFile=" + svgFile +
                ", output=" + output +
                ", maximumWidth=" + maximumWidth +
                ", maximumHeight=" + maximumHeight +
                ", extension='" + extension + '\'' +
                '}';
        }
    }

}
