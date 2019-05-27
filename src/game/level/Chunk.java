package game.level;

import game.object.Block;
import game.object.GameObject;
import game.object.StaticGameObject;

import java.util.ArrayList;
import java.util.List;

public class Chunk {

    public static final int SIZE = 32;

    private List<StaticGameObject> gameObjects;

    public Chunk(List<StaticGameObject> gameObjects) {
        this.gameObjects = gameObjects;
    }

    public List<StaticGameObject> getGameObjects(){
        return gameObjects;
    }

}
