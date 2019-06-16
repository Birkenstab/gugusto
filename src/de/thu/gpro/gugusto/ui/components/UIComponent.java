package de.thu.gpro.gugusto.ui.components;

import de.thu.gpro.gugusto.collision.BoundingBox;
import de.thu.gpro.gugusto.collision.CollisionUtil;
import de.thu.gpro.gugusto.game.Camera;
import de.thu.gpro.gugusto.game.object.GameObject;
import de.thu.gpro.gugusto.input.event.EventCallback;
import de.thu.gpro.gugusto.input.event.InputEvent;
import de.thu.gpro.gugusto.input.event.InputEventType;
import de.thu.gpro.gugusto.input.event.MouseEvent;
import de.thu.gpro.gugusto.util.Size;
import de.thu.gpro.gugusto.util.Vector;
import de.thu.gpro.gugusto.input.event.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public abstract class UIComponent extends GameObject {

    private List<List<EventCallback<InputEvent>>> listeners;
    protected boolean visible = true;
    private boolean mouseOver = false;

    protected Rectangle2D clipArea;
    protected BoundingBox clipBoundingBox;

    protected List<UIComponent> components;

    public UIComponent(Vector position, Size size) {
        super(position, size);
        components = new ArrayList<>();
        listeners = new ArrayList<>();
        for(int i = 0; i < InputEventType.values().length; i++) listeners.add(new ArrayList<>());
    }

    @Override
    public void update(double delta) {}

    public void draw(Graphics2D g2d) {}

    @Override
    public final void draw(Graphics2D g2d, Camera camera) {
        super.draw(g2d, camera);

        if(clipArea != null) g2d.setClip(clipArea);
        draw(g2d);
        g2d.setClip(null);
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
        if(visible){
            if(clipArea != null && !CollisionUtil.contains(point, clipBoundingBox)) return false;
            return CollisionUtil.contains(point, boundingBox);
        }

        return false;
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
        List<UIComponent> comps = new ArrayList<>(components);
        for(UIComponent component : components) comps.addAll(component.getComponents());
        return comps;
    }

    public void setPosition(Vector position){
        Vector offset = position.clone().subtract(boundingBox.getPosition());
        boundingBox.getPosition().set(position);

        for(UIComponent component : components) component.getBoundingBox().getPosition().add(offset);
    }

}
