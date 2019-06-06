package game.object;

import game.Camera;
import game.Game;
import util.Size;
import util.Vector;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Block extends StaticGameObject {

    private static Image blockImage = null;

    static {
        File file = new File(".\\Gugusto Graphics\\128x128 Spring\\Grass.png");

        try {
            blockImage = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Block(Vector position) {
        super(position, new Size(1,1));
    }

    @Override
    public void draw(Graphics2D g2d, Camera camera, Vector pos, Size size) {
        //g2d.setColor(Color.GREEN);
        //g2d.fillRect((int)pos.getX(), (int)pos.getY(), (int)size.getWidth(), (int)size.getHeight());
        g2d.drawImage(blockImage, (int)pos.getX(), (int)pos.getY(), (int)size.getWidth(), (int)size.getHeight(), null);
    }

    @Override
    public void update(double delta) {

    }
}
