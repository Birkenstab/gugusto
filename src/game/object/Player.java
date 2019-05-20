package game.object;

import game.Game;
import input.event.InputEventType;
import input.event.KeyEvent;
import util.Size;
import util.Vector;

import java.awt.*;

public class Player extends GameObject {

    private static final Size size = new Size(10, 10);

    public Player(Vector position){
        super(position, size);

        Game.getInstance().getInputSystem().addListener(InputEventType.KEY_DOWN, this::onKeyDown);
    }

    private void onKeyDown(KeyEvent e){
        int step = 5;
        if(e.getChar() == 'w') boundingBox.getPosition().add(new Vector(0, -step));
        else if(e.getChar() == 's') boundingBox.getPosition().add(new Vector(0, step));
        else if(e.getChar() == 'a') boundingBox.getPosition().add(new Vector(-step, 0));
        else if(e.getChar() == 'd') boundingBox.getPosition().add(new Vector(step, 0));
    }

    @Override
    public void update(double delta){

    }

    @Override
    public void draw(Graphics2D g2d) {
        Vector pos = Game.getInstance().getCamera().toScreenCoordinates(boundingBox.getPosition());
        Size size = boundingBox.getSize();

        g2d.setColor(Color.BLACK);
        g2d.drawRect((int)pos.getX(), (int)pos.getY(), (int)size.getWidth(), (int)size.getHeight());
    }

}
