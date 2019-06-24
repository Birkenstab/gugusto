package de.thu.gpro.gugusto.scene.scenes.levelselection;

import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.scene.UILayer;
import de.thu.gpro.gugusto.ui.components.BackgroundCurtain;
import de.thu.gpro.gugusto.ui.components.Panel;
import de.thu.gpro.gugusto.ui.components.button.BackToStartButton;
import de.thu.gpro.gugusto.util.Size;
import de.thu.gpro.gugusto.util.Vector;

import java.awt.*;

class LevelSelectionUILayer extends UILayer {

    LevelSelectionUILayer(){
        addUIComponent(new BackgroundCurtain());
        addUIComponent(new BackToStartButton());
        addUIComponent(new LevelSelectionList());
    }


}
