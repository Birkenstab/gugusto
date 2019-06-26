package de.thu.gpro.gugusto.game.object.blocks;

import de.thu.gpro.gugusto.graphic.Texture;
import de.thu.gpro.gugusto.graphic.TextureLoader;
import de.thu.gpro.gugusto.util.Vector;

import java.awt.image.BufferedImage;

public class GoalBlock extends TextureBlock {

    public static final BufferedImage TEXTURE = TextureLoader.get(Texture.BLOCK_GOAL);

    public GoalBlock(Vector position) {
        super(BlockType.GOAL, position, TEXTURE, false);
    }

}
