package game.object.blocks;

import util.Vector;

import javax.imageio.ImageIO;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GrassBlock extends TextureBlock {

    private static Image grassTexture = null;

    static {
        File file = new File("./Gugusto Graphics/128x128 Spring/Grass.png");

        try {
            grassTexture = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GrassBlock(Vector position) {
        super(BlockType.GRASS, position, grassTexture);
    }

}
