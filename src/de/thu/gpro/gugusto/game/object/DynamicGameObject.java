package de.thu.gpro.gugusto.game.object;

import de.thu.gpro.gugusto.collision.BoundingBox;
import de.thu.gpro.gugusto.util.Vector;
import de.thu.gpro.gugusto.util.Size;

import java.util.stream.DoubleStream;

public abstract class DynamicGameObject extends GameObject {
    public static final double GRAVITY = 30;

    private Vector velocity = new Vector();
    private boolean onGround = false;
    private boolean movable = true;
    private boolean gravity = true;

    public DynamicGameObject(Vector position, Size size) {
        super(position, size);
    }

    @Override
    public void update(double delta) {
        if (gravity) {
            if (velocity.getY() < 30) {
                velocity.add(new Vector(0, GRAVITY * delta));
            }
        }
        boundingBox.getPosition().add(velocity.clone().multiply(delta));
    }

    @Override
    public void collision(GameObject other) {
        if (other.isSolid() && movable) {
            Vector pos1 = boundingBox.getPosition();
            Vector pos2 = other.getBoundingBox().getPosition();
            Size size1 = boundingBox.getSize();
            Size size2 = other.getBoundingBox().getSize();

            if (boundingBox.getType() != BoundingBox.Type.RECTANGLE || other.getBoundingBox().getType() != BoundingBox.Type.RECTANGLE)
                throw new Error("Not implemented"); // Todo

            double deltaLeft = pos1.getX() + size1.getWidth() - pos2.getX();
            double deltaTop = pos1.getY() + size1.getHeight() - pos2.getY();
            double deltaRight = pos2.getX() + size2.getWidth() - pos1.getX();
            double deltaBottom = pos2.getY() + size2.getHeight() - pos1.getY();
            
            // TODO evtl. wenn schon weggesputh (min kleiner als 0, dann nichts tun)


            double min = DoubleStream.of(deltaLeft, deltaTop, deltaRight, deltaBottom).min().getAsDouble();

            if (min == deltaLeft) { // Nach links vom Hindernis pushen
                boundingBox.getPosition().setX(pos2.getX() - size1.getWidth());
                if (velocity.getX() > 0)
                    velocity.setX(0);
            } else if (min == deltaRight) { // Nach rechts vom Hindernis pushen
                boundingBox.getPosition().setX(pos2.getX() + size2.getWidth());
                if (velocity.getX() < 0)
                    velocity.setX(0);
            } else if (min == deltaTop) { // Nach oben vom Hindernis pushen
                if (velocity.getY() >= 0)
                    onGround = true;
                boundingBox.getPosition().setY(pos2.getY() - size1.getHeight());
                if (velocity.getY() > 0)
                    velocity.setY(0);
            } else if (min == deltaBottom) { // Nach unten vom Hindernis pushen
                boundingBox.getPosition().setY(pos2.getY() + size2.getHeight());
                if (velocity.getY() < 0)
                    velocity.setY(0);
            }
        }
    }

    public final void kill() {
        kill(null);
    }

    public void kill(GameObject by) {
        remove();
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

    public void setGravity(boolean gravity) {
        this.gravity = gravity;
    }
}
