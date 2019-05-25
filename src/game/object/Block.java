package game.object;

import game.Game;
import util.Size;
import util.Vector;

import java.awt.*;

public class Block extends GameObject {
    public static final int SIZE = 16;

    public Block(Vector position) {
        super(position, new Size(SIZE,SIZE));
    }

    @Override
    public void draw(Graphics2D g2d) {
        Vector pos = Game.getInstance().getCamera().toScreenCoordinates(boundingBox.getPosition());

        g2d.setColor(Color.GREEN);
        g2d.fillRect((int)pos.getX(), (int)pos.getY(), (int)getWidth(), (int)getHeight());
    }

    @Override
    public void update(double delta) {

    }
}
