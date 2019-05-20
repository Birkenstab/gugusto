package game.object;

import collision.BoundingBox;
import game.Game;
import util.Vector;

import java.awt.*;

public class Circle extends BaseGameObject {

    protected Vector velocity;
    protected double speed;
    protected Color color;
    protected BoundingBox boundingBox;

    public Circle(int size, double speed){
        Vector position = new Vector(Math.random() * Game.WIDTH-size, Math.random() * Game.HEIGHT-size);
        boundingBox = new BoundingBox(position, size);
        velocity = new Vector(Math.random() * 2 - 1, Math.random() * 2 - 1);
        color = new Color((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255));
        this.speed = speed;
    }

    @Override
    public void draw(Graphics2D g2d) {
        Vector pos = boundingBox.getPosition();
        double size = boundingBox.getRadius();

        g2d.setColor(color);
        g2d.fillOval((int)(pos.getX() - size), (int)(pos.getY() - size), (int)size*2, (int)size*2);
        g2d.setColor(Color.BLACK);
        g2d.drawOval((int)(pos.getX() - size), (int)(pos.getY() - size), (int)size*2, (int)size*2);
        g2d.drawRect((int)pos.getX(), (int)pos.getY(), 1, 1);
    }

    void move(double delta){
        boundingBox.getPosition().add(velocity.clone().multiply(delta*speed));
    }

    @Override
    public void update(double delta) {
        move(delta);
        int collision = Game.getInstance().getCollisionSystem().isCollidingWithScreen(this);

        if(collision == -1) velocity.setX(velocity.getX() * -1);
        else if(collision == 1) velocity.setY(velocity.getY() * -1);
    }

    @Override
    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

}
