package server.com;

public class Coordinates {
    int x;
    int y;
    Coordinates () { }
    Coordinates (int x, int y) {
        this.x = x;
        this.y = y;
    }
    protected void setX (int x) {
        this.x = x;
    }
    protected void setY (int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return x + ", " + y;
    }
}
