package game.object;

import game.object.blocks.Block;
import util.Size;
import util.Vector;

import java.util.stream.DoubleStream;

public abstract class DynamicGameObject extends GameObject {
    public static final double GRAVITY = 30;

    private Vector velocity = new Vector();
    private boolean onGround = false;
    private boolean movable = true;

    public DynamicGameObject(Vector position, Size size) {
        super(position, size);
    }

    @Override
    public void update(double delta) {
        velocity.add(new Vector(0, GRAVITY * delta));
        boundingBox.getPosition().add(velocity.clone().multiply(delta));
    }

    @Override
    public void collision(GameObject other) {
        if (other instanceof Block && ((Block) other).isSolid() && movable) {
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
                if (velocity.getX() > 0)
                    velocity.setX(0);
            } else if (max == deltaRight) {
                boundingBox.getPosition().setX(pos2.getX() + size2.getWidth());
                if (velocity.getX() < 0)
                    velocity.setX(0);
            } else if (max == deltaTop) {
                onGround = true;
                boundingBox.getPosition().setY(pos2.getY() - size1.getHeight());
                if (velocity.getY() > 0)
                    velocity.setY(0);
            } else if (max == deltaBottom) {
                boundingBox.getPosition().setY(pos2.getY() + size2.getHeight());
                if (velocity.getY() < 0)
                    velocity.setY(0);
            }
        }
    }

    public Vector getVelocity() {
        return velocity;
    }
    protected void setVelocity(Vector velocity){
        this.velocity = velocity;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public void setMovable(boolean movable){
        this.movable = movable;
    }

}
