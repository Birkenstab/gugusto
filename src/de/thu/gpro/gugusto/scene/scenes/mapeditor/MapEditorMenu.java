package de.thu.gpro.gugusto.scene.scenes.mapeditor;

import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.scene.scenes.startmenu.StartMenuScene;
import de.thu.gpro.gugusto.ui.components.Menu;

public class MapEditorMenu extends Menu {

    private MapEditorAction mapEditorAction;

    public MapEditorMenu(MapEditorAction mapEditorAction){
        super();

        this.mapEditorAction = mapEditorAction;

        addMenuEntry("Save Map", e -> {
            mapEditorAction.save();
            setVisible(false);
        });
        addMenuEntry("Save and Exit", e -> {
            mapEditorAction.save();
            Game.getInstance().getSceneManager().setScene(new StartMenuScene());
        });
        addMenuEntry("Back to Main Menu", e -> Game.getInstance().getSceneManager().setScene(new StartMenuScene()));
        addMenuEntry("Exit Game", e -> System.exit(0));
        build();
    }

}
