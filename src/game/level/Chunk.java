package game.level;

import game.object.GameObject;

import java.util.List;

public class Chunk {

    public static final int SIZE = 32;

    private List<GameObject> gameObjects;

    List<GameObject> getGameObjects(){
        return gameObjects;
    }

}
