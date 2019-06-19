package de.thu.gpro.gugusto.scene.scenes.level;

import de.thu.gpro.gugusto.scene.Scene;
import de.thu.gpro.gugusto.scene.scenes.leveleditor.editor.LevelEditorConfig;

import java.nio.file.Path;

public class LevelScene extends Scene {

    private LevelAction levelAction;
    private LevelLayer levelLayer;

    public LevelScene(Path levelPath, LevelEditorConfig config){
        build(levelPath, config);
    }

    public LevelScene(Path levelPath) {
        build(levelPath, null);
    }

    private void build(Path levelPath, LevelEditorConfig config){
        levelAction = new LevelAction(levelPath);
        levelAction.setLevelEditorConfig(config);

        LevelUILayer uiLayer = new LevelUILayer(levelAction);
        levelLayer = new LevelLayer(levelPath, levelAction);

        levelAction.setLevelLayer(levelLayer);
        levelAction.setUiLayer(uiLayer);

        addLayer(levelLayer);
        addLayer(uiLayer);
    }

    public LevelLayer getLevelLayer() {
        return levelLayer;
    }
}
