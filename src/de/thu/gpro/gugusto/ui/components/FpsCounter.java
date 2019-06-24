package de.thu.gpro.gugusto.ui.components;

import de.thu.gpro.gugusto.input.event.EventCallback;
import de.thu.gpro.gugusto.input.event.InputEventType;
import de.thu.gpro.gugusto.input.event.KeyEvent;
import de.thu.gpro.gugusto.util.Size;
import de.thu.gpro.gugusto.util.Vector;

import java.awt.*;

public class FpsCounter extends UIComponent {

    private static final Font font = new Font("Dialog", Font.PLAIN, 12);
    public static final int WIDTH = 64;
    public static final int HEIGHT = 16;

    private int frames;
    private long since;
    private double fps = 0;

    public FpsCounter(Vector position) {
        super(position, new Size(WIDTH, HEIGHT));
        since = System.currentTimeMillis();

        addListener(InputEventType.KEY_DOWN, (EventCallback<KeyEvent>) e -> {
            if(e.getChar() == 'p'){
                setVisible(!isVisible());
                return true;
            }
            return false;
        });

        setVisible(false);
    }

    @Override
    public void draw(Graphics2D g2d){
        frames++;

        long current = System.currentTimeMillis();
        if(current - since >= 1000){
            fps = frames;
            since = current;
            frames = 0;
        }

        g2d.setColor(Color.BLACK);
        g2d.fillRect(getX(), getY(), getWidth(), getHeight());

        g2d.setFont(font);
        g2d.setColor(Color.WHITE);
        g2d.drawString("FPS: " + String.format("%2.2f",fps), getX() + 2, getY() + 12);
    }

}
