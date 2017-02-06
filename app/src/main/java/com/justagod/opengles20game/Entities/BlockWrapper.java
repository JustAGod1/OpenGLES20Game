package com.justagod.opengles20game.Entities;

import com.justagod.opengles20game.Rendering.Tesselator;
import com.justagod.opengles20game.WorldBlocks.Block;
import com.justagod.opengles20game.WorldProviding.Vectors.BlockPos;
import com.justagod.opengles20game.WorldProviding.Vectors.Vector;

import static android.opengl.GLES20.GL_TRIANGLES;

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
    private float[] vertices = {
            // 0
            0, 0, 0,
            1, 0, 0,
            1, 1, 0,
            0, 1, 0,

            // 4
            1, 0, 0,
            1, 1, 0,
            1, 1, 1,
            1, 0, 1,

            // 8
            0, 1, 0,
            1, 1, 0,
            1, 1, 1,
            0, 1, 1,

            // 12
            0, 0, 0,
            0, 1, 0,
            0, 1, 1,
            0, 0, 1,

            // 16
            0, 0, 0,
            1, 0, 0,
            1, 0, 1,
            0, 0, 1,


            // 20
            0, 0, 1,
            1, 0, 1,
            1, 1, 1,
            0, 1, 1
    };
    private int[] indices = {0,1,2, 0,3,2, 4,5,6, 4,7,6, 8,9,10, 8,11,10, 12,13,14, 12,15,14, 16,17,18, 16,19,18, 20,21,22, 20,23,22};

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
        Tesselator.setColorUniform(1, 1, 1, 1);
        Tesselator.bindVertices(vertices);
        //Tesselator.translate(pos.getX(), pos.getY(), pos.getZ());
        Tesselator.draw(GL_TRIANGLES, indices);
    }

    @Override
    public boolean isVectorInBounds(Vector vector) {
        return false;
    }

    @Override
    public void initGL() {

    }
}
