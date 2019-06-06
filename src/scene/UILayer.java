package scene;

import game.Camera;
import graphic.GraphicSystem;
import graphic.Window;
import input.event.InputEventType;
import input.event.MouseEvent;
import ui.Button;
import ui.UIComponent;
import util.Vector;

import java.util.ArrayList;
import java.util.List;

public class UILayer extends Layer {

    private static final Camera defaultCamera = new Camera(new Vector(), 1);

    public UILayer(){
        addListener(InputEventType.MOUSE_CLICK, this::onMouseClick);
        addListener(InputEventType.MOUSE_MOVE, this::onMouseMove);
    }

    private boolean onMouseClick(MouseEvent e){
        for(int i = gameObjects.size() - 1; i >= 0; i--){

        }

        return false;
    }

    private boolean onMouseMove(MouseEvent e){
        Vector point = new Vector(e.getX(), e.getY() - Window.TITLEBAR_HEIGHT);
        boolean consumed = false;

        for(int i = gameObjects.size() - 1; i >= 0; i--){
            UIComponent component = (UIComponent)gameObjects.get(i);

            if(component instanceof Button){
                boolean contains = component.contains(point);
                ((Button)component).setHover(!consumed && contains);
                if(contains) consumed = true;
            }
        }

        return false;
    }

    protected void addComponent(UIComponent component){
        gameObjects.add(component);
    }

}
