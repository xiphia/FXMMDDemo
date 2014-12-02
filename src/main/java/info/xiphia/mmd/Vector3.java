package info.xiphia.mmd;

/**
 * Created by xiphia on 2014/11/02.
 */
public class Vector3 {
    private float _x;
    private float _y;
    private float _z;

    public Vector3(float x, float y, float z) {
        _x = x;
        _y = y;
        _z = z;
    }

    public Vector3(float[] coordinate) {
        if(coordinate.length != 3) {
            throw new IllegalArgumentException();
        }
        _x = coordinate[0];
        _y = coordinate[1];
        _z = coordinate[2];
    }

    public float getX() {
        return _x;
    }

    public float getY() {
        return _y;
    }

    public float getZ() {
        return _z;
    }

    public float[] toArray() {
        return new float[]{_x, _y, _z};
    }

    public String toString() {
        return "[" + _x + ", " + _y + ", " + _z + "]";
    }
}
