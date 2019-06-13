package game.object.blocks;

import graphic.SpriteSheet;
import graphic.animation.SpriteAnimation;
import util.Vector;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Coin extends AnimationBlock {

    public static BufferedImage texture = null;

    static {
        File file = new File(".\\Gugusto Graphics\\coin.png");

        try {
            texture = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static SpriteAnimation createAnimation(){
        BufferedImage[] frames = SpriteSheet.extract(texture, 16, 1, 128, 128);
        return new SpriteAnimation(frames, 1000);
    }

    public Coin(Vector position){
        super(BlockType.COIN, position, createAnimation(), false);
    }

}
