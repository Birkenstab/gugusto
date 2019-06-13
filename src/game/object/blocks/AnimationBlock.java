package game.object.blocks;

import game.Camera;
import graphic.animation.SpriteAnimation;
import util.Vector;

import java.awt.*;

public abstract class AnimationBlock extends Block {

    private SpriteAnimation animation;

    AnimationBlock(BlockType type, Vector position, SpriteAnimation animation) {
        this(type, position, animation, true);
    }

    AnimationBlock(BlockType type, Vector position, SpriteAnimation animation, boolean isSolid) {
        super(type, position, isSolid);
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
