package pl.edu.pw.elka.ire.asiom.image.descritors;

import pl.edu.pw.elka.ire.asiom.image.types.ImageData;
import pl.edu.pw.elka.ire.asiom.image.types.YCbCrPixel;

public class ColorLayoutDescriptorUtils implements DescriptorUtils {
    //
//    protected static int[] arrayZigZag = {
//            0, 1, 8, 16, 9, 2, 3, 10, 17, 24, 32, 25, 18, 11, 4, 5,
//            12, 19, 26, 33, 40, 48, 41, 34, 27, 20, 13, 6, 7, 14, 21, 28,
//            35, 42, 49, 56, 57, 50, 43, 36, 29, 22, 15, 23, 30, 37, 44, 51,
//            58, 59, 52, 45, 38, 31, 39, 46, 53, 60, 61, 54, 47, 55, 62, 63
//    };
    protected final static int BLOCK_SIZE = 8;
    protected final static double[][] ARRAY_COSIN = {
            {
                    3.535534e-01, 3.535534e-01, 3.535534e-01, 3.535534e-01,
                    3.535534e-01, 3.535534e-01, 3.535534e-01, 3.535534e-01
            },
            {
                    4.903926e-01, 4.157348e-01, 2.777851e-01, 9.754516e-02,
                    -9.754516e-02, -2.777851e-01, -4.157348e-01, -4.903926e-01
            },
            {
                    4.619398e-01, 1.913417e-01, -1.913417e-01, -4.619398e-01,
                    -4.619398e-01, -1.913417e-01, 1.913417e-01, 4.619398e-01
            },
            {
                    4.157348e-01, -9.754516e-02, -4.903926e-01, -2.777851e-01,
                    2.777851e-01, 4.903926e-01, 9.754516e-02, -4.157348e-01
            },
            {
                    3.535534e-01, -3.535534e-01, -3.535534e-01, 3.535534e-01,
                    3.535534e-01, -3.535534e-01, -3.535534e-01, 3.535534e-01
            },
            {
                    2.777851e-01, -4.903926e-01, 9.754516e-02, 4.157348e-01,
                    -4.157348e-01, -9.754516e-02, 4.903926e-01, -2.777851e-01
            },
            {
                    1.913417e-01, -4.619398e-01, 4.619398e-01, -1.913417e-01,
                    -1.913417e-01, 4.619398e-01, -4.619398e-01, 1.913417e-01
            },
            {
                    9.754516e-02, -2.777851e-01, 4.157348e-01, -4.903926e-01,
                    4.903926e-01, -4.157348e-01, 2.777851e-01, -9.754516e-02
            }
    };

    public float[] extract(ImageData image) {

        return new float[0];
    }

    public ImageData<YCbCrPixel> createTinyImage(ImageData<YCbCrPixel> imageData) {
        YCbCrPixel[] tinyImgPixels = new YCbCrPixel[BLOCK_SIZE * BLOCK_SIZE];
        ImageData<YCbCrPixel> tinyImage = new ImageData<YCbCrPixel>(tinyImgPixels, BLOCK_SIZE, BLOCK_SIZE);

        int[] pixelCounter = new int[tinyImage.getPixels().length];
        for (int y = 0; y < imageData.getHeight(); ++y) {
            for (int x = 0; x < imageData.getWidth(); ++x) {
                int tinyImgX = (int)((float)x / (float)imageData.getWidth() * BLOCK_SIZE);
                int tinyImgY = (int)((float)y / (float)imageData.getHeight() * BLOCK_SIZE);
                YCbCrPixel blockPixel = tinyImage.getPixel(tinyImgX, tinyImgY);
                //todo: try to remove
                blockPixel = blockPixel == null ? new YCbCrPixel(tinyImgX, tinyImgY) : blockPixel;
                blockPixel.addValue(imageData.getPixel(x, y));
                ++pixelCounter[tinyImgY * BLOCK_SIZE + tinyImgX];
                tinyImage.setPixel(blockPixel);
            }
        }

        for (int i = 0; i < tinyImage.getPixels().length; ++i) {
            tinyImage.setPixel(tinyImage.getPixels()[i].divide(pixelCounter[i]));
        }

        return tinyImage;
    }
//
//        for (int y = 0, ; y < imageData.getHeight(); y += imageData.getHeight()/BLOCK_SIZE + ((imageData.getHeight()-y)%BLOCK_SIZE == 0 ? 0 : 1)) {
//            for (int x = 0, tinyImgX = 0; x < imageData.getWidth(); x += imageData.getWidth()/BLOCK_SIZE + ((imageData.getWidth()-x)%BLOCK_SIZE == 0 ? 0 : 1)) {
//                pixel = imageData.getPixel(x, y);
//
//                tinyImgYCoordinate = (int) (y / (imageData.getHeight() / 8.0));
//                tinyImgXCoordinate = (int) (x / (imageData.getWidth() / 8.0));
//
//                k = (y_axis << 3) + x_axis;
//
//                //RGB to YCbCr, partition and average-calculation
//                yy = (0.299 * R + 0.587 * G + 0.114 * B) / 256.0;
//                sum[0][k] += (int) (219.0 * yy + 16.5); // Y
//                sum[1][k] += (int) (224.0 * 0.564 * (B / 256.0 * 1.0 - yy) + 128.5); // Cb
//                sum[2][k] += (int) (224.0 * 0.713 * (R / 256.0 * 1.0 - yy) + 128.5); // Cr
//                cnt[k]++;
//            }
//        }
//
//        for (i = 0; i < 8; i++) {
//            for (j = 0; j < 8; j++) {
//                for (k = 0; k < 3; k++) {
//                    if (cnt[(i << 3) + j] != 0)
//                        tinyImage[k][(i << 3) + j] = (int) (sum[k][(i << 3) + j] / cnt[(i << 3) + j]);
//                    else
//                        tinyImage[k][(i << 3) + j] = 0;
//                }
//            }
//        }
//    }

    private static int[] Fdct(int[] shapes) {
        int i, j, k;
        double s;
        double[] dct = new double[64];

        //calculation of the cos-values of the second sum
        for (i = 0; i < 8; i++) {
            for (j = 0; j < 8; j++) {
                s = 0.0;
                for (k = 0; k < 8; k++)
                    s += ARRAY_COSIN[j][k] * shapes[8 * i + k];
                dct[8 * i + j] = s;
            }
        }

        for (j = 0; j < 8; j++) {
            for (i = 0; i < 8; i++) {
                s = 0.0;
                for (k = 0; k < 8; k++)
                    s += ARRAY_COSIN[i][k] * dct[8 * k + j];
                shapes[8 * i + j] = (int) Math.floor(s + 0.499999);
            }
        }

        return shapes;
    }
}
