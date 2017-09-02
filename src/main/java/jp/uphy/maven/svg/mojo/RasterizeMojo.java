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
@Mojo(name = "rasterize", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class RasterizeMojo extends AbstractRasterizeMojo {

    /**
     * SVG file input.
     */
    @Parameter(required = true)
    private File input;
    /**
     * Output definitions.
     * <pre>
     * <outputs>
     *     <output>
     *         <path>target</path>
     *         <width>128</width>
     *         <height>128</height>
     *         <format>png</format>
     *     </output>
     * </outputs>
     * </pre>
     */
    @Parameter
    private List<Output> outputs;

    @Override
    protected void initialize() throws MojoFailureException {
        assertIsExistingFile("input", this.input);
        for (Output output : outputs) {
            addRasterization(this.input, output.path, output.width, output.height, output.format);
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
