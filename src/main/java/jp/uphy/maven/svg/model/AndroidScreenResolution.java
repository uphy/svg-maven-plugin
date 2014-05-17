package jp.uphy.maven.svg.model;

public enum AndroidScreenResolution {

    LDPI(0.75),
    MDPI(1),
    HDPI(1.5),
    XHDPI(2),
    XXHDPI(3);

    private double scale;

    private AndroidScreenResolution(double scale) {
        this.scale = scale;
    }

    public double getScale() {
        return this.scale;
    }

}