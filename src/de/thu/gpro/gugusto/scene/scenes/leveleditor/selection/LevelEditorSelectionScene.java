package de.thu.gpro.gugusto.scene.scenes.leveleditor.selection;

import de.thu.gpro.gugusto.scene.Scene;
import de.thu.gpro.gugusto.scene.scenes.levelselection.BackgroundLayer;

public class LevelEditorSelectionScene extends Scene {

    public LevelEditorSelectionScene(){
        addLayer(new BackgroundLayer());
        addLayer(new LevelEditorSelectionUILayer());

    }

}
