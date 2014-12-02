package info.xiphia.mmd;

/**
 * Created by xiphia on 2014/11/02.
 */
public class Vector2 {
    private float _x;
    private float _y;

    public Vector2(float x, float y) {
        _x = x;
        _y = y;
    }

    public Vector2(float[] coordinate) {
        if(coordinate.length != 2) {
            throw new IllegalArgumentException();
        }
        _x = coordinate[0];
        _y = coordinate[1];
    }

    public float getX() {
        return _x;
    }

    public float getY() {
        return _y;
    }

    public float[] toArray() {
        return new float[]{_x, _y};
    }

    public String toString() {
        return "[" + _x + ", " + _y + "]";
    }
}
