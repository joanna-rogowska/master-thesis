package pl.edu.pw.elka.ire.asiom.image.types;

public class Pixel2D {

    private int xCoordinate;

    private int yCoordinate;

    public Pixel2D() {
        xCoordinate = -1;
        yCoordinate = -1;
    }

    public Pixel2D(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }
}
