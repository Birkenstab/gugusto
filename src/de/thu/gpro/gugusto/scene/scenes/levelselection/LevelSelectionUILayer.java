package de.thu.gpro.gugusto.scene.scenes.levelselection;

import de.thu.gpro.gugusto.scene.UILayer;
import de.thu.gpro.gugusto.ui.components.button.BackToStartButton;

class LevelSelectionUILayer extends UILayer {

    LevelSelectionUILayer(){
        addUIComponent(new BackToStartButton());
        addUIComponent(new LevelSelectionList());
    }


}
