package game.object.blocks;

import util.Vector;

import javax.imageio.ImageIO;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GrassBlock extends TextureBlock {

    public static BufferedImage texture = null;

    static {
        File file = new File(".\\Gugusto Graphics\\128x128 Spring\\Grass.png");

        try {
            texture = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GrassBlock(Vector position) {
        super(BlockType.GRASS, position, texture);
    }

}
