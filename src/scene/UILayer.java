package scene;

import game.object.GameObject;
import graphic.Window;
import input.event.InputEventType;
import input.event.KeyEvent;
import input.event.MouseEvent;

import ui.components.UIComponent;
import util.Vector;

import java.awt.*;
import java.util.List;

public class UILayer extends Layer {

    public UILayer(){
        addListener(InputEventType.MOUSE_DOWN, this::onMouseDown);
        addListener(InputEventType.MOUSE_UP, this::onMouseUp);
        addListener(InputEventType.MOUSE_CLICK, this::onMouseClick);
        addListener(InputEventType.MOUSE_MOVE, this::onMouseMove);
        addListener(InputEventType.KEY_DOWN, this::onKeyDown);
        addListener(InputEventType.KEY_UP, this::onKeyUp);
        addListener(InputEventType.KEY_PRESS, this::onKeyPress);
    }

    @Override
    public void draw(Graphics2D g2d){
        for(GameObject object : gameObjects){
            if(!object.shouldBeRemoved() && ((UIComponent)object).isVisible()) object.draw(g2d, getCamera());
        }
    }

    private boolean onKeyDown(KeyEvent e){
        for(int i = gameObjects.size() - 1; i >= 0; i--){
            UIComponent component = (UIComponent)gameObjects.get(i);
            if(component.onKeyDown(e)) return true;
        }

        return false;
    }

    private boolean onKeyUp(KeyEvent e){
        for(int i = gameObjects.size() - 1; i >= 0; i--){
            UIComponent component = (UIComponent)gameObjects.get(i);
            if(component.onKeyUp(e)) return true;
        }

        return false;
    }

    private boolean onKeyPress(KeyEvent e){
        for(int i = gameObjects.size() - 1; i >= 0; i--){
            UIComponent component = (UIComponent)gameObjects.get(i);
            if(component.onKeyPress(e)) return true;
        }

        return false;
    }

    private boolean onMouseUp(MouseEvent e){
        Vector point = new Vector(e.getX(), e.getY() - Window.TITLEBAR_HEIGHT);

        for(int i = gameObjects.size() - 1; i >= 0; i--){
            UIComponent component = (UIComponent)gameObjects.get(i);

            if(component.contains(point) && component.onMouseUp(e)) return true;
        }

        return false;
    }

    private boolean onMouseDown(MouseEvent e){
        Vector point = new Vector(e.getX(), e.getY() - Window.TITLEBAR_HEIGHT);

        for(int i = gameObjects.size() - 1; i >= 0; i--){
            UIComponent component = (UIComponent)gameObjects.get(i);

            if(component.contains(point) && component.onMouseDown(e)) return true;
        }

        return false;
    }

    private boolean onMouseClick(MouseEvent e){
        Vector point = new Vector(e.getX(), e.getY() - Window.TITLEBAR_HEIGHT);

        for(int i = gameObjects.size() - 1; i >= 0; i--){
            UIComponent component = (UIComponent)gameObjects.get(i);

            if(component.contains(point) && component.onMouseClick(e)) return true;
        }

        return false;
    }

    private boolean onMouseMove(MouseEvent e){
        Vector point = new Vector(e.getX(), e.getY() - Window.TITLEBAR_HEIGHT);
        boolean consumed = false;

        for(int i = gameObjects.size() - 1; i >= 0; i--){
            UIComponent component = (UIComponent)gameObjects.get(i);

            if(!consumed && component.contains(point)){
                if(!component.isMouseOver()){
                    component.onMouseEnter(e);
                    component.setMouseOver(true);
                }
                if(component.onMouseMove(e)) consumed = true;
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
