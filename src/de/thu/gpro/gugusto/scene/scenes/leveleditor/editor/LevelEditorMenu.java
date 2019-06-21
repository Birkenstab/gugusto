package de.thu.gpro.gugusto.scene.scenes.leveleditor.editor;

import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.scene.scenes.leveleditor.selection.LevelEditorSelectionScene;
import de.thu.gpro.gugusto.scene.scenes.startmenu.StartMenuScene;
import de.thu.gpro.gugusto.ui.components.Menu;

class LevelEditorMenu extends Menu {

    private LevelEditorAction levelEditorAction;

    public LevelEditorMenu(LevelEditorAction levelEditorAction){
        super();

        this.levelEditorAction = levelEditorAction;

        addMenuEntry("Save Map", e -> {
            levelEditorAction.save();
            setVisible(false);
        });
        addMenuEntry("Save and Exit", e -> {
            levelEditorAction.save();
            Game.getInstance().getSceneManager().setScene(new StartMenuScene());
        });
        addMenuEntry("Back to Level Selection", e -> Game.getInstance().getSceneManager().setScene(new LevelEditorSelectionScene()));
        addMenuEntry("Exit Game", e -> System.exit(0));
        build();
    }

}
