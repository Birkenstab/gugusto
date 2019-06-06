package game.object;

import game.Camera;
import input.KeyState;
import util.Size;
import util.Vector;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Player extends DynamicGameObject {

    private static final Size size = new Size(1, 1);
    private static Image playerImage = null;

    static {
        File file = new File(".\\Gugusto Graphics\\char_tmp.png");

        try {
            playerImage = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Player(Vector position){
        super(position, size);
    }
    @Override
    public void update(double delta){
        super.update(delta);

        int step = 10;
        if (KeyState.isDown('w'))
            boundingBox.getPosition().add(new Vector(0, - delta * step));
        if (KeyState.isDown('s'))
            boundingBox.getPosition().add(new Vector(0, delta * step));
        if (KeyState.isDown('a'))
            boundingBox.getPosition().add(new Vector(- delta * step, 0));
        if (KeyState.isDown('d'))
            boundingBox.getPosition().add(new Vector(delta * step, 0));
        if (KeyState.isDown(32)) { // Space
            if (isOnGround())
                getVelocity().setY(-18);
        }
    }

    @Override
    public void draw(Graphics2D g2d, Camera camera, Vector pos, Size size) {
        //g2d.setColor(Color.BLACK);
        //g2d.fillRect((int)pos.getX(), (int)pos.getY(), (int)size.getWidth(), (int)size.getHeight());
        g2d.drawImage(playerImage, (int)pos.getX(), (int)pos.getY(), (int)size.getWidth(), (int)size.getHeight(), null);
    }

    @Override
    public void collision(GameObject other) {
        super.collision(other);
        if (other instanceof GoalBlock) {
            System.out.println("Jo du hast das Ziel erreicht!");
        }
    }
}
