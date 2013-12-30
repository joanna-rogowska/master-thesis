package pl.edu.pw.elka.ire.asiom.image.types;

public class RGBPixel extends Pixel2D {

    private int r;

    private int g;

    private int b;

    public RGBPixel(int x, int y, int r, int g, int b) {
        super(x, y);
        this.r = r;
        this.g = g;
        this.b = b;
    }


    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }
}
