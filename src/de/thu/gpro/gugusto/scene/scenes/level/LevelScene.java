package de.thu.gpro.gugusto.scene.scenes.level;

import de.thu.gpro.gugusto.scene.Scene;

import java.nio.file.Path;

public class LevelScene extends Scene {

    private LevelAction levelAction;
    private LevelLayer levelLayer;

    public LevelScene(Path levelPath) {
        levelAction = new LevelAction();
        levelLayer = new LevelLayer(levelPath, levelAction);
        levelAction.setLevelLayer(levelLayer);
        LevelUILayer uiLayer = new LevelUILayer(levelAction);
        levelAction.setUiLayer(uiLayer);

        addLayer(levelLayer);
        addLayer(uiLayer);
    }

    public LevelLayer getLevelLayer() {
        return levelLayer;
    }
}
