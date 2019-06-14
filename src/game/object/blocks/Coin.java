package game.object.blocks;

import graphic.SpriteSheet;
import graphic.Texture;
import graphic.TextureLoader;
import graphic.animation.SpriteAnimation;
import util.Vector;

import java.awt.image.BufferedImage;

public class Coin extends AnimationBlock {

    private static final BufferedImage texture = TextureLoader.get(Texture.BLOCK_COIN);

    private static SpriteAnimation createAnimation(){
        BufferedImage[] frames = SpriteSheet.extract(texture, 16, 1, 128, 128);
        return new SpriteAnimation(frames, 1000);
    }

    public Coin(Vector position){
        super(BlockType.COIN, position, createAnimation(), false);
    }

}
