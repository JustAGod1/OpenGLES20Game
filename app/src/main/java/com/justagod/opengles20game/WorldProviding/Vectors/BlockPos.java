package com.justagod.opengles20game.WorldProviding.Vectors;

/**
 * Создано Юрием в 04.02.17.
 * <p>
 * =====================================================
 * =            Магия! Руками не трогать!!!           =
 * =====================================================
 */

public class BlockPos {
    private int x;
    private int y;

    public BlockPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BlockPos blockPos = (BlockPos) o;

        if (x != blockPos.x) return false;
        return y == blockPos.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "BlockPos{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
