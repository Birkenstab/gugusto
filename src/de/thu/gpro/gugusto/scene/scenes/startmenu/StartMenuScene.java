package de.thu.gpro.gugusto.scene.scenes.startmenu;

import de.thu.gpro.gugusto.scene.Scene;
import de.thu.gpro.gugusto.scene.scenes.level.LevelAction;
import de.thu.gpro.gugusto.scene.scenes.level.LevelLayer;

public class StartMenuScene extends Scene {

    public StartMenuScene(){
        LevelAction levelAction = new LevelAction(null);
        LevelLayer levelLayer = new LevelLayer(null, levelAction, true);
        levelAction.setLevelLayer(levelLayer);
        addLayer(levelLayer);
        addLayer(new StartMenuUILayer());
    }

}
