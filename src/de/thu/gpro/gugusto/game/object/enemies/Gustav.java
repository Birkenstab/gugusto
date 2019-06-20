package de.thu.gpro.gugusto.game.object.enemies;

import de.thu.gpro.gugusto.game.Camera;
import de.thu.gpro.gugusto.graphic.SpriteSheet;
import de.thu.gpro.gugusto.graphic.Texture;
import de.thu.gpro.gugusto.graphic.TextureLoader;
import de.thu.gpro.gugusto.graphic.animation.Animation;
import de.thu.gpro.gugusto.graphic.animation.SpriteAnimation;
import de.thu.gpro.gugusto.util.Vector;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Gustav extends GenericWalkingEnemy {
    private static BufferedImage[] animationImage = SpriteSheet.extract(TextureLoader.get(Texture.ENEMY_GUSTAV_WALK), 18,1, 100, 80);

    private double lastDecisionTime;
    private boolean walkingLeft;

    public Gustav(Vector position) {
        this(position, 0.8);
    }

    public Gustav(Vector position, double size) {
        super(EnemyType.GUSTAV, position, size, new SpriteAnimation(animationImage, 1000));
    }

    @Override
    protected void ai(double delta) {
        if (isOnGround()) {
            lastDecisionTime += delta;
            if (lastDecisionTime > 2) {
                lastDecisionTime = 0;
                if (Math.random() < 0.4)
                    walkingLeft = !walkingLeft;
            }

            if (walkingLeft)
                walkLeft();
            else
                walkRight();
        }
    }
}
