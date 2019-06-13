package scene.scenes.mapeditor;

import collision.CollisionUtil;
import game.level.Chunk;
import game.level.Level;
import game.level.LevelLoader;
import game.object.GameObject;
import game.object.blocks.Block;
import game.object.blocks.BlockFactory;
import game.object.blocks.BlockType;
import game.object.enemies.Enemy;
import game.object.enemies.EnemyFactory;
import util.Vector;

import java.nio.file.Paths;
import java.util.List;

public class MapEditorAction {

    private Level level;
    private GameObject.Type selectedType = GameObject.Type.Block;
    private int selectedId = BlockType.GRASS.getId();

    public MapEditorAction(Level level){
        this.level = level;
    }

    public void primaryAction(Vector position, List<GameObject> gameObjects){
        position.floor();
        Vector chunkPosition = position.clone().divide(Chunk.SIZE);

        if(selectedType == GameObject.Type.Block){
            Block block = BlockFactory.create(selectedId, position);
            if(level.getChunkList().addBlock(block, chunkPosition)) gameObjects.add(block);
        } else if(selectedType == GameObject.Type.Enemy){
            Enemy enemy = EnemyFactory.create(selectedId, position);
            level.getEnemies().add(enemy);
            gameObjects.add(enemy);
        }
    }

    public void secondaryAction(Vector position, List<GameObject> gameObjects){
        Vector chunkPosition = position.clone().divide(Chunk.SIZE);
        Block block = level.getChunkList().removeBlock(position.clone().floor(), chunkPosition);

        if(block != null){
            gameObjects.remove(block);
        } else {
            for(int i = 0; i < level.getEnemies().size(); i++){
                Enemy enemy = level.getEnemies().get(i);

                if(CollisionUtil.contains(position, enemy.getBoundingBox())){
                    level.getEnemies().remove(enemy);
                    gameObjects.remove(enemy);
                    break;
                }
            }
        }
    }

    public void save(){
        LevelLoader.save(level, Paths.get("./test.gug"));
    }

    public void setSelectedObject(int id, GameObject.Type type){
        selectedId = id;
        selectedType = type;
    }

}
