package com.justagod.opengles20game;

import com.justagod.opengles20game.WorldProviding.World;

/**
 * Создано Юрием в 06.02.17.
 * <p>
 * =====================================================
 * =            Магия! Руками не трогать!!!           =
 * =====================================================
 */

public final class Tanks {
    private static boolean initiated;

    public static void main(String[] args) {
        if (initiated) return;

        World.generateNewWorld();

        initiated = true;
    }
}
