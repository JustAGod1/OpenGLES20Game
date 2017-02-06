package com.justagod.opengles20game.WorldProviding;

import com.justagod.opengles20game.Entities.BlockWrapper;
import com.justagod.opengles20game.WorldBlocks.Blocks;
import com.justagod.opengles20game.WorldProviding.Vectors.BlockPos;
import com.justagod.opengles20game.WorldProviding.Vectors.ChunkPos;

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

    public void generate(ChunkPos pos) {
        for (int i = pos.getX() * 50; i < pos.getX() * 50 + 50; i++) {
            for (int j = pos.getZ() * 50; j < pos.getZ() * 50 + 50; j++) {
                BlockPos blockPos = new BlockPos(i, -20, j);
                blocks.put(blockPos, Blocks.bedrock.createWrapper(blockPos));
            }
        }
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

    public void onDraw() {
        for (Map.Entry<BlockPos, BlockWrapper> entry : blocks.entrySet()) {
            entry.getValue().onDraw();
        }
    }
}
