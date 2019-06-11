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

public class MapEditorAction {

    private Level level;

    public MapEditorAction(Level level){
        this.level = level;
    }

    public void primaryAction(Vector position, Vector chunkPosition, List<GameObject> gameObjects){
        GrassBlock block = new GrassBlock(position);

        if(level.getChunkList().addBlock(block, chunkPosition)) gameObjects.add(block);
    }

    public void secondaryAction(Vector position, Vector chunkPosition, List<GameObject> gameObjects){
        Block block = level.getChunkList().removeBlock(position, chunkPosition);
        if(block != null) gameObjects.remove(block);
    }

    public void save(){
        LevelLoader.save(level, Paths.get("./test.gug"));
    }

}
