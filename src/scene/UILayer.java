package scene;

import game.Camera;
import game.object.GameObject;
import graphic.Window;
import input.event.InputEvent;
import input.event.InputEventType;
import input.event.KeyEvent;
import input.event.MouseEvent;

import ui.components.UIComponent;
import util.Vector;

import java.awt.*;
import java.util.List;

public class UILayer extends Layer {

    public UILayer(){
        addListener(InputEventType.MOUSE_DOWN, this::onMouseEvent);
        addListener(InputEventType.MOUSE_UP, this::onMouseEvent);
        addListener(InputEventType.MOUSE_CLICK, this::onMouseEvent);
        addListener(InputEventType.MOUSE_MOVE, this::onMouseMove);
        addListener(InputEventType.MOUSE_SCROLL, this::onMouseEvent);
        addListener(InputEventType.KEY_DOWN, this::onKeyEvent);
        addListener(InputEventType.KEY_UP, this::onKeyEvent);
        addListener(InputEventType.KEY_PRESS, this::onKeyEvent);
    }

    @Override
    public void draw(Graphics2D g2d){
        for(GameObject object : gameObjects){
            if(!object.shouldBeRemoved() && ((UIComponent)object).isVisible()) object.draw(g2d, getCamera());
        }
    }

    private boolean onMouseEvent(MouseEvent e){
        Vector point = e.asVector();

        for(int i = gameObjects.size() - 1; i >= 0; i--){
            UIComponent component = (UIComponent)gameObjects.get(i);
            if(component.contains(point) && component.dispatchEvent(e)) return true;
        }

        return false;
    }

    private boolean onKeyEvent(KeyEvent e){
        for(int i = gameObjects.size() - 1; i >= 0; i--){
            UIComponent component = (UIComponent)gameObjects.get(i);
            if(component.dispatchEvent(e)) return true;
        }

        return false;
    }

    private boolean onMouseMove(MouseEvent e){
        Vector point = e.asVector();
        boolean consumed = false;

        for(int i = gameObjects.size() - 1; i >= 0; i--){
            UIComponent component = (UIComponent)gameObjects.get(i);

            if(!consumed && component.contains(point)){
                if(!component.isMouseOver()){
                    component.onMouseEnter(e);
                    component.setMouseOver(true);
                }
                if(component.dispatchEvent(e)) consumed = true;
            } else {
                if(component.isMouseOver()){
                    component.onMouseLeave(e);
                    component.setMouseOver(false);
                }
            }

        }

        return consumed;
    }

    protected void addUIComponent(UIComponent component){
        gameObjects.add(component);
        gameObjects.addAll(component.getComponents());
    }

    protected void addUIComponents(List<UIComponent> components){
        for(UIComponent component : components) addUIComponent(component);
    }

}
