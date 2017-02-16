package com.justagod.opengles20game.WorldProviding.Vectors;

/**
 * Создано Юрием в 04.02.17.
 * <p>
 * =====================================================
 * =            Магия! Руками не трогать!!!           =
 * =====================================================
 */

public class ChunkPos {

    private BlockPos position;

    public ChunkPos(int x, int y) {
        position = new BlockPos(x, y);
    }


    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }
}
