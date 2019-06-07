package scene.scenes.level;

import game.Game;
import game.level.Level;
import scene.UILayer;
import scene.scenes.startmenu.StartMenuScene;
import ui.components.FpsCounter;
import ui.components.IconButton;
import ui.components.Menu;
import ui.components.UIComponent;
import ui.icon.Icon;
import util.Size;
import util.Vector;

import java.util.ArrayList;
import java.util.List;

public class LevelUILayer extends UILayer {

    private Menu menu;
    private Action action;

    public LevelUILayer(Action action){
        this.action = action;

        Vector position = new Vector(300, 200);
        Size size = new Size(250, 100);
        List<UIComponent> components = new ArrayList<>();

        IconButton back = new IconButton(new Vector(320, 220), Icon.ARROW_LEFT);
        IconButton retry = new IconButton(new Vector(394, 220), Icon.RELOAD);

        back.setClickListener(e -> Game.getInstance().getSceneManager().setScene(new StartMenuScene()));
        retry.setClickListener(e -> {
            action.resetLevel();
            menu.setVisible(false);
        });

        components.add(back);
        components.add(retry);

        menu = new Menu(position, size, components);
        menu.setVisible(false);

        addUIComponent(menu);
        addUIComponent(new FpsCounter(new Vector(Game.WIDTH - FpsCounter.WIDTH - 10, 10)));
    }

}
