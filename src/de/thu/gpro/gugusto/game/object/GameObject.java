package de.thu.gpro.gugusto.game.object;

import de.thu.gpro.gugusto.collision.BoundingBox;
import de.thu.gpro.gugusto.util.Vector;
import de.thu.gpro.gugusto.game.Camera;
import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.util.Size;

import java.awt.*;

public abstract class GameObject {

    public enum Type { Block, Enemy }

    private static final int insetX = (int)Game.WINDOW.getTopLeftInsets().getX();
    private static final int insetY = (int)Game.WINDOW.getTopLeftInsets().getY();

    private boolean shouldRemove = false;
    private BoundingBox scaledDrawBoundingBox;
    protected BoundingBox boundingBox;
    protected BoundingBox drawBoundingBox;
    private boolean solid;

    public GameObject(Vector position, Size size){
        boundingBox = new BoundingBox(position, size);
        drawBoundingBox = new BoundingBox(new Vector(0, 0), size);
        scaledDrawBoundingBox = boundingBox;
    }

    public abstract void update(double delta);

    public void draw(Graphics2D g2d, Camera camera) {
        Vector position = camera.toScreenCoordinates(boundingBox.getPosition().clone().add(drawBoundingBox.getPosition()));
        Size size = camera.toScreenCoordinates(drawBoundingBox.getSize());
        scaledDrawBoundingBox = new BoundingBox(position, size);
    }


    public BoundingBox getBoundingBox(){
        return boundingBox;
    }

    public BoundingBox getDrawBoundingBox() {
        return drawBoundingBox;
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
        return (int)(scaledDrawBoundingBox.getPosition().getX() + insetX);
    }

    protected int getY(){
        return (int)(scaledDrawBoundingBox.getPosition().getY() + insetY);
    }

    protected int getWidth(){
        return (int)scaledDrawBoundingBox.getSize().getWidth();
    }

    protected int getHeight(){
        return (int)scaledDrawBoundingBox.getSize().getHeight();
    }
    // ----------------------------------------------------------------------------------

    public void collision(GameObject other){}

    public boolean isSolid() {
        return solid;
    }

    protected void setSolid(boolean solid) {
        this.solid = solid;
    }
}
