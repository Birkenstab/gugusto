package game.object;

import util.Size;
import util.Vector;

import java.awt.*;

public class FpsCounter extends GameObject {
    private int frames;
    private long since;
    private double fps;

    public FpsCounter() {
        super(new Vector(10, 40), new Size(200, 10));
    }

    @Override
    public void draw(Graphics2D g2d, Vector pos, Size size) {
        frames++;

        if (frames > 35) {
            long time = System.currentTimeMillis();
            fps = (double)frames / (time - since) * 1000;
            since = time;
            frames = 0;
        }
        g2d.setColor(Color.BLACK);
        g2d.drawString("FPS: " + String.format("%2.3f",fps), (int)getX(), (int)getY());
    }

    @Override
    public void update(double delta) {

    }
}
