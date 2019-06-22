package de.thu.gpro.gugusto.scene.scenes.level;

import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.game.level.Profile;
import de.thu.gpro.gugusto.game.level.io.LevelUtil;
import de.thu.gpro.gugusto.game.level.io.ProfileLoader;
import de.thu.gpro.gugusto.scene.scenes.leveleditor.editor.LevelEditorConfig;
import de.thu.gpro.gugusto.scene.scenes.leveleditor.editor.LevelEditorScene;
import de.thu.gpro.gugusto.scene.scenes.levelselection.LevelSelectionScene;
import de.thu.gpro.gugusto.scene.scenes.startmenu.StartMenuScene;

import java.nio.file.Path;

public class LevelAction {

    private LevelLayer levelLayer;
    private LevelUILayer uiLayer;
    private LevelEditorConfig config;
    private Path levelPath;

    public LevelAction(Path levelPath){
        this.levelPath = levelPath;
    }

    public void restartLevel() {
        if (uiLayer != null)
            uiLayer.restartLevel();
        levelLayer.restartLevel();
    }

    public void endLevelByDeath() {
        uiLayer.showDeathScreen();
    }

    public void endLevelByWin() {
        uiLayer.showLevelWinScreen();
        int levelId = LevelUtil.getPlayableLevels().indexOf(levelPath);
        Profile profile = ProfileLoader.load();
        int lulid = profile.getLastUnlockedLevelId();

        if(lulid == levelId){
            profile.unlockNextLevel();
            ProfileLoader.save(profile);
        }
    }

    public void pause() {
        levelLayer.pause();
    }

    public void resume() {
        if(levelLayer != null) levelLayer.resume();
    }

    public void setLevelLayer(LevelLayer levelLayer) {
        this.levelLayer = levelLayer;
    }

    public void setUiLayer(LevelUILayer uiLayer) {
        this.uiLayer = uiLayer;
    }

    public void backToLevelSelector() {
        Game.getInstance().getSceneManager().setScene(new LevelSelectionScene());
    }

    public void backToLevelEditorScene(){
        Game.getInstance().getSceneManager().setScene(new LevelEditorScene(levelPath, config));
    }

    public void setLevelEditorConfig(LevelEditorConfig config){
        this.config = config;
    }

    public boolean hasConfig(){
        return config != null;
    }
}
