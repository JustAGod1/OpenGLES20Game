package com.justagod.opengles20game.WorldProviding;

import com.justagod.opengles20game.Entities.BlockWrapper;
import com.justagod.opengles20game.WorldProviding.Vectors.BlockPos;

import java.util.HashMap;
import java.util.Map;

/**
 * Создано Юрием в 04.02.17.
 * <p>
 * =====================================================
 * =            Магия! Руками не трогать!!!           =
 * =====================================================
 */

public class Chunk {

    private Map<BlockPos, BlockWrapper> blocks = new HashMap<>();

    public void generate() {

    }

    public BlockWrapper getBlock(BlockPos pos) {
        return blocks.get(pos);
    }

    public void setBlock(BlockPos pos, BlockWrapper block) {
        blocks.put(pos, block);
    }

    public boolean hasBlockAtPos(BlockPos pos) {
        return blocks.get(pos) != null;
    }
}
