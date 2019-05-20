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

    public abstract void draw(Graphics2D g2d);
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

}