package game.object;

import game.Camera;
import game.object.blocks.GoalBlock;
import graphic.Animation;
import input.KeyState;
import util.Size;
import util.Vector;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import graphic.Animation;

public class Player extends DynamicGameObject {

    private static final Size size = new Size(1, 1);
    private static Image playerImage = null;
    private static Animation run = new Animation();

    static {
        File file = new File("./Gugusto Graphics/char_tmp.png");

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

        int step = 5;
        if (KeyState.isDown('w'))
        { boundingBox.getPosition().add(new Vector(0, - delta * step)); animation('w');}
        if (KeyState.isDown('s'))
        { boundingBox.getPosition().add(new Vector(0, delta * step));  animation('s');}
        if (KeyState.isDown('a'))
        { boundingBox.getPosition().add(new Vector(- delta * step, 0));  animation('a');}
        if (KeyState.isDown('d'))
        {  boundingBox.getPosition().add(new Vector(delta * step, 0));  animation('d');}
        if (KeyState.isDown(32)) { // Space
            if (isOnGround()) getVelocity().setY(-18);
        }
    }

    private void animation(char key){
        try {
            playerImage = ImageIO.read( run.update(key));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g2d, Camera camera) {
        super.draw(g2d, camera);
        g2d.drawImage(playerImage, getX(), getY(), getWidth(), getHeight(), null);
    }

    @Override
    public void collision(GameObject other) {
        super.collision(other);
        if (other instanceof GoalBlock) {
            System.out.println("Jo du hast das Ziel erreicht!");
        }
    }

    public void reset(){
        setOnGround(true);
        setVelocity(new Vector());
    }

}
