package de.thu.gpro.gugusto.ui.components;

import de.thu.gpro.gugusto.input.event.EventCallback;
import de.thu.gpro.gugusto.input.event.InputEvent;
import de.thu.gpro.gugusto.input.event.InputEventType;
import de.thu.gpro.gugusto.input.event.MouseEvent;
import de.thu.gpro.gugusto.util.Size;
import de.thu.gpro.gugusto.util.Vector;

import java.awt.*;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class Panel extends UIComponent {

    private Map<InputEventType, Boolean> hasNoListener;

    protected Color color;

    public Panel(Vector position, Size size) {
        this(position, size, null);
    }

    public Panel(Vector position, Size size, Color color) {
        super(position, size);
        this.color = color;

        hasNoListener = new HashMap<>();
        for(InputEventType type : EnumSet.allOf(InputEventType.class)){
            if(type.isMouseEvent()){
                hasNoListener.put(type, true);
                super.addListener(type, e -> preventMouseDefault((MouseEvent)e));
            }
        }
    }

    private boolean preventMouseDefault(MouseEvent event){
        if(event.getType() == InputEventType.MOUSE_DOWN || event.getType() == InputEventType.MOUSE_UP){
            if(event.getButton() == MouseEvent.BUTTON2) return false;
        }

        return hasNoListener.get(event.getType());
    }

    @Override
    protected <T extends InputEvent> void addListener(InputEventType type, EventCallback<T> callback){
        super.addListener(type, callback);
        hasNoListener.put(type, false);
    }

    @Override
    public void draw(Graphics2D g2d){
        if(color != null){
            g2d.setColor(color);
            g2d.fillRect(getX(), getY(), getWidth(), getHeight());
        }
    }

    protected void setFilter(InputEventType type, boolean enable){
        hasNoListener.put(type, enable);
    }

}
