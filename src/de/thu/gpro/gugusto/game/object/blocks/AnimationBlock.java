package de.thu.gpro.gugusto.game.object.blocks;

import de.thu.gpro.gugusto.game.Camera;
import de.thu.gpro.gugusto.graphic.animation.SpriteAnimation;
import de.thu.gpro.gugusto.util.Size;
import de.thu.gpro.gugusto.util.Vector;

import java.awt.*;

public abstract class AnimationBlock extends Block {

    private SpriteAnimation animation;

    AnimationBlock(BlockType type, Vector position, SpriteAnimation animation) {
        this(type, position, animation, true);
    }

    AnimationBlock(BlockType type, Vector position, SpriteAnimation animation, boolean isSolid) {
        this(type, position, animation, isSolid, new Size(1, 1));
    }

    AnimationBlock(BlockType type, Vector position, SpriteAnimation animation, boolean isSolid, Size size) {
        super(type, position, isSolid, size);
        this.animation = animation;
        animation.start();
    }

    @Override
    public void update(double delta){
        animation.update(delta);
    }

    @Override
    public void draw(Graphics2D g2d, Camera camera){
        super.draw(g2d, camera);
        animation.draw(g2d, getX(), getY(), getWidth(), getHeight());
    }

}
