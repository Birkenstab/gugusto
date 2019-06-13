package game.object.blocks;

import game.Camera;
import util.Vector;

import java.awt.*;

public abstract class TextureBlock extends Block {

    private Image texture;

    TextureBlock(BlockType type, Vector position, Image texture) {
        this(type, position, texture, true);
    }

    TextureBlock(BlockType type, Vector position, Image texture, boolean isSolid) {
        super(type, position, isSolid);
        this.texture = texture;
    }

    @Override
    public void draw(Graphics2D g2d, Camera camera){
        super.draw(g2d, camera);
        g2d.drawImage(texture, getX(), getY(), getWidth(), getHeight(), null);
    }

}
