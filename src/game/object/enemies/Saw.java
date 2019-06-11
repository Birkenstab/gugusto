package game.object.enemies;

import game.Camera;
import graphic.animation.RotationAnimation;
import util.Size;
import util.Vector;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Saw extends Enemy {

    private static final int ROTATION_SPEED = 2000;
    private static BufferedImage texture = null;

    private RotationAnimation animation;

    static {
        File file = new File(".\\Gugusto Graphics\\Enemies\\Saw Enemie.png");

        try {
            texture = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Saw(Vector position){
        this(position, 2);
    }

    public Saw(Vector position, int size) {
        super(position, new Size(size, size));
        setMovable(false);
        animation = new RotationAnimation(texture, ROTATION_SPEED, RotationAnimation.Anchor.CENTER);
        animation.start();
    }

    @Override
    public void draw(Graphics2D g2d, Camera camera){
        super.draw(g2d, camera);
        animation.draw(g2d, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void update(double delta) {
        super.update(delta);
        animation.update(delta);
    }

}
