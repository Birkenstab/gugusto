package de.thu.gpro.gugusto.scene.scenes.leveleditor.selection;

import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.input.event.EventCallback;
import de.thu.gpro.gugusto.input.event.InputEventType;
import de.thu.gpro.gugusto.input.event.KeyEvent;
import de.thu.gpro.gugusto.scene.UILayer;
import de.thu.gpro.gugusto.scene.scenes.startmenu.StartMenuScene;
import de.thu.gpro.gugusto.ui.components.button.IconButton;
import de.thu.gpro.gugusto.ui.icon.Icon;
import de.thu.gpro.gugusto.util.Vector;

public class LevelEditorSelectionUILayer extends UILayer {

    private LevelEditorSelectionConfig config;

    public LevelEditorSelectionUILayer(){
        BackButton backButton = new BackButton(new Vector(10, 10));

        config = new LevelEditorSelectionConfig();
        backButton.setClickListener(b -> backToStartMenu());

        addUIComponent(backButton);
        addUIComponent(config);
        addUIComponent(new LevelEditorSelectionLevelList());
    }

    private boolean backToStartMenu(){
        Game.getInstance().getSceneManager().setScene(new StartMenuScene());
        return true;
    }

    private class BackButton extends IconButton {

        public BackButton(Vector position) {
            super(position, Icon.ARROW_LEFT);

            addListener(InputEventType.KEY_DOWN, (EventCallback<KeyEvent>) e -> {
                if(!config.isActive() && e.getKeyCode() == KeyEvent.VK_ESCAPE) return backToStartMenu();
                return false;
            });
        }
    }

}
