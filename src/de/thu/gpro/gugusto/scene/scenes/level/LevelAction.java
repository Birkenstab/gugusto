package de.thu.gpro.gugusto.scene.scenes.level;

import de.thu.gpro.gugusto.scene.UILayer;

public class LevelAction {
    private LevelLayer levelLayer;
    private LevelUILayer uiLayer;

    public void restartLevel() {
        uiLayer.restartLevel();
        levelLayer.restartLevel();
    }

    public void endLevelByDeath() {
        uiLayer.showDeathScreen();
    }

    public void pause() {
        levelLayer.pause();
    }

    public void resume() {
        levelLayer.resume();
    }


    public void setLevelLayer(LevelLayer levelLayer) {
        this.levelLayer = levelLayer;
    }

    public void setUiLayer(LevelUILayer uiLayer) {
        this.uiLayer = uiLayer;
    }
}
