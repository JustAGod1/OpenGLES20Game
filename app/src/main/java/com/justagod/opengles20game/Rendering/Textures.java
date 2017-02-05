package com.justagod.opengles20game.Rendering;

import android.support.annotation.DrawableRes;

/**
 * Создано Юрием в 05.02.17.
 * <p>
 * =====================================================
 * =            Магия! Руками не трогать!!!           =
 * =====================================================
 */

public class Textures {

    public ITexture createSimpleTextureFromResource(@DrawableRes int res) {
        return new SimpleTexture(res);
    }
}
