package de.thu.gpro.gugusto.game.object.enemies;

import de.thu.gpro.gugusto.collision.CollisionUtil;
import de.thu.gpro.gugusto.game.Camera;
import de.thu.gpro.gugusto.game.level.Chunk;
import de.thu.gpro.gugusto.game.object.blocks.Block;
import de.thu.gpro.gugusto.graphic.SpriteSheet;
import de.thu.gpro.gugusto.graphic.Texture;
import de.thu.gpro.gugusto.graphic.TextureLoader;
import de.thu.gpro.gugusto.graphic.animation.SpriteAnimation;
import de.thu.gpro.gugusto.util.AiUtil;
import de.thu.gpro.gugusto.util.Vector;

import java.awt.image.BufferedImage;

public class Verfolgi extends GenericWalkingEnemy {
    private static final BufferedImage[] ANIMATION_FRAMES = SpriteSheet.extract(TextureLoader.get(Texture.ENEMY_VERFOLGI_WALK), 18,1, 100, 80);
    public static final BufferedImage TEXTURE = ANIMATION_FRAMES[0];

    public Verfolgi(Vector position) {
        super(EnemyType.VERFOLGI, position, new SpriteAnimation(ANIMATION_FRAMES, 1000));
    }

    @Override
    protected void ai(double delta) {
        Vector target = boundingBox.getPosition().clone().add(new Vector(0, boundingBox.getSize().getHeight() + 0.5));
        Vector movement = new Vector(WALKING_SPEED / 15, 0);
        if (getPlayerPosition().getX() > boundingBox.getPosition().getX()) {
            walkRight();
            target.add(movement);
        } else {
            walkLeft();
            target.subtract(movement).add(new Vector(boundingBox.getSize().getWidth(), 0));
        }

        if (Math.abs(getPlayerPosition().getX() - boundingBox.getPosition().getX()) < 4) {
            jump();
        }

        if (!AiUtil.isBlock(target)) {
            jump();
        }

    }
}
