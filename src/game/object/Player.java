package game.object;

import game.Game;
import input.KeyState;
import input.event.InputEventType;
import input.event.KeyEvent;
import util.Size;
import util.Vector;

import java.awt.*;

public class Player extends GameObject {

    private static final Size size = new Size(10, 10);

    public Player(Vector position){
        super(position, size);
    }
    @Override
    public void update(double delta){
        int step = 30;
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
    public void draw(Graphics2D g2d) {
        Vector pos = Game.getInstance().getCamera().toScreenCoordinates(boundingBox.getPosition());
        Size size = boundingBox.getSize();

        g2d.setColor(Color.BLACK);
        g2d.drawRect((int)pos.getX(), (int)pos.getY(), (int)size.getWidth(), (int)size.getHeight());
    }

}
