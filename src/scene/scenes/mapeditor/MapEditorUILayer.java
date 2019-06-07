package scene.scenes.mapeditor;

import game.Game;
import input.event.EventCallback;
import input.event.InputEvent;
import input.event.InputEventType;
import input.event.KeyEvent;
import scene.UILayer;
import scene.scenes.startmenu.StartMenuScene;
import ui.components.IconButton;
import ui.components.Menu;
import ui.components.UIComponent;
import ui.icon.Icon;
import util.Size;
import util.Vector;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MapEditorUILayer extends UILayer {

    private Action action;
    private Menu menu;

    public MapEditorUILayer(Action action){
        this.action = action;

        Vector position = new Vector(300, 200);
        Size size = new Size(250, 100);
        List<UIComponent> components = new ArrayList<>();

        IconButton save = new IconButton(new Vector(320, 220), Icon.CHECK);
        IconButton back = new IconButton(new Vector(394, 220), Icon.ARROW_LEFT);
        IconButton close = new IconButton(new Vector(468, 220), Icon.CLOSE);

        save.setClickListener(e -> {
            action.save();
            Game.getInstance().getSceneManager().setScene(new StartMenuScene());
        });
        back.setClickListener(e -> Game.getInstance().getSceneManager().setScene(new StartMenuScene()));
        close.setClickListener(e -> menu.setVisible(false));

        components.add(save);
        components.add(back);
        components.add(close);

        menu = new Menu(position, size, components);
        menu.setVisible(false);

        addUIComponent(menu);
    }


}
