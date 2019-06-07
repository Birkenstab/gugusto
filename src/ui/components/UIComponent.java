package ui.components;

import collision.CollisionUtil;
import game.Camera;
import game.object.GameObject;
import input.event.KeyEvent;
import input.event.MouseEvent;
import util.Size;
import util.Vector;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class UIComponent extends GameObject {

    private List<UIComponent> components;
    private boolean mouseOver = false;
    private boolean visible = true;

    public UIComponent(Vector position, Size size) {
        super(position, size);
        components = new ArrayList<>();
    }

    @Override
    public void update(double delta) {}

    @Override
    public void draw(Graphics2D g2d, Camera camera) {
        super.draw(g2d, camera);
    }

    public void onMouseEnter(MouseEvent event){}

    public void onMouseLeave(MouseEvent event){ }

    public boolean onMouseMove(MouseEvent event){
        return false;
    }

    public boolean onMouseDown(MouseEvent event){
        return false;
    }

    public boolean onMouseUp(MouseEvent event){
        return false;
    }

    public boolean onMouseClick(MouseEvent event){
        return false;
    }

    public boolean onKeyDown(KeyEvent event){
        return false;
    }

    public boolean onKeyUp(KeyEvent event){
        return false;
    }

    public boolean onKeyPress(KeyEvent event){
        return false;
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
