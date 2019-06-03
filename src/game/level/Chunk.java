package game.level;

import game.object.Block;
import game.object.GameObject;
import game.object.StaticGameObject;

import java.util.ArrayList;
import java.util.List;

public class Chunk {

    public static final int SIZE = 32;

    /**
     * Gibt den Chunk zurück, in dem sich die Position befindet
     * @param position
     * @return
     */
    public static int getChunkNo(double position) {
        double no = position / Chunk.SIZE;
        return (int)Math.floor(no);
    }

    /**
     * Gibt innerhalb des Chunks die Position zurück (relativ zur oberen linken Ecke des Chunks)
     * @param position
     * @return
     */
    public static double getPositionInChunk(double position) {
        double mod = position % Chunk.SIZE;
        if (mod >= 0)
            return mod;
        return mod + Chunk.SIZE;
    }

    private List<StaticGameObject> gameObjects;

    public Chunk(List<StaticGameObject> gameObjects) {
        this.gameObjects = gameObjects;
    }

    public List<StaticGameObject> getGameObjects(){
        return gameObjects;
    }

}
