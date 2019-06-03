package game.object;

import input.KeyState;
import util.Size;
import util.Vector;

import java.awt.*;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class Player extends GameObject {

    private static final Size size = new Size(1, 1);

    public Player(Vector position){
        super(position, size);
    }
    @Override
    public void update(double delta){
        int step = 10;
        if (KeyState.isDown('w'))
            boundingBox.getPosition().add(new Vector(0, - delta * step));
        if (KeyState.isDown('s'))
            boundingBox.getPosition().add(new Vector(0, delta * step));
        if (KeyState.isDown('a'))
            boundingBox.getPosition().add(new Vector(- delta * step, 0));
        if (KeyState.isDown('d'))
            boundingBox.getPosition().add(new Vector(delta * step, 0));
    }

    @Override
    public void draw(Graphics2D g2d, Vector pos, Size size) {
        g2d.setColor(Color.BLACK);
        g2d.fillRect((int)pos.getX(), (int)pos.getY(), (int)size.getWidth(), (int)size.getHeight());
    }

    @Override
    public void collision(GameObject other) {
        super.collision(other);
        Vector pos1 = boundingBox.getPosition();
        Vector pos2 = other.getBoundingBox().getPosition();
        Size size1 = boundingBox.getSize();
        Size size2 = other.getBoundingBox().getSize();

        Vector center1 = pos1.clone().add(size1.toVector().multiply(0.5));
        Vector center2 = pos2.clone().add(size2.toVector().multiply(0.5));

        double deltaLeft = center2.getX() - center1.getX();
        double deltaTop = center2.getY() - center1.getY();

        double deltaRight = -deltaLeft;
        double deltaBottom = -deltaTop;

        double max = DoubleStream.of(deltaLeft, deltaTop, deltaRight, deltaBottom).max().getAsDouble();

        if (max == deltaLeft) {
            boundingBox.getPosition().setX(pos2.getX() - size1.getWidth());
        } else if (max == deltaRight) {
            boundingBox.getPosition().setX(pos2.getX() + size2.getWidth());
        } else if (max == deltaTop) {
            boundingBox.getPosition().setY(pos2.getY() - size1.getHeight());
        } else if (max == deltaBottom) {
            boundingBox.getPosition().setY(pos2.getY() + size2.getHeight());
        }
    }
}
