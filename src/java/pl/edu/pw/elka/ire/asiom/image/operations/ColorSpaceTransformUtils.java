package pl.edu.pw.elka.ire.asiom.image.operations;

import pl.edu.pw.elka.ire.asiom.image.types.RGBPixel;
import pl.edu.pw.elka.ire.asiom.image.types.YCbCrPixel;

public class ColorSpaceTransformUtils {

    public YCbCrPixel[] convertRGBDataToYCbCr(RGBPixel[] rgbPixels) {
        YCbCrPixel[] yCbCrPixels = new YCbCrPixel[rgbPixels.length];

        for (int i = 0; i < rgbPixels.length; ++i) {
            yCbCrPixels[i] = rgb2ycbcr(rgbPixels[i]);
        }

        return yCbCrPixels;
    }

    public YCbCrPixel rgb2ycbcr(RGBPixel rgbPixel) {
        float y = (int) (0.299 * rgbPixel.getR() + 0.587 * rgbPixel.getG() + 0.114 * rgbPixel.getB());
        float cb = (int) (-0.16874 * rgbPixel.getR() - 0.33126 * rgbPixel.getG() + 0.50000 * rgbPixel.getB());
        float cr = (int) (0.50000 * rgbPixel.getR() - 0.41869 * rgbPixel.getG() - 0.08131 * rgbPixel.getB());
        return new YCbCrPixel(rgbPixel.getxCoordinate(), rgbPixel.getyCoordinate(), y, cb, cr);
    }
}
