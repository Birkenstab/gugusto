package game.object.blocks;

import game.Camera;
import util.Vector;

import java.awt.*;

public class TextureBlock extends Block {

    private Image texture;

    public TextureBlock(BlockType type, Vector position, Image texture) {
        super(type, position);
        this.texture = texture;
    }

    @Override
    public void draw(Graphics2D g2d, Camera camera){
        super.draw(g2d, camera);
        g2d.drawImage(texture, getX(), getY(), getWidth(), getHeight(), null);
    }

}
