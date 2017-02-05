package com.justagod.opengles20game;

import com.justagod.opengles20game.WorldProviding.Vectors.BlockPos;
import com.justagod.opengles20game.WorldProviding.Vectors.Vector;

import org.junit.Assert;
import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void testVectorTransformation() {
        Vector vector = new Vector(-0.1f, 0.2f, 1);

        Assert.assertEquals(new BlockPos(-1, 0, 1), vector.toBlockPos());
    }
}