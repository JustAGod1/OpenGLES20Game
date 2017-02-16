package com.justagod.opengles20game.Rendering;

import android.content.res.Resources;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.justagod.opengles20game.Entities.BlockWrapper;
import com.justagod.opengles20game.R;
import com.justagod.opengles20game.Stuff.Logger;
import com.justagod.opengles20game.WorldBlocks.Blocks;
import com.justagod.opengles20game.WorldProviding.Vectors.BlockPos;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static com.justagod.opengles20game.Rendering.Tesselator.popMatrix;
import static com.justagod.opengles20game.Rendering.Tesselator.printMatrix;
import static com.justagod.opengles20game.Rendering.Tesselator.pushMatrix;
import static com.justagod.opengles20game.Rendering.Tesselator.readTextFileFromResources;
import static com.justagod.opengles20game.Rendering.Tesselator.rotate;
import static com.justagod.opengles20game.Rendering.Tesselator.scale;
import static com.justagod.opengles20game.Rendering.Tesselator.translate;
import static com.justagod.opengles20game.Stuff.Logger.infoOneTime;


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
    private float angle = 0;
    private BlockWrapper block;

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
        //Tesselator.setColorUniform(1, 0, 0, 1);
        GLES20.glClearColor(0, 0, 0, 1);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        block = Blocks.bedrock.createWrapper(new BlockPos(0, 0));
        block.initGL();


        Logger.i("Rendering is started!");
        Logger.printToFile("Test");
        Logger.sendFile();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        //perspective(width, height);
        Tesselator.matrixMode(Tesselator.MatrixMode.PROJECTION);
        scale(1 / (((float) width) / ((float) height)), 1, 1);
        Tesselator.matrixMode(Tesselator.MatrixMode.VIEW);
        Logger.i("Changed");
    }



    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);





        angle += 2;

        pushMatrix();
        {
            translate(0, -0.5f, 0);
            rotate(angle, 0, 0, 1);

            pushMatrix();
            {
                block.onDraw();
                Logger.infoWithDelay(Tesselator.printMatricesInfo(), "Matrix info", 1600);
                //World.getInstance().onDraw();
            }
            popMatrix();
        }
        popMatrix();
    }

    private void drawTest() {
        float[] vertices = {
                // 0
                0, 0, 0,
                1, 0, 0,
                1, 1, 0,
                0, 1, 0,

                // 4
                1, 0, 0,
                1, 1, 0,
                1, 1, 1,
                1, 0, 1,

                // 8
                0, 1, 0,
                1, 1, 0,
                1, 1, 1,
                0, 1, 1,

                // 12
                0, 0, 0,
                0, 1, 0,
                0, 1, 1,
                0, 0, 1,

                // 16
                0, 0, 0,
                1, 0, 0,
                1, 0, 1,
                0, 0, 1,


                // 20
                0, 0, 1,
                1, 0, 1,
                1, 1, 1,
                0, 1, 1
        };
        float[] colors = {
                1, 0, 0,
                0, 1, 0,
                0, 1, 1,
                1, 0, 1,

                1, 0, 0,
                0, 1, 0,
                0, 1, 1,
                1, 0, 1,

                1, 0, 0,
                0, 1, 0,
                0, 1, 1,
                1, 0, 1,

                1, 0, 0,
                0, 1, 0,
                0, 1, 1,
                1, 0, 1,

                1, 0, 0,
                0, 1, 0,
                0, 1, 1,
                1, 0, 1,

                1, 0, 0,
                0, 1, 0,
                0, 1, 1,
                1, 0, 1
        };
        int[] indices = {0,1,2, 0,3,2, 4,5,6, 4,7,6, 8,9,10, 8,11,10, 12,13,14, 12,15,14, 16,17,18, 16,19,18, 20,21,22, 20,23,22};

        int program = Tesselator.createProgram(readTextFileFromResources(R.raw.vertex_shader), readTextFileFromResources(R.raw.fragment_shader));
        GLES20.glUseProgram(program);

        float[] mat = new float[16];
        float[] projMat = new float[16];
        float[] MVPMatrix = new float[16];
        Matrix.setIdentityM(mat, 0);
        Matrix.setIdentityM(projMat, 0);
        final float ratio = (float) (width) / height;
        final float left = -ratio;
        final float right = ratio;
        final float bottom = -1.0f;
        final float top = 1.0f;
        final float near = 1.0f;
        final float far = 20.0f;
        //Matrix.frustumM(projMat, 0, left, right, bottom, top, near, far);
        Matrix.setLookAtM(mat, 0, 0.5f, 0.5f, 1, 0.5f, 0.5f, 0, 0, 1, 0);
        //Matrix.translateM(mat, 0, -0.5f, -0.5f, 1);
        //Matrix.rotateM(mat, 0, angle, 1, 1, 0);


        Matrix.multiplyMM(MVPMatrix, 0, mat, 0, projMat, 0);

        MVPMatrix[10] *= 037777777777;

        infoOneTime("View matrix", printMatrix(mat), "Test-View Matrix");
        infoOneTime("Projection matrix", printMatrix(projMat), "Test-Projection Matrix");
        infoOneTime("Final matrix", Tesselator.printMatrix(MVPMatrix), "Test-Final Matrix");

        GLES20.glUniformMatrix4fv(GLES20.glGetUniformLocation(program, "u_Matrix"), 1, false, MVPMatrix, 0);

        GLES20.glUniform4f(GLES20.glGetUniformLocation(program, "u_Color"), 1, 1, 1, 1);

        GLES20.glVertexAttribPointer(GLES20.glGetAttribLocation(program, "a_Position"), 3, GLES20.GL_FLOAT, false, 0, Tesselator.createFloatBuffer(vertices));
        glEnableVertexAttribArray(GLES20.glGetAttribLocation(program, "a_Position"));

        GLES20.glVertexAttribPointer(GLES20.glGetAttribLocation(program, "a_Color"), 4, GL_FLOAT, false, 0, Tesselator.createFloatBuffer(colors));
        glEnableVertexAttribArray(GLES20.glGetAttribLocation(program, "a_Color"));

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, indices.length, GLES20.GL_UNSIGNED_INT, Tesselator.createIndicesBuffer(indices));

        GLES20.glDeleteProgram(program);
    }
}
