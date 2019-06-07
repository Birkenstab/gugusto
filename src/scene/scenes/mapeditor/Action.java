package scene.scenes.mapeditor;

import game.level.Chunk;
import game.level.Level;
import game.level.LevelLoader;
import game.object.GameObject;
import game.object.blocks.Block;
import game.object.blocks.GrassBlock;
import util.Vector;

import java.nio.file.Paths;
import java.util.List;

public class Action {

    private Level level;

    public Action(Level level){
        this.level = level;
    }

    public void primaryAction(Vector position, Chunk chunk, List<GameObject> gameObjects){
        GrassBlock block = new GrassBlock(position);
        if(chunk.addBlock(block)) gameObjects.add(block);
    }

    public void secondaryAction(Vector position, Chunk chunk, List<GameObject> gameObjects){
        Block block = chunk.removeBlock(position);
        if(block != null) gameObjects.remove(block);
    }

    public void save(){
        LevelLoader.save(level, Paths.get("./test.gug"));
    }

}
