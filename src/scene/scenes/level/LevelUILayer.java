package scene.scenes.level;

import game.Game;
import scene.UILayer;
import scene.scenes.startmenu.StartMenuScene;
import ui.components.FpsCounter;
import ui.components.Menu;
import ui.components.UIComponent;
import ui.components.button.TextButton;
import ui.components.button.TextButtonFactory;
import util.Size;
import util.Vector;

import java.util.ArrayList;
import java.util.List;

public class LevelUILayer extends UILayer {

    private LevelAction levelAction;

    public LevelUILayer(LevelAction levelAction){
        this.levelAction = levelAction;


        addUIComponent(new LevelMenu(levelAction));
        addUIComponent(new FpsCounter(new Vector(Game.WIDTH - FpsCounter.WIDTH - 10, 10)));
    }

}
