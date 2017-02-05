package com.justagod.opengles20game.Rendering;

/**
 * Создано Юрием в 05.02.17.
 * <p>
 * =====================================================
 * =            Магия! Руками не трогать!!!           =
 * =====================================================
 */

public interface ITexture {

    int getTexture();

    void bind();

    void delete();

    void disable();
}
