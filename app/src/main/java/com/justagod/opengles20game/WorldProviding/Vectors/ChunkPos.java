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

    public ChunkPos(int x, int z) {
        position = new BlockPos(x, 0, z);
    }


    public int getX() {
        return position.getX();
    }

    public int getZ() {
        return position.getZ();
    }
}
