package ui.components;

import game.Camera;
import input.event.MouseEvent;
import util.Size;
import util.Vector;

import java.awt.*;

public class Panel extends UIComponent {

    private Color color;

    public Panel(Vector position, Size size) {
        super(position, size);
    }

    public Panel(Vector position, Size size, Color color) {
        super(position, size);
        this.color = color;
    }

    @Override
    public void draw(Graphics2D g2d, Camera camera){
        super.draw(g2d, camera);

        g2d.setColor(color);
        g2d.fillRect(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public boolean onMouseMove(MouseEvent e){
        return true;
    }

    @Override
    public boolean onMouseDown(MouseEvent e){
        return true;
    }

    @Override
    public boolean onMouseUp(MouseEvent e){
        return true;
    }

    @Override
    public boolean onMouseClick(MouseEvent e){
        return true;
    }

}
