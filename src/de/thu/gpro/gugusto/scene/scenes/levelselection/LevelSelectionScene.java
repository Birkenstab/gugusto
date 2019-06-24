package de.thu.gpro.gugusto.scene.scenes.levelselection;

import de.thu.gpro.gugusto.scene.Scene;
import de.thu.gpro.gugusto.scene.scenes.level.LevelAction;
import de.thu.gpro.gugusto.scene.scenes.level.LevelLayer;

public class LevelSelectionScene extends Scene {

    public LevelSelectionScene(){
        addLayer(new BackgroundLayer());
        addLayer(new LevelSelectionUILayer());
    }

}
