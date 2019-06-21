package de.thu.gpro.gugusto.game.object.blocks;

import de.thu.gpro.gugusto.game.object.DynamicGameObject;
import de.thu.gpro.gugusto.game.object.GameObject;
import de.thu.gpro.gugusto.game.object.player.Player;
import de.thu.gpro.gugusto.graphic.Texture;
import de.thu.gpro.gugusto.graphic.TextureLoader;
import de.thu.gpro.gugusto.util.Vector;

import java.awt.image.BufferedImage;

public class JumpPad extends TextureBlock {

    public static final BufferedImage TEXTURE = TextureLoader.get(Texture.BLOCK_JUMP_PAD);

    public JumpPad(Vector position) {
        super(BlockType.JUMP_PAD, position, TEXTURE);
    }

    @Override
    public void collision(GameObject other) {
        super.collision(other);
        if (other instanceof DynamicGameObject && ((DynamicGameObject) other).isOnGround()) {
            double bottom = other.getBoundingBox().getPosition().getY() + other.getBoundingBox().getSize().getHeight();
            if (bottom < boundingBox.getPosition().getY() + boundingBox.getSize().getHeight() / 4) {
                ((Player) other).getVelocity().setY(-25);
            }
        }
    }
}
