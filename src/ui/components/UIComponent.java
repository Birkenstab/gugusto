package ui.components;

import collision.CollisionUtil;
import game.Camera;
import game.object.GameObject;
import input.InputSystem;
import input.event.*;
import util.Size;
import util.Vector;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class UIComponent extends GameObject {

    private List<List<EventCallback<InputEvent>>> listeners;
    private boolean visible = true;
    private boolean mouseOver = false;

    protected List<UIComponent> components;

    public UIComponent(Vector position, Size size) {
        super(position, size);
        components = new ArrayList<>();
        listeners = new ArrayList<>();
        for(int i = 0; i < InputEventType.values().length; i++) listeners.add(new ArrayList<>());
    }

    @Override
    public void update(double delta) {}

    @Override
    public void draw(Graphics2D g2d, Camera camera) {
        super.draw(g2d, camera);
    }

    public void onMouseEnter(MouseEvent e){}
    public void onMouseLeave(MouseEvent e){}

    protected <T extends InputEvent> void addListener(InputEventType type, EventCallback<T> callback){
        List<EventCallback<InputEvent>> list = listeners.get(type.getId());
        if(list != null) list.add((EventCallback<InputEvent>)callback);
    }

    public boolean dispatchEvent(InputEvent event){
        List<EventCallback<InputEvent>> callbacks = listeners.get(event.getType().getId());
        boolean consumed = false;

        for(EventCallback<InputEvent> callback : callbacks){
            if(callback.callback(event)) consumed = true;
        }

        return consumed;
    }

    public boolean contains(Vector point){
        return visible && CollisionUtil.contains(point, boundingBox);
    }

    public void setVisible(boolean visible){
        this.visible = visible;
        for(UIComponent component : components) component.setVisible(visible);
    }

    public boolean isVisible(){
        return visible;
    }

    public void setMouseOver(boolean mouseOver){
        this.mouseOver = mouseOver;
    }

    public boolean isMouseOver(){
        return mouseOver;
    }

    public void addUIComponent(UIComponent component){
        components.add(component);
    }
    public void addUIComponent(List<UIComponent> components){
        this.components.addAll(components);
    }

    public List<UIComponent> getComponents(){
        return components;
    }

}
