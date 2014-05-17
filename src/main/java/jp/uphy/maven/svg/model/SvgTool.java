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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import org.apache.batik.apps.rasterizer.DestinationType;
import org.apache.batik.apps.rasterizer.SVGConverter;
import org.apache.batik.apps.rasterizer.SVGConverterException;


/**
 * @author Yuhi Ishikura
 */
public class SvgTool {

    private final SVGConverter svgConverter;

    /**
     * {@link SvgTool}オブジェクトを構築します。
     */
    public SvgTool() {
        this.svgConverter = new SVGConverter();
    }

    /**
     * SVGファイルを、PNGに変換します。
     *
     * @param svgFile         SVGファイル
     * @param output          出力ファイル
     * @param maximumWidth    最大の画像サイズ
     * @param maximumHeight   最大の画像サイズ
     * @param destinationType 出力ファイルタイプ
     * @throws org.apache.batik.apps.rasterizer.SVGConverterException 不正なSVGが与えられた場合
     */
    public void rasterize(File svgFile, File output, int maximumWidth, int maximumHeight, DestinationType destinationType) throws SVGConverterException {
        if (maximumWidth > maximumHeight) {
            this.svgConverter.setWidth(maximumWidth);
        } else {
            this.svgConverter.setHeight(maximumHeight);
        }
        this.svgConverter.setDestinationType(destinationType);
        this.svgConverter.setSources(new String[] {svgFile.getAbsolutePath()});
        this.svgConverter.setDst(output);

        final PrintStream oldSysOut = System.out;
        System.setOut(new PrintStream(new ByteArrayOutputStream())); // 不要な出力が出るので抑止
        try {
            this.svgConverter.execute();
        } finally {
            System.setOut(oldSysOut);
        }
    }

}
