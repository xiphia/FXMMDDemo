package info.xiphia.mmd.pmx;

/**
 * Created by xiphia on 2014/11/02.
 */
public class PMXSurface {
    private Number _x;
    private Number _y;
    private Number _z;

    public PMXSurface(Number x, Number y, Number z) {
        _x = x;
        _y = y;
        _z = z;
    }

    public PMXSurface(Number[] coordinate) {
        if(coordinate.length != 3) {
            throw new IllegalArgumentException();
        }
        _x = coordinate[0];
        _y = coordinate[1];
        _z = coordinate[2];
    }

    public Number getX() {
        return _x;
    }

    public Number getY() {
        return _y;
    }

    public Number getZ() {
        return _z;
    }

    public Number[] toArray() {
        return new Number[]{_x, _y, _z};
    }

    public String toString() {
        return "[" + _x + ", " + _y + ", " + _z + "]";
    }
}
