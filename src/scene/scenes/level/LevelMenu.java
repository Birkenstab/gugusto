package scene.scenes.level;

import game.Game;
import scene.scenes.startmenu.StartMenuScene;
import ui.components.Menu;

public class LevelMenu extends Menu {

    private LevelAction levelAction;

    LevelMenu(LevelAction levelAction){
        super();

        this.levelAction = levelAction;

        addMenuEntry("Reset Level", e -> {
            levelAction.restartLevel();
            setVisible(false);
        });
        addMenuEntry("Back to Main Menu", e -> Game.getInstance().getSceneManager().setScene(new StartMenuScene()));
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
