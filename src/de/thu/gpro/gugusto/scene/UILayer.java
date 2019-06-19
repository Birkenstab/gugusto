package de.thu.gpro.gugusto.scene;

import de.thu.gpro.gugusto.input.event.*;
import de.thu.gpro.gugusto.game.object.GameObject;
import de.thu.gpro.gugusto.input.event.*;

import de.thu.gpro.gugusto.ui.components.UIComponent;
import de.thu.gpro.gugusto.util.Vector;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UILayer extends Layer {

    public UILayer(){
        super.addListener(InputEventType.MOUSE_DOWN, this::onMouseDown);
        super.addListener(InputEventType.MOUSE_UP, this::onMouseEvent);
        super.addListener(InputEventType.MOUSE_CLICK, this::onMouseEvent);
        super.addListener(InputEventType.MOUSE_MOVE, this::onMouseMove);
        super.addListener(InputEventType.MOUSE_SCROLL, this::onMouseEvent);
        super.addListener(InputEventType.KEY_DOWN, this::onKeyEvent);
        super.addListener(InputEventType.KEY_UP, this::onKeyEvent);
        super.addListener(InputEventType.KEY_PRESS, this::onKeyEvent);
    }

    @Override
    protected <T extends InputEvent> void addListener(InputEventType type, EventCallback<T> callback){
        throw new Error("You are not allowed to add a listener to a UILayer. Please move the listener inside your component.");
    }

    @Override
    public void draw(Graphics2D g2d){
        for(GameObject object : gameObjects){
            if(!object.shouldBeRemoved() && ((UIComponent)object).isVisible()) object.draw(g2d, getCamera());
        }
    }

    private boolean onKeyEvent(KeyEvent e){
        for(int i = gameObjects.size() - 1; i >= 0; i--){
            UIComponent component = (UIComponent)gameObjects.get(i);
            if(component.dispatchEvent(e)) return true;
        }

        return false;
    }

    private boolean onMouseEvent(MouseEvent e){
        Vector point = e.asVector();

        for(int i = gameObjects.size() - 1; i >= 0; i--){
            UIComponent component = (UIComponent)gameObjects.get(i);
            if(component.contains(point) && component.dispatchEvent(e)) return true;
        }

        return false;
    }

    private boolean onMouseDown(MouseEvent e){
        Vector point = e.asVector();
        boolean consumed = false;

        for(int i = gameObjects.size() - 1; i >= 0; i--){
            UIComponent component = (UIComponent)gameObjects.get(i);

            if(!consumed && component.contains(point)){
                component.setActive(true);
                if(component.dispatchEvent(e)) consumed = true;
            } else {
                component.setActive(false);
            }
        }

        return consumed;
    }

    private boolean onMouseMove(MouseEvent e){
        Vector point = e.asVector();
        boolean consumed = false;

        for(int i = gameObjects.size() - 1; i >= 0; i--){
            UIComponent component = (UIComponent)gameObjects.get(i);

            if(!consumed && component.contains(point)){
                if(!component.isMouseOver()){
                    component.setMouseOver(true);
                    component.onMouseEnter(e);
                }
                if(component.dispatchEvent(e)) consumed = true;
            } else {
                if(component.isMouseOver()){
                    component.setMouseOver(false);
                    component.onMouseLeave(e);
                }
            }
        }

        return consumed;
    }

    protected void clear(){
        gameObjects = new ArrayList<>();
    }

    protected void addUIComponent(UIComponent component){
        gameObjects.add(component);
        gameObjects.addAll(component.getComponents());
    }

    protected void addUIComponents(List<UIComponent> components){
        for(UIComponent component : components) addUIComponent(component);
    }

}
