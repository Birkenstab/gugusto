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

    protected boolean rounded = false;
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
            if(rounded){
                g2d.setColor(Color.BLACK);
                drawRoundedRect(g2d, new Vector(2, 2));
                g2d.setColor(color);
                drawRoundedRect(g2d, new Vector());
            } else {
                g2d.setColor(color);
                g2d.fillRect(getX(), getY(), getWidth(), getHeight());
            }
        }
    }

    private void drawRoundedRect(Graphics2D g2d, Vector offset){
        int bs = 10; // border radius size
        int bsh = bs / 2; // border radius half
        int ox = (int)offset.getX();
        int oy = (int)offset.getY();

        g2d.fillArc(getX() + ox, getY() + oy, bs, bs, 0, 360);
        g2d.fillArc(getX() + getWidth() - bs + ox, getY() + oy, bs, bs, 0, 360);
        g2d.fillArc(getX() + ox, getY() + getHeight() - bs + oy, bs, bs, 0, 360);
        g2d.fillArc(getX() + getWidth() - bs + ox, getY() + getHeight() - bs + oy, bs, bs, 0, 360);

        g2d.fillRect(getX() + bsh + ox, getY() + oy, getWidth() - bs, bs);
        g2d.fillRect(getX() + bsh + ox, getY() + getHeight() - bs + oy, getWidth() - bs, bs);
        g2d.fillRect(getX() + ox, getY() + bsh + oy, bs, getHeight() - bs);
        g2d.fillRect(getX() + getWidth() - bs + ox, getY() + bsh + oy, bs, getHeight() - bs);

        g2d.fillRect(getX() + bs + ox, getY() + bs + oy, getWidth() - bs * 2, getHeight() - bs * 2);
    }

    protected void setFilter(InputEventType type, boolean enable){
        hasNoListener.put(type, enable);
    }

    public void setRounded(boolean rounded){
        this.rounded = rounded;
    }

}
