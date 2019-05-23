package game.level;

import game.object.Block;
import game.object.GameObject;
import util.Vector;

import java.util.ArrayList;
import java.util.List;

public class LevelLoader {
    public static Level loadTestLevel() {
        int height = 2;
        int width = 10;
        List<List<Chunk>> chunks = new ArrayList<>();

        for (int x = 0; x < width; x++) {
            chunks.add(new ArrayList<>(height));
            for (int y = 0; y < height; y++) {
                List<GameObject> gameObjects = new ArrayList<>();

                for (int i = 0; i < 10; i++) {
                    gameObjects.add(new Block(new Vector(x * Chunk.SIZE + i * Block.SIZE, y * Chunk.SIZE + i * Block.SIZE)));
                }

                Chunk chunk = new Chunk(gameObjects);
                chunks.get(x).add(chunk);
            }
        }


        ChunkList chunkList = new ChunkList(chunks, width, height);
        return new Level("Test", chunkList, new Vector(40, 20));
    }
}
