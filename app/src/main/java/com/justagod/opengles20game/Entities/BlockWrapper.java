package com.justagod.opengles20game.Entities;

import com.justagod.opengles20game.WorldBlocks.Block;
import com.justagod.opengles20game.WorldProviding.Vectors.BlockPos;
import com.justagod.opengles20game.WorldProviding.Vectors.Vector;

/**
 * Создано Юрием в 04.02.17.
 * <p>
 * =====================================================
 * =            Магия! Руками не трогать!!!           =
 * =====================================================
 */
public class BlockWrapper extends Entity{

    private Block block;
    private BlockPos pos;

    public BlockWrapper(Block block, BlockPos pos) {
        this.block = block;
        this.pos = pos;
    }

    public void onDraw(BlockPos pos) {

    }

    public Block getBlock() {
        return block;
    }

    public BlockPos getPos() {
        return pos;
    }


    @Override
    public boolean isUpdatable() {
        return false;
    }

    @Override
    public void onDraw() {

    }

    @Override
    public boolean isVectorInBounds(Vector vector) {
        return false;
    }

    @Override
    public void initGL() {

    }
}
