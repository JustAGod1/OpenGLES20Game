package com.justagod.opengles20game.Rendering;

import android.support.annotation.RawRes;

/**
 * Создано Юрием в 05.02.17.
 * <p>
 * =====================================================
 * =            Магия! Руками не трогать!!!           =
 * =====================================================
 */

public final class GLPrograms {

    public static Program createProgram(String vertexShader, String fragmentShader, String matrixUniformName, String positionAttributeName, String colorAttributeName, String textureAttributeName) {
        return new Program(Tesselator.createProgram(vertexShader, fragmentShader), matrixUniformName, positionAttributeName, colorAttributeName, textureAttributeName);
    }

    public static Program createProgram(@RawRes int vertexShader, @RawRes int fragmentShader, String matrixUniformName, String positionAttributeName, String colorAttributeName, String textureAttributeName) {
        return createProgram(Tesselator.readTextFileFromResources(vertexShader), Tesselator.readTextFileFromResources(fragmentShader), matrixUniformName, positionAttributeName, colorAttributeName, textureAttributeName);
    }

    public static Program createProgram(String vertexShader, String fragmentShader, String matrixUniformName, String positionAttributeName) {
        return createProgram(vertexShader, fragmentShader, matrixUniformName, positionAttributeName, null, null);
    }

    public static Program createProgram(@RawRes int vertexShader, @RawRes int fragmentShader, String matrixUniformName, String positionAttributeName) {
        return createProgram(vertexShader, fragmentShader, matrixUniformName, positionAttributeName, null, null);
    }
}
