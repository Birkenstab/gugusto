package de.thu.gpro.gugusto.game.object.blocks;

import de.thu.gpro.gugusto.graphic.animation.SpriteAnimation;
import de.thu.gpro.gugusto.util.Vector;
import de.thu.gpro.gugusto.graphic.SpriteSheet;
import de.thu.gpro.gugusto.graphic.Texture;
import de.thu.gpro.gugusto.graphic.TextureLoader;

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
