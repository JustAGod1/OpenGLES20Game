package com.justagod.opengles20game.Rendering;

import android.opengl.GLSurfaceView;
import android.util.Log;

import com.justagod.opengles20game.R;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_BUFFER_BIT;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetProgramInfoLog;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glVertexAttribPointer;
import static com.justagod.opengles20game.Rendering.Tesselator.bindProgram;
import static com.justagod.opengles20game.Rendering.Tesselator.compileFragmentShader;
import static com.justagod.opengles20game.Rendering.Tesselator.compileVertexShader;
import static com.justagod.opengles20game.Rendering.Tesselator.createProgram;
import static com.justagod.opengles20game.Rendering.Tesselator.readTextFileFromResources;


/**
 * Создано Юрием в 01.02.17.
 * <p>
 * =====================================================
 * =            Магия! Руками не трогать!!!           =
 * =====================================================
 */

public class Renderer implements GLSurfaceView.Renderer {

    public static final String U_COLOR = "u_Color";
    public static final String A_POSITION = "a_Position";
    public static final String TAG = "Renderer";

    public int uColorLocation;
    public int aPositionLocation;


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(1, 0, 0, 1);

        String vertexShaderCode = readTextFileFromResources(R.raw.vertex_shader);
        System.out.println(vertexShaderCode);
        System.out.println("--------------------------------------");
        int vertexShader = compileVertexShader(vertexShaderCode);
        String fragmentShaderCode = readTextFileFromResources(R.raw.fragment_shader);
        System.out.println(fragmentShaderCode);
        int fragmentShader = compileFragmentShader(fragmentShaderCode);

        int program = createProgram(vertexShader, fragmentShader);

        bindProgram(program);



        uColorLocation = glGetUniformLocation(program, U_COLOR);
        aPositionLocation = glGetAttribLocation(program, A_POSITION);

        Tesselator.validateProgram(program);

        Log.v(TAG, glGetProgramInfoLog(program));
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT| GL_DEPTH_BUFFER_BIT);

        float[] vertices = {
                -1, -1, 0,
                1, -1, 0,
                1, 1, 0,
                -1, 1, 0
        };

        glVertexAttribPointer(aPositionLocation, vertices.length, GL_FLOAT, false, 0, Tesselator.createFloatBuffer(vertices));


        glDrawArrays(GL_TRIANGLE_FAN, 0, 4);
    }
}
