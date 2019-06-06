package ui;

import game.Camera;
import util.Size;
import util.Vector;

import java.awt.*;

public class Button extends UIComponent {

    private boolean hover = false;

    public Button(Vector position, Size size) {
        super(position, size);
    }

    @Override
    protected void draw(Graphics2D g2d, Camera camera, Vector position, Size size) {
        if(hover) g2d.setColor(Color.YELLOW);
        else g2d.setColor(Color.RED);
        g2d.fillRect((int)position.getX(), (int)position.getY(), (int)size.getWidth(), (int)size.getHeight());
    }

    @Override
    public void update(double delta) {

    }

    public void setHover(boolean hover){
        this.hover = hover;
    }

}
