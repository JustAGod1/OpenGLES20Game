package com.justagod.opengles20game.Rendering;

import android.content.res.Resources;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.justagod.opengles20game.WorldProviding.World;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


/**
 * Создано Юрием в 01.02.17.
 * <p>
 * =====================================================
 * =            Магия! Руками не трогать!!!           =
 * =====================================================
 */

public class Renderer implements GLSurfaceView.Renderer {


    private static int width;
    private static int height;
    private Resources mResources;

    public Renderer(Resources resources, int width, int height) {

        mResources = resources;
        this.width = width;
        this.height = height;
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Tesselator.init(mResources);
        Tesselator.setColorUniform(1, 0, 0, 1);
        GLES20.glClearColor(1, 1, 0, 1);

        Tesselator.bindVertices(new float[] {
                -1, -1,
                1, -1,
                1, 1,
                -1, 1
        });
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Tesselator.perspective(width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        //GLES20.glClear(GL_COLOR_BUFFER_BIT);
        //Tesselator.draw(GL_TRIANGLES, new int[]{0, 1, 2, 0, 3, 2});
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        Tesselator.loadIdentity();

        World.getInstance().onDraw();
    }
}
