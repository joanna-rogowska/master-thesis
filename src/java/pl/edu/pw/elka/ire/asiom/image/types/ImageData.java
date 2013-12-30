package pl.edu.pw.elka.ire.asiom.image.types;

//import com.google.common.base.Joiner;

public class ImageData<T extends Pixel2D> {

    private T[] pixels;

    private int width;

    private int height;

    public ImageData() {
        this.pixels = (T[]) new Pixel2D[0];
        this.width = 0;
        this.height = 0;
    }

    public ImageData(T[] pixels, int width, int height) {
        this.pixels = pixels;
        this.width = width;
        this.height = height;
    }

    public T getPixel(int x, int y) {
        if (x < width && x >= 0 && y < height && y >= 0) {
            return pixels[y * width + x];
        } else {
            throw new ArrayIndexOutOfBoundsException("Invalid coordinates: ("+x+","+y+").Image size is ("+width+","+height+")");
        }
    }

    public void setPixel(T pixel) {
        int x = pixel.getxCoordinate();
        int y = pixel.getyCoordinate();
        if (x < width && x >= 0 && y < height && y >= 0) {
            pixels[y * width + x] = pixel;
        } else {
            throw new ArrayIndexOutOfBoundsException("Invalid coordinates: ("+x+","+y+").Image size is ("+width+","+height+")");
        }
    }

    public T[] getPixels() {
        return pixels;
    }

    public void setPixels(T[] pixels) {
        this.pixels = pixels;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}


