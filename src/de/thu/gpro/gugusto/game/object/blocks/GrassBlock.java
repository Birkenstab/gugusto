package de.thu.gpro.gugusto.game.object.blocks;

import de.thu.gpro.gugusto.graphic.Texture;
import de.thu.gpro.gugusto.graphic.TextureLoader;
import de.thu.gpro.gugusto.util.Vector;

import java.awt.image.BufferedImage;

public class GrassBlock extends TextureBlock {

    private static final BufferedImage texture = TextureLoader.get(Texture.BLOCK_GRASS);

    public GrassBlock(Vector position) {
        super(BlockType.GRASS, position, texture);
    }

}
