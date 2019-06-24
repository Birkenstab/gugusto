package de.thu.gpro.gugusto.scene.scenes.leveleditor.selection;

import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.input.event.EventCallback;
import de.thu.gpro.gugusto.input.event.InputEventType;
import de.thu.gpro.gugusto.input.event.KeyEvent;
import de.thu.gpro.gugusto.scene.UILayer;
import de.thu.gpro.gugusto.scene.scenes.startmenu.StartMenuScene;
import de.thu.gpro.gugusto.ui.components.BackgroundCurtain;
import de.thu.gpro.gugusto.ui.components.button.BackToStartButton;
import de.thu.gpro.gugusto.ui.components.button.IconButton;
import de.thu.gpro.gugusto.ui.icon.Icon;
import de.thu.gpro.gugusto.util.Vector;

public class LevelEditorSelectionUILayer extends UILayer {

    private LevelEditorSelectionConfig config;

    public LevelEditorSelectionUILayer(){
        BackToStartButton backButton = new BackToStartButton(){
            @Override
            protected boolean shouldGoBack(KeyEvent e){
                return !config.isActive() && super.shouldGoBack(e);
            }
        };

        config = new LevelEditorSelectionConfig();

        addUIComponent(new BackgroundCurtain());
        addUIComponent(backButton);
        addUIComponent(config);
        addUIComponent(new LevelEditorSelectionLevelList());
    }

}
