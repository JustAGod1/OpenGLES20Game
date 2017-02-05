package com.justagod.opengles20game.Rendering;

import android.support.annotation.DrawableRes;

/**
 * Создано Юрием в 05.02.17.
 * <p>
 * =====================================================
 * =            Магия! Руками не трогать!!!           =
 * =====================================================
 */

public class SimpleTexture implements ITexture {

    private int textureId;

    public SimpleTexture(@DrawableRes int res) {
        textureId = Tesselator.loadTexture(res);
    }



    @Override
    public int getTexture() {
        return textureId;
    }

    @Override
    public void bind() {

    }

    @Override
    public void delete() {

    }

    @Override
    public void disable() {

    }
}
