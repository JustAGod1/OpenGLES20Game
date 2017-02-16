package com.justagod.opengles20game;

import android.content.Context;
import android.opengl.Matrix;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.justagod.opengles20game.Stuff.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;

import static com.justagod.opengles20game.Rendering.Tesselator.printMatrix;
import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.justagod.opengles20game", appContext.getPackageName());

        float[] test = new float[16];
        Matrix.setIdentityM(test, 0);
        Matrix.translateM(test, 0, 1, 6, 0);
        Logger.i(String.format("Translated matrix: \n%s", printMatrix(test)));
    }
}
