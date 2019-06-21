package de.thu.gpro.gugusto.scene.scenes.level;

import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.scene.scenes.levelselection.LevelSelectionScene;
import de.thu.gpro.gugusto.scene.scenes.startmenu.StartMenuScene;
import de.thu.gpro.gugusto.ui.components.Menu;

public class LevelMenu extends Menu {

    private LevelAction levelAction;

    LevelMenu(LevelAction levelAction){
        super();

        this.levelAction = levelAction;

        addMenuEntry("Reset Level", e -> {
            levelAction.restartLevel();
            setVisible(false);
        });

        if(levelAction.hasConfig()){
            addMenuEntry("Back to Level Editor", e -> levelAction.backToLevelEditorScene());
        }

        addMenuEntry("Back to Level Selection", e -> Game.getInstance().getSceneManager().setScene(new LevelSelectionScene()));
        addMenuEntry("Exit Game", e -> System.exit(0));
        build();
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            levelAction.pause();
        } else {
            levelAction.resume();
        }
    }
}
