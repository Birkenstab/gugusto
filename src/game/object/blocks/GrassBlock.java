package game.object.blocks;

import graphic.Texture;
import graphic.TextureLoader;
import util.Vector;

import javax.imageio.ImageIO;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GrassBlock extends TextureBlock {

    private static final BufferedImage texture = TextureLoader.get(Texture.BLOCK_GRASS);

    public GrassBlock(Vector position) {
        super(BlockType.GRASS, position, texture);
    }

}
