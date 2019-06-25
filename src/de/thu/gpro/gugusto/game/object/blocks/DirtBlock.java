package de.thu.gpro.gugusto.game.object.blocks;

import de.thu.gpro.gugusto.graphic.Texture;
import de.thu.gpro.gugusto.graphic.TextureLoader;
import de.thu.gpro.gugusto.util.Vector;

import java.awt.image.BufferedImage;

public class DirtBlock extends TextureBlock {

    public static final BufferedImage TEXTURE = TextureLoader.get(Texture.BLOCK_DIRT);

    public DirtBlock(Vector position) {
        super(BlockType.DIRT, position, TEXTURE);
    }

}
