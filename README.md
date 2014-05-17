# svg-maven-plugin

## Summary

Maven SVG rasterization plugin.
This plugin converts SVG files to raster images like png, jpg. 
Also supports converting to multiple resolutions for Android.

## Setup

Edit your pom.xml as follows.
See also example [pom.xml](https://github.com/uphy/svg-maven-plugin/blob/develop/sample/pom.xml).

1. Add plugin repository.

```xml
<pluginRepositories>
    <pluginRepository>
        <id>uphy.jp - Plugins</id>
        <url>http://uphy.jp/nexus/content/repositories/plugins/</url>
    </pluginRepository>
</pluginRepositories>
```

2. Add plugin.

```xml
<plugin>
    <groupId>jp.uphy.maven</groupId>
    <artifactId>svg-maven-plugin</artifactId>
    <version>1.0</version>
    <executions>
        <execution>
            <id>Rasterize</id>
            <goals>
                <goal>rasterize</goal>
            </goals>
            <configuration>
                <!-- ... -->
            </configuration>
        </execution>
    </executions>
</plugin>
```

## Goals
### Rasterize SVG image file
Convert a SVG image to raster images.

Goal: rasterize
```xml
<configuration>
    <!-- Input file path.  Must exist.[Required] -->
    <input>svg/sample.svg</input>
    <outputs>
        <output>
            <!-- Output file path. [Required] -->
            <path>output/sample128.png</path>
            <!-- Maximum width of output image. [Required] -->
            <width>128</width>
            <!-- Maximum height of output image. [Required] -->
            <height>128</height>
            <!-- Format for output image(png, jpg, pdf, tiff). [Optional, default:png] -->
            <format>png</format>
        </output>
        <output>
            <path>output/sample256.pdf</path>
            <width>256</width>
            <height>256</height>
            <format>pdf</format>
        </output>
    </outputs>
</configuration>
```

### Rasterize SVG images in a directory
Convert SVG images in a directory at once.
Filename must be a special below format.

Goal: rasterize-batch
```xml
<configuration>
    <!-- Input directory path.  Must exist.[Required] -->
    <!-- Files in this directory must be named [name]_[maximumWidth]x[maximumHeight].svg -->
    <inputDirectory>svg/forBatch</inputDirectory>
    <!-- Format for output image(png, jpg, pdf, tiff). [Optional, default:png] -->
    <format>png</format>
    <!-- Output directory path.  Must exist.[Required] -->
    <outputDirectory>output/batch</outputDirectory>
</configuration>
```

### Rasterize SVG image for Android
Convert a SVG image to raster images for multiple resolutions of Android.

Goal: rasterize-android
```xml
<configuration>
    <!-- Input file path.  Must exist.[Required] -->
    <input>svg/sample.svg</input>
    <!-- Maximum width of output image. [Required] -->
    <width>128</width>
    <!-- Maximum height of output image. [Required] -->
    <height>128</height>
    <!-- "res" directory of Android. [Optional, default:res] -->
    <resDirectory>output/android/res</resDirectory>
    <!-- Output resolutions. [Optional, default:LDPI,MDPI,HDPI,XHDPI,XXHDPI] -->
    <!-- The size of MDPI equals to the base size(same as ${width},${height}}).-->
    <!-- Output location will be determined automatically. -->
    <!-- e.g., LDPI will be located in ${resDirectory}/drawable-ldpi/ -->
    <resolutions>
        <resolution>LDPI</resolution>
        <resolution>MDPI</resolution>
        <resolution>HDPI</resolution>
        <resolution>XHDPI</resolution>
        <resolution>XXHDPI</resolution>
    </resolutions>
</configuration>
```

### Rasterize SVG images in a directory for Android
Convert SVG images in a directory to Android multiple resolution images.
Filename must be a special below format. 
 
Goal: rasterize-android-batch
```xml
<configuration>
    <!-- Input directory path.  Must exist.[Required] -->
    <!-- Files in this directory must be named [name]_[maximumWidth]x[maximumHeight].svg -->
    <inputDirectory>svg/forBatch</inputDirectory>
    <!-- Format for output image(png, jpg, pdf, tiff). [Optional, default:png] -->
    <format>png</format>
    <!-- "res" directory of Android. [Optional, default:res] -->
    <resDirectory>output/android/batch/res</resDirectory>
    <!-- Output resolutions. [Optional, default:LDPI,MDPI,HDPI,XHDPI,XXHDPI] -->
    <!-- The size of MDPI equals to the base size(same as ${width},${height}}).-->
    <!-- Output location will be determined automatically. -->
    <!-- e.g., LDPI will be located in ${resDirectory}/drawable-ldpi/ -->
    <resolutions>
        <resolution>LDPI</resolution>
        <resolution>MDPI</resolution>
        <resolution>HDPI</resolution>
        <resolution>XHDPI</resolution>
        <resolution>XXHDPI</resolution>
    </resolutions>
</configuration>
```