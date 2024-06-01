package common.vehicle;

import java.io.Serial;
import java.io.Serializable;

public class Coordinates implements Serializable {
    int x;
    int y;
    public Coordinates (int x, int y) {
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
