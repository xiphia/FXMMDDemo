package info.xiphia.mmd;

/**
 * Created by xiphia on 2014/11/02.
 */
public class Vector4 {
    private float _x;
    private float _y;
    private float _z;
    private float _w;

    public Vector4(float x, float y, float z, float w) {
        _x = x;
        _y = y;
        _z = z;
        _w = w;
    }

    public Vector4(float[] coordinate) {
        if(coordinate.length != 4) {
            throw new IllegalArgumentException();
        }
        _x = coordinate[0];
        _y = coordinate[1];
        _z = coordinate[2];
        _w = coordinate[3];
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

    public float getW() {
        return _w;
    }

    public float[] toArray() {
        return new float[]{_x, _y, _z, _w};
    }

    public String toString() {
        return "[" + _x + ", " + _y + ", " + _z + ", " + _w + "]";
    }
}
