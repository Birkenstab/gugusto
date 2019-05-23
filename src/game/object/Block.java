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
        Size size = boundingBox.getSize();

        g2d.setColor(Color.GREEN);
        g2d.fillRect((int)pos.getX(), (int)pos.getY(), (int)size.getWidth(), (int)size.getHeight());
    }

    @Override
    public void update(double delta) {

    }
}
