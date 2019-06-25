package de.thu.gpro.gugusto.scene.scenes.leveleditor.selection;

import de.thu.gpro.gugusto.input.event.KeyEvent;
import de.thu.gpro.gugusto.scene.UILayer;
import de.thu.gpro.gugusto.ui.components.BackgroundCurtain;
import de.thu.gpro.gugusto.ui.components.button.BackToStartButton;

public class LevelEditorSelectionUILayer extends UILayer {

    private LevelEditorSelectionConfig config;
    private ConfirmationPopup confirmationPopup;
    private LevelEditorSelectionLevelList levelEditorSelectionLevelList;

    public LevelEditorSelectionUILayer(){
        BackToStartButton backButton = new BackToStartButton(){
            @Override
            protected boolean shouldGoBack(KeyEvent e){
                return !config.isActive() && super.shouldGoBack(e);
            }
        };

        config = new LevelEditorSelectionConfig();
        confirmationPopup = new ConfirmationPopup();
        levelEditorSelectionLevelList = new LevelEditorSelectionLevelList(confirmationPopup);
        confirmationPopup.setCallback(levelEditorSelectionLevelList.getCallback());

        addUIComponent(new BackgroundCurtain());
        addUIComponent(backButton);
        addUIComponent(config);
        addUIComponent(levelEditorSelectionLevelList);
        addUIComponent(confirmationPopup);
    }

}
