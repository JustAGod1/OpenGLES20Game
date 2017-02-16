package com.justagod.opengles20game.WorldProviding.Vectors;

/**
 * Создано Юрием в 04.02.17.
 * <p>
 * =====================================================
 * =            Магия! Руками не трогать!!!           =
 * =====================================================
 */

public class Vector {

    private float x;
    private float y;


    public Vector(float x, float y) {
        this.x = x;
        this.y = y;

    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }



    public BlockPos toBlockPos() {
        float x = getX();
        float y = getY();


        return new BlockPos(transformFloat(x), transformFloat(y));
    }

    private int transformFloat(float f) {
        if (f >= 0) {
            return (int) f;
        } else {
            if (f % 1f != 0) return (int) f - 1;
            else return (int) f;
        }

    }

    @Override
    public String toString() {
        return "Vector{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector vector = (Vector) o;

        if (Float.compare(vector.x, x) != 0) return false;
        return Float.compare(vector.y, y) == 0;

    }

    @Override
    public int hashCode() {
        int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        return result;
    }

    public void add(Vector vector) {
        x += vector.getX();
        y += vector.getY();

    }
}
