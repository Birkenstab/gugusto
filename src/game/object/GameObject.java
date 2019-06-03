package game.object;

import collision.BoundingBox;
import game.Game;
import util.Size;
import util.Vector;

import java.awt.*;

public abstract class GameObject {

    private boolean shouldRemove = false;
    protected BoundingBox boundingBox;

    public GameObject(Vector position, Size size){
        boundingBox = new BoundingBox(position, size);
    }

    protected abstract void draw(Graphics2D g2d, Vector position, Size size);

    public final void draw(Graphics2D g2d) {
        Vector position = Game.getInstance().getCamera().toScreenCoordinates(boundingBox.getPosition());
        Size size = Game.getInstance().getCamera().toScreenCoordinates(boundingBox.getSize());
        draw(g2d, position, size);
    }
    public abstract void update(double delta);

    public BoundingBox getBoundingBox(){
        return boundingBox;
    }

    public void remove(){
        shouldRemove = true;
    }

    public boolean shouldBeRemoved(){
        return shouldRemove;
    }

    public double getX(){
        return boundingBox.getPosition().getX();
    }

    public double getY(){
        return boundingBox.getPosition().getY();
    }

    public void collision(GameObject other) {}

    public double getWidth(){
        if(boundingBox.getType() == BoundingBox.Type.RECTANGLE){
            return boundingBox.getSize().getWidth();
        }

        return boundingBox.getRadius();
    }

    public double getHeight(){
        if(boundingBox.getType() == BoundingBox.Type.RECTANGLE){
            return boundingBox.getSize().getHeight();
        }

        return boundingBox.getRadius();
    }

}
