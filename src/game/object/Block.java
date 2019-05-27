package game.object;

import game.Game;
import util.Size;
import util.Vector;

import java.awt.*;

public class Block extends StaticGameObject {

    public Block(Vector position) {
        super(position, new Size(1,1));
    }

    @Override
    public void draw(Graphics2D g2d, Vector pos, Size size) {
        g2d.setColor(Color.GREEN);
        g2d.fillRect((int)pos.getX(), (int)pos.getY(), (int)size.getWidth(), (int)size.getHeight());
    }

    @Override
    public void update(double delta) {

    }
}
