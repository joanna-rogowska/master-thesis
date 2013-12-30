package pl.edu.pw.elka.ire.asiom.image.types;

public class YCbCrPixel extends Pixel2D{

    private float y;

    private float cb;

    private float cr;

    public YCbCrPixel() {
        this.y = 0;
        this.cb = 0;
        this.cr = 0;
    }

    public YCbCrPixel(int xCoordiate, int yCoordinate) {
        super(xCoordiate, yCoordinate);
        this.y = 0;
        this.cb = 0;
        this.cr = 0;
    }

    public YCbCrPixel(int xCoordiate, int yCoordinate, float y, float cb, float cr) {
        super(xCoordiate, yCoordinate);
        this.y = y;
        this.cb = cb;
        this.cr = cr;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getCb() {
        return cb;
    }

    public void setCb(float cb) {
        this.cb = cb;
    }

    public float getCr() {
        return cr;
    }

    public void setCr(float cr) {
        this.cr = cr;
    }

    public void addValue(YCbCrPixel pixel) {
        this.y += pixel.getY();
        this.cb += pixel.getCb();
        this.cr += pixel.getCr();
    }

    public YCbCrPixel divide(int i) {
        return new YCbCrPixel(this.getxCoordinate(), this.getyCoordinate(),this.y / i, this.cb / i, this.cr / i);
    }
}
