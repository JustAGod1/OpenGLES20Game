package com.justagod.opengles20game.Rendering;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.support.annotation.DrawableRes;
import android.support.annotation.RawRes;

import com.justagod.opengles20game.R;
import com.justagod.opengles20game.Stuff.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Stack;

import static android.opengl.GLES20.GL_COMPILE_STATUS;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_LINK_STATUS;
import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_VALIDATE_STATUS;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glCompileShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glDeleteTextures;
import static android.opengl.GLES20.glGenTextures;
import static android.opengl.GLES20.glGetProgramInfoLog;
import static android.opengl.GLES20.glGetProgramiv;
import static android.opengl.GLES20.glGetShaderiv;
import static android.opengl.GLES20.glLinkProgram;
import static android.opengl.GLES20.glShaderSource;
import static android.opengl.GLES20.glValidateProgram;

/**
 * Создано Юрием в 01.02.17.
 * <p>
 * =====================================================
 * =            Магия! Руками не трогать!!!           =
 * =====================================================
 */

public class Tesselator {

    private static Resources resources;
    private static int programInUse;
    private static float[] matrix = new float[16];
    private static Stack<float[]> matrixStack = new Stack<>();


    private static int defaultVertexShader;
    private static int defaultFragmentShader;
    private static int defaultProgram;


    public static void init(Resources res) {
        resources = res;
        defaultVertexShader = compileVertexShader(R.raw.vertex_shader);
        defaultFragmentShader = compileFragmentShader(R.raw.fragment_shader);
        defaultProgram = createProgram(defaultVertexShader, defaultFragmentShader);
        Matrix.setIdentityM(matrix, 0);
    }

    public static int compileVertexShader(String shaderCode) {
        return compileShader(GL_VERTEX_SHADER, shaderCode);
    }

    public static int compileFragmentShader(String shaderCode) {
        return compileShader(GL_FRAGMENT_SHADER, shaderCode);
    }

    public static int compileFragmentShader(@RawRes int res) {
        return compileFragmentShader(readTextFileFromResources(res));
    }

    public static int compileVertexShader(@RawRes int res) {
        return compileVertexShader(readTextFileFromResources(res));
    }

    private static int compileShader(int type, String shaderCode) {
        int res = glCreateShader(type);
        if (res == 0) {
            Logger.e("Could not create shader");
            return res;
        }

        glShaderSource(res, shaderCode);
        glCompileShader(res);

        final int[] compileStatus = new int[1];
        glGetShaderiv(res, GL_COMPILE_STATUS, compileStatus, 0);


        if (compileStatus[0] == 0) Logger.e("Не могу скомпилировать шейдер:\n" + shaderCode);

        return res;
    }

    public static boolean validateProgram(int programObjectId) {
        glValidateProgram(programObjectId);
        final int[] validateStatus = new int[1];
        glGetProgramiv(programObjectId, GL_VALIDATE_STATUS, validateStatus, 0);
        Logger.v("Results of validating program: " + validateStatus[0]
                + "\nLog:" + glGetProgramInfoLog(programObjectId));
        return validateStatus[0] != 0;
    }

    public static String readTextFileFromStream(InputStream inputStream) {
        StringBuilder body = new StringBuilder();
        try {

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String nextLine;
            while ((nextLine = bufferedReader.readLine()) != null) {
                body.append(nextLine);
                body.append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body.toString();
    }

    public static int createProgram(int vertexShader, int fragmentShader) {
        int programId = glCreateProgram();

        if (programId == 0) {
            Logger.e("Не получилось создать програму");
        }


        glAttachShader(programId, vertexShader);
        glAttachShader(programId, fragmentShader);

        glLinkProgram(programId);

        final int[] linkStatus = new int[1];
        glGetProgramiv(programId, GL_LINK_STATUS, linkStatus, 0);

        if (linkStatus[0] == 0) {
            Logger.v("Linking of program failed.");
        }

        return programId;
    }

    public static void bindProgram(int programId) {
        GLES20.glUseProgram(programId);
        programInUse = programId;
    }

    public static String readTextFileFromResources(@RawRes int resourceId) {
        return readTextFileFromStream(resources.openRawResource(resourceId));
    }

    public static FloatBuffer createFloatBuffer(float[] floats) {
        return (FloatBuffer) ByteBuffer
                .allocateDirect(floats.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(floats)
                .position(0);
    }

    public static void useDefaultProgram() {
        bindProgram(defaultProgram);
    }

    public static void useNewDefaultProgram() {
        bindProgram(createProgram(defaultVertexShader, defaultFragmentShader));
    }

    public static int getAttr(String name) {
        int res = GLES20.glGetAttribLocation(programInUse, name);

        if (res == -1) Logger.e("Cant find attribute \"" + name + "\"");

        return res;
    }

    public static int getUniform(String name) {
        int res = GLES20.glGetUniformLocation(programInUse, name);

        if (res == -1) Logger.e("Cant find uniform \"" + name + "\"");

        return res;
    }

    //public static int getDefUniform

    public static int loadTexture(@DrawableRes int resourceId) {
        final int[] textureIds = new int[1];
        glGenTextures(1, textureIds, 0);
        if (textureIds[0] == 0) {
            return 0;
        }

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        final Bitmap bitmap = BitmapFactory.decodeResource(
                resources, resourceId, options);

        if (bitmap == null) {
            glDeleteTextures(1, textureIds, 0);
            return 0;
        }

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, textureIds[0]);

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

        bitmap.recycle();

        glBindTexture(GL_TEXTURE_2D, 0);

        return textureIds[0];
    }
}
