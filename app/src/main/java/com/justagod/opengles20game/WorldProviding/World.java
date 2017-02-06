package com.justagod.opengles20game.WorldProviding;

import com.justagod.opengles20game.WorldProviding.Vectors.ChunkPos;

import java.util.HashMap;
import java.util.Map;

/**
 * Создано Юрием в 04.02.17.
 * <p>
 * =====================================================
 * =            Магия! Руками не трогать!!!           =
 * =====================================================
 */

public class World {

    private static World instance;
    private Map<ChunkPos, Chunk> chunks = new HashMap<>();

    private World() {}

    public static void generateNewWorld() {
        instance = new World();

        ChunkPos pos = new ChunkPos(0, 0);
        instance.chunks.put(pos, new Chunk());
        instance.chunks.get(pos).generate(pos);
    }

    public static World getInstance() {
        return instance;
    }

    public void onDraw() {
        for (Map.Entry<ChunkPos, Chunk> entry : chunks.entrySet()) {
            entry.getValue().onDraw();
        }
    }
}
