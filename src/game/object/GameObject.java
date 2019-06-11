package game.object;

import collision.BoundingBox;
import game.Camera;
import game.Game;
import util.Size;
import util.Vector;

import java.awt.*;

public abstract class GameObject {

    private boolean shouldRemove = false;
    private BoundingBox scaledBoundingBox;
    protected BoundingBox boundingBox;

    public GameObject(Vector position, Size size){
        boundingBox = new BoundingBox(position, size);
        scaledBoundingBox = boundingBox;
    }

    public abstract void update(double delta);

    public void draw(Graphics2D g2d, Camera camera) {
        Vector position = camera.toScreenCoordinates(boundingBox.getPosition());
        Size size = camera.toScreenCoordinates(boundingBox.getSize());
        scaledBoundingBox = new BoundingBox(position, size);
    }


    public BoundingBox getBoundingBox(){
        return boundingBox;
    }

    public void remove(){
        shouldRemove = true;
    }

    public boolean shouldBeRemoved(){
        return shouldRemove;
    }

    // These methods should only be used in drawing stuff
    // ----------------------------------------------------------------------------------
    protected int getX(){
        return (int)scaledBoundingBox.getPosition().getX();
    }

    protected int getY(){
        return (int)scaledBoundingBox.getPosition().getY();
    }

    protected int getWidth(){
        return (int)scaledBoundingBox.getSize().getWidth();
    }

    protected int getHeight(){
        return (int)scaledBoundingBox.getSize().getHeight();
    }
    // ----------------------------------------------------------------------------------

    public void collision(GameObject other) {}

}
