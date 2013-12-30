package pl.edu.pw.elka.ire.asiom.image.types;

public class GrayImgPixel extends Pixel2D{

    private float value;

    public GrayImgPixel(float val, int x, int y) {
        super(x, y);
        this.value = val;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
