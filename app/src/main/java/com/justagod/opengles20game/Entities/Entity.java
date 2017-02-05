package com.justagod.opengles20game.Entities;

import com.justagod.opengles20game.WorldProviding.Vectors.Vector;

/**
 * Создано Юрием в 04.02.17.
 * <p>
 * =====================================================
 * =            Магия! Руками не трогать!!!           =
 * =====================================================
 */

public abstract class Entity {

    public abstract boolean isUpdatable();

    public void update() {

    }

    public abstract void onDraw();

    public abstract boolean isVectorInBounds(Vector vector);

    public boolean isIntangible() {
        return false;
    }

    public abstract void initGL();


}
