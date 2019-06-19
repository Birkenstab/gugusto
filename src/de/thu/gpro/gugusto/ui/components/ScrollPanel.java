package de.thu.gpro.gugusto.ui.components;

import de.thu.gpro.gugusto.collision.BoundingBox;
import de.thu.gpro.gugusto.input.event.InputEventType;
import de.thu.gpro.gugusto.input.event.MouseEvent;
import de.thu.gpro.gugusto.util.Size;
import de.thu.gpro.gugusto.util.Vector;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ScrollPanel extends Panel {

    private int scrollY = 0;
    private double scrollModifier;
    private Map<UIComponent, Vector> componentOffset;

    protected int maxScrollY;

    public ScrollPanel(Vector position, Size size){
        this(position, size, 1);
    }

    public ScrollPanel(Vector position, Size size, double scrollModifier){
        super(position, size, Color.BLACK);

        this.scrollModifier = scrollModifier;
        componentOffset = new HashMap<>();
        addListener(InputEventType.MOUSE_SCROLL, this::onScroll);
    }

    private boolean onScroll(MouseEvent e){
        if(maxScrollY < 1){
            if(scrollY != 0){
                scrollY = 0;
                updateChildPositions();
            }
            return false;
        }
        scroll(e.getUnitsToScroll());
        return true;
    }

    public void build(){
        int contentHeight = 0;

        for(UIComponent component : components){
            componentOffset.put(component, component.getBoundingBox().getPosition().clone().subtract(boundingBox.getPosition()));
            contentHeight += component.getBoundingBox().getSize().getHeight();
        }

        maxScrollY = contentHeight - (int)boundingBox.getSize().getHeight();
        setClipArea(new Rectangle(), boundingBox);
    }

    private void scroll(int units){
        units *= scrollModifier;
        int tmp = scrollY + units;

        if(tmp < 0) units = tmp != units ? -scrollY : 0;
        else if(tmp > maxScrollY) units = tmp != maxScrollY + units ? maxScrollY - scrollY : 0;

        scrollY += units;
        updateChildPositions();
    }

    private void updateChildPositions(){
        Vector scrollVector = new Vector(0, scrollY);

        for(UIComponent component : components){
            Vector pos = boundingBox.getPosition().clone().add(componentOffset.get(component)).subtract(scrollVector);
            component.setPosition(pos);
        }
    }

    @Override
    public void update(double delta){
        super.update(delta);

        clipArea.setRect(getX(), getY(), getWidth(), getHeight());
    }

}
