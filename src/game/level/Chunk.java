package game.level;

import game.object.Block;
import game.object.GameObject;

import java.util.ArrayList;
import java.util.List;

public class Chunk {

    public static final int SIZE = 32;

    private List<GameObject> gameObjects;

    public Chunk(List<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
    }

    public List<GameObject> getGameObjects(){
        return gameObjects;
    }

    public List<GameObject> getBlocks(){
        List<GameObject> blocks = new ArrayList<>();

        for(GameObject block : gameObjects){
            if(block != null) blocks.add(block);
        }

        return blocks;
    }

}
