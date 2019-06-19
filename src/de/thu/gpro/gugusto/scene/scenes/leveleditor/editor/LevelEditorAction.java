package de.thu.gpro.gugusto.scene.scenes.leveleditor.editor;

import de.thu.gpro.gugusto.collision.CollisionUtil;
import de.thu.gpro.gugusto.game.level.Chunk;
import de.thu.gpro.gugusto.game.level.Level;
import de.thu.gpro.gugusto.game.level.io.LevelLoader;
import de.thu.gpro.gugusto.game.object.GameObject;
import de.thu.gpro.gugusto.game.object.blocks.Block;
import de.thu.gpro.gugusto.game.object.enemies.Enemy;
import de.thu.gpro.gugusto.game.object.enemies.EnemyFactory;
import de.thu.gpro.gugusto.util.Vector;
import de.thu.gpro.gugusto.game.object.blocks.BlockFactory;
import de.thu.gpro.gugusto.game.object.blocks.BlockType;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

class LevelEditorAction {

    private Path levelPath;
    private Level level;
    private GameObject.Type selectedType = GameObject.Type.Block;
    private int selectedId = BlockType.GRASS.getId();

    LevelEditorAction(Path path, Level level){
        levelPath = path;
        this.level = level;
    }

    void primaryAction(Vector position, List<GameObject> gameObjects){
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
        LevelLoader.save(level, levelPath);
    }

    public void setSelectedObject(int id, GameObject.Type type){
        selectedId = id;
        selectedType = type;
    }

}
