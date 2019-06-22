package de.thu.gpro.gugusto.scene.scenes.leveleditor.editor;

import de.thu.gpro.gugusto.game.level.Level;
import de.thu.gpro.gugusto.game.level.io.LevelLoader;
import de.thu.gpro.gugusto.scene.Scene;

import java.nio.file.Path;
import java.nio.file.Paths;

public class LevelEditorScene extends Scene {

    private LevelEditorLayer levelEditorLayer;
    private LevelEditorUILayer levelEditorUILayer;

    private LevelEditorAction levelEditorAction;

    public LevelEditorScene(Path path){
        build(path, LevelLoader.load(path), null);
    }

    public LevelEditorScene(Path path, Level level){
        build(path, level, null);
    }

    public LevelEditorScene(Path path, LevelEditorConfig config){
        build(path, LevelLoader.load(path), config.getCamera());
        levelEditorAction.setConfig(config);
    }

    private void build(Path path, Level level, LevelEditorCamera camera){
        levelEditorAction = new LevelEditorAction(path, level);
        levelEditorUILayer = new LevelEditorUILayer(levelEditorAction);
        levelEditorLayer = new LevelEditorLayer(level, levelEditorAction, camera);

        levelEditorAction.setLevelEditorLayer(levelEditorLayer);

        addLayer(levelEditorLayer);
        addLayer(levelEditorUILayer);
    }

}
