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
import java.nio.IntBuffer;
import java.util.Stack;

import static android.opengl.GLES20.GL_COMPILE_STATUS;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_LINK_STATUS;
import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_UNSIGNED_INT;
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
    private static Program programInUse;
    private static float[] matrix = new float[16];
    private static Stack<float[]> matrixStack = new Stack<>();

    private static boolean verticesBinded;
    private static boolean colorsBinded;
    private static boolean textureCoordsBinded;

    private static Program defaultProgram;



    public static void init(Resources res) {
        resources = res;
        defaultProgram = GLPrograms.createProgram(R.raw.vertex_shader, R.raw.fragment_shader, "u_Matrix", "a_Position", "a_Color", "");
        Matrix.setIdentityM(matrix, 0);
        useDefaultProgram();
        setMatrix();
    }

    public static void setMatrix() {
        if (programInUse == null) {
            Logger.e("Програма не привязана!");
            throw new RuntimeException("Програма не привязана!");
        }
        GLES20.glUniformMatrix4fv(programInUse.getMatrixUniformId(), 1, false, matrix, 0);
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

    public static int createProgram(String vertexShader, String fragmentShader) {
        int programId = glCreateProgram();

        if (programId == 0) {
            Logger.e("Не получилось создать програму");
        }


        glAttachShader(programId, compileVertexShader(vertexShader));
        glAttachShader(programId, compileFragmentShader(fragmentShader));

        glLinkProgram(programId);

        final int[] linkStatus = new int[1];
        glGetProgramiv(programId, GL_LINK_STATUS, linkStatus, 0);

        if (linkStatus[0] == 0) {
            Logger.v("Linking of program failed.\n" +
                    "Log: " + glGetProgramInfoLog(programId));
        }

        return programId;
    }

    public static int createProgram(@RawRes int vertexShader, @RawRes int fragmentShader) {
        return createProgram(readTextFileFromResources(vertexShader), readTextFileFromResources(fragmentShader));
    }

    public static void bindProgram(Program program) {
        program.bind();
        if (programInUse != null) {
            programInUse.unBind();
        }
        programInUse = program;
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
        bindProgram(GLPrograms.createProgram(R.raw.vertex_shader, R.raw.fragment_shader, "u_Matrix", "a_Position", "", "a_Texture"));
    }

    public static Program getProgramInUse() {
        return programInUse;
    }

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

    public static void bindVertices(float[] vertices) throws RuntimeException {
        bindVertices(vertices, 3);
    }

    public static void bindVertices(float[] vertices, int size) throws RuntimeException {
        if (programInUse == null) {
            Logger.e("Програма не привязана!");
            throw new RuntimeException("Програма не привязана!");
        }
        GLES20.glVertexAttribPointer(programInUse.getPositionAttributeId(), size, GL_FLOAT, false, 0, createFloatBuffer(vertices));
        GLES20.glEnableVertexAttribArray(programInUse.getPositionAttributeId());

        verticesBinded = true;
    }

    public static void bindColors(float[] colors) {
        bindColors(colors, 4);
    }

    public static void bindColors(float[] colors, int size) throws RuntimeException {
        if (programInUse == null) {
            Logger.e("Програма не привязана!");
            throw new RuntimeException("Програма не привязана!");
        }
        GLES20.glVertexAttribPointer(programInUse.getColorAttributeId(), size, GL_FLOAT, false, 0, createFloatBuffer(colors));
        GLES20.glEnableVertexAttribArray(programInUse.getColorAttributeId());

        colorsBinded = true;
    }

    public static void setColorUniform(float red, float green, float blue, float alpha) {
        if (programInUse == null) {
            Logger.e("Програма не привязана");
            return;
        }
        int location = programInUse.getUniform("u_Color");

        GLES20.glUniform4f(location, red, green, blue, alpha);
    }

    public static void draw(int mode, int[] indices) {
        if (!colorsBinded) {
            bindColors(createIndentyColorArray(indices.length));
            colorsBinded = false;
        }
        GLES20.glDrawElements(mode, indices.length, GL_UNSIGNED_INT, createIndicesBuffer(indices));
    }

    public static void perspective(int width, int height) {
        float aspect = width / height;

        GLES20.glViewport(0, 0, width, height);

        loadIdentity();
        Matrix.perspectiveM(matrix, 0, 45, aspect, 0.1f, 100f);
        setMatrix();
    }

    public static void loadIdentity() {
        Matrix.setIdentityM(matrix, 0);
        setMatrix();
    }

    private static float[] createIndentyColorArray(int size) {
        float[] res = new float[size * 4];

        for (int i = 0; i < size * 4; i++) {
            res[i] = 1;
        }

        return res;
    }

    public static IntBuffer createIndicesBuffer(int[] indices) {
        return (IntBuffer) ByteBuffer
                .allocateDirect(indices.length * Integer.SIZE / 8)
                .order(ByteOrder.nativeOrder())
                .asIntBuffer()
                .put(indices)
                .position(0);

    }

    public static void translate(float x, float y, float z) {
        Matrix.translateM(matrix, 0, x, y, z);
        setMatrix();
    }
}
