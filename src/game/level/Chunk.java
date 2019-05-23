package game.level;

import game.object.GameObject;

import java.util.List;

public class Chunk {

    public static final int SIZE = 512;

    private List<GameObject> gameObjects;

    public Chunk(List<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
    }

    public List<GameObject> getGameObjects(){
        return gameObjects;
    }

}
