package de.thu.gpro.gugusto.game.object.blocks;

import de.thu.gpro.gugusto.graphic.animation.SpriteAnimation;
import de.thu.gpro.gugusto.util.Size;
import de.thu.gpro.gugusto.util.Vector;
import de.thu.gpro.gugusto.graphic.SpriteSheet;
import de.thu.gpro.gugusto.graphic.Texture;
import de.thu.gpro.gugusto.graphic.TextureLoader;

import java.awt.image.BufferedImage;

public class Coin extends AnimationBlock {

    private static final BufferedImage[] ANIMATION_FRAMES = SpriteSheet.extract(TextureLoader.get(Texture.BLOCK_COIN), 16, 1, 128, 128);
    public static final BufferedImage TEXTURE = ANIMATION_FRAMES[0];

    public static SpriteAnimation createAnimation(){
        return new SpriteAnimation(ANIMATION_FRAMES, 1000);
    }

    public Coin(Vector position) {
        this(position, new Size(1,1));
    }

    public Coin(Vector position, Size size) {
        super(BlockType.COIN, position, createAnimation(), false, size);
    }

}
