package ui.components;

import game.Camera;
import util.Size;
import util.Vector;

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
    }

    @Override
    public void draw(Graphics2D g2d, Camera camera) {
        super.draw(g2d, camera);

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
