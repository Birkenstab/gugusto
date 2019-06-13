package ui.components;

import game.Camera;
import input.event.InputEventType;
import input.event.MouseEvent;
import util.Size;
import util.Vector;

import java.awt.*;

public class Panel extends Container {

    private Color color;

    public Panel(Vector position, Size size) {
        this(position, size, Color.GRAY);
    }

    public Panel(Vector position, Size size, Color color) {
        super(position, size);
        this.color = color;

        addListener(InputEventType.MOUSE_MOVE, e -> true);
        addListener(InputEventType.MOUSE_DOWN, e -> true);
        addListener(InputEventType.MOUSE_UP, e -> true);
        addListener(InputEventType.MOUSE_CLICK, e -> true);
    }

    @Override
    public void draw(Graphics2D g2d, Camera camera){
        super.draw(g2d, camera);

        g2d.setColor(color);
        g2d.fillRect(getX(), getY(), getWidth(), getHeight());
    }

}
