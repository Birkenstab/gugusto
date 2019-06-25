package de.thu.gpro.gugusto.scene.scenes.leveleditor.editor;

import de.thu.gpro.gugusto.collision.CollisionUtil;
import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.game.level.Chunk;
import de.thu.gpro.gugusto.game.level.Level;
import de.thu.gpro.gugusto.game.level.io.LevelLoader;
import de.thu.gpro.gugusto.game.object.GameObject;
import de.thu.gpro.gugusto.game.object.blocks.Block;
import de.thu.gpro.gugusto.game.object.enemies.Enemy;
import de.thu.gpro.gugusto.game.object.enemies.EnemyFactory;
import de.thu.gpro.gugusto.scene.scenes.level.LevelScene;
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

    private LevelEditorLayer levelEditorLayer;

    LevelEditorAction(Path path, Level level){
        levelPath = path;
        this.level = level;
    }

    void placeObject(Vector blockPosition, Vector chunkPosition, List<GameObject> gameObjects){
        blockPosition.floor();

        if(selectedType == GameObject.Type.Block){
            Block block = BlockFactory.create(selectedId, blockPosition);
            if(level.getChunkList().addBlock(block, chunkPosition)) gameObjects.add(block);
        } else if(selectedType == GameObject.Type.Enemy){
            Enemy enemy = EnemyFactory.create(selectedId, blockPosition);
            level.getEnemies().add(enemy);
            gameObjects.add(enemy);
        } else if(selectedType == GameObject.Type.Player){
            level.getPlayer().getBoundingBox().getPosition().set(blockPosition);
            level.setStartPosition(blockPosition);
        }
    }

    public void removeObject(Vector blockPosition, Vector chunkPosition, List<GameObject> gameObjects){
        blockPosition.floor();
        Block block = level.getChunkList().removeBlock(blockPosition, chunkPosition);

        if(block != null){
            gameObjects.remove(block);
        } else {
            for(int i = 0; i < level.getEnemies().size(); i++){
                Enemy enemy = level.getEnemies().get(i);

                if(CollisionUtil.contains(blockPosition, enemy.getBoundingBox())){
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
        if(type == GameObject.Type.Player) levelEditorLayer.setMode(LevelEditorMode.Mode.SINGLE);
    }

    public void setLevelEditorLayer(LevelEditorLayer layer){
        levelEditorLayer = layer;
    }

    public void toLevelScene(){
        save();
        levelEditorLayer.removeListeners();
        Game.getInstance().getSceneManager().setScene(new LevelScene(levelPath, buildConfig()));
    }

    public void setConfig(LevelEditorConfig config){
        setSelectedObject(config.getSelectedId(), config.getSelectedType());
        levelEditorLayer.setMode(config.getMode());
    }

    public LevelEditorConfig buildConfig(){
        LevelEditorConfig config = new LevelEditorConfig();
        config.setSelected(selectedId, selectedType);
        config.setCamera(levelEditorLayer.getCamera().getPosition(), levelEditorLayer.getCamera().getScaling());
        config.setMode(levelEditorLayer.getMode());
        return config;
    }

    public String getModeString(){
        return levelEditorLayer.getMode() == LevelEditorMode.Mode.SINGLE ? "Mode: Single" : "Mode: Area";
    }

    public GameObject.Type getSelectedType(){
        return selectedType;
    }

}
