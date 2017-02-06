package com.justagod.opengles20game.Rendering;

import android.opengl.GLES20;

import com.justagod.opengles20game.Stuff.Logger;

/**
 * Создано Юрием в 05.02.17.
 * <p>
 * =====================================================
 * =            Магия! Руками не трогать!!!           =
 * =====================================================
 */

public class Program {

    private String matrixUniform;
    private String positionAttr;
    private String colorAttr;
    private String textureCoordAttr;
    private int program;
    private boolean isBinded = false;

    Program(int program, String matrixUniform, String positionAttr) {
        this.program = program;
        this.matrixUniform = matrixUniform;
        this.positionAttr = positionAttr;
    }

    Program(int program, String matrixUniform, String positionAttr, String colorAttr, String textureCoordAttr) {
        this.program = program;
        this.matrixUniform = matrixUniform;
        this.positionAttr = positionAttr;
        this.colorAttr = colorAttr;
        this.textureCoordAttr = textureCoordAttr;
    }

    void bind() {
        GLES20.glUseProgram(program);
        isBinded = true;
    }

    void unBind() {
        isBinded = false;
    }

    public int getPositionAttributeId() {
        if (!isBinded) Logger.e("Програма не привязана");
        return GLES20.glGetAttribLocation(program, positionAttr);
    }

    public int getMatrixUniformId() {
        if (!isBinded) Logger.e("Програма не привязана");
        return GLES20.glGetUniformLocation(program, matrixUniform);
    }

    public int getColorAttributeId() {
        if (!isBinded) Logger.e("Програма не привязана");
        return GLES20.glGetAttribLocation(program, colorAttr);
    }

    public int getTextureCoordAttributeId() {
        if (!isBinded) Logger.e("Програма не привязана");
        return GLES20.glGetAttribLocation(program, textureCoordAttr);
    }

    public String getMatrixUniform() {
        return matrixUniform;
    }

    public Program setMatrixUniform(String matrixUniform) {
        this.matrixUniform = matrixUniform;
        return this;
    }

    public String getPositionAttr() {
        return positionAttr;
    }

    public Program setPositionAttr(String positionAttr) {
        this.positionAttr = positionAttr;
        return this;
    }

    public String getColorAttr() {
        return colorAttr;
    }

    public Program setColorAttr(String colorAttr) {
        this.colorAttr = colorAttr;
        return this;
    }

    public String getTextureCoordAttr() {
        return textureCoordAttr;
    }

    public Program setTextureCoordAttr(String textureCoordAttr) {
        this.textureCoordAttr = textureCoordAttr;
        return this;
    }

    public int getUniform(String name) {
        return GLES20.glGetUniformLocation(program, name);
    }
}
