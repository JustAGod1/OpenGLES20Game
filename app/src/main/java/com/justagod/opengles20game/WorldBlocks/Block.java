package com.justagod.opengles20game.WorldBlocks;

import com.justagod.opengles20game.Entities.BlockWrapper;
import com.justagod.opengles20game.R;
import com.justagod.opengles20game.Rendering.ITexture;
import com.justagod.opengles20game.Rendering.SimpleTexture;
import com.justagod.opengles20game.WorldProviding.Vectors.BlockPos;

/**
 * Создано Юрием в 04.02.17.
 * <p>
 * =====================================================
 * =            Магия! Руками не трогать!!!           =
 * =====================================================
 */

public class Block {

    private ITexture texture;

    public void registerTextures() {
        texture = new SimpleTexture(R.drawable.brick);
    }

    public ITexture getTexture(BlockSide side) {
        return texture;
    }

    public BlockWrapper createWrapper(BlockPos position) {
        return new BlockWrapper(this, position);
    }
}
