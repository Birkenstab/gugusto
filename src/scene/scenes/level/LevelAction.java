package scene.scenes.level;

import scene.UILayer;

public class LevelAction {
    private LevelLayer levelLayer;
    private UILayer uiLayer;

    public void restartLevel() {
        levelLayer.restartLevel();
    }

    public void endLevel() {

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

    public void setUiLayer(UILayer uiLayer) {
        this.uiLayer = uiLayer;
    }
}
