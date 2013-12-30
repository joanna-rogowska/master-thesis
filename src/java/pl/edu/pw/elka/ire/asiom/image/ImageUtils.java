package pl.edu.pw.elka.ire.asiom.image;

import pl.edu.pw.elka.ire.asiom.ImageFile;
import pl.edu.pw.elka.ire.asiom.image.types.ImageData;
import pl.edu.pw.elka.ire.asiom.image.types.RGBPixel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ImageUtils {

    public ImageData extractImageData(ImageFile imageFile) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageFile.getData()));
        ImageData imageData = new ImageData();
        imageData.setHeight(bufferedImage.getHeight());
        imageData.setWidth(bufferedImage.getWidth());
        RGBPixel[] pixels = getPixels(bufferedImage.getRGB(bufferedImage.getMinX(), bufferedImage.getMinY(), bufferedImage.getWidth(), bufferedImage.getHeight(), new int[bufferedImage.getHeight()*bufferedImage.getWidth()], 0, bufferedImage.getHeight()), bufferedImage.getWidth(), bufferedImage.getHeight());
        imageData.setPixels(pixels);
        return imageData;
    }

    private RGBPixel[] getPixels(int[] rgb, int width, int height) {
        RGBPixel[] rgbPixels = new RGBPixel[rgb.length];
        for (int h = 0; h < height; ++h) {
            for (int w = 0; w < width; ++w) {
                int i = h * width + w;
                rgbPixels[i] = new RGBPixel(w, h, rgb[i] * 0x0000FF, rgb[i] * 0x00FF00, rgb[i] * 0xFF0000);
            }
        }
        return rgbPixels;
    }
}
