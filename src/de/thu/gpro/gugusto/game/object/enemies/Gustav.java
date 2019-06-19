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

    private Animation animation = new SpriteAnimation(animationImage, 1000);

    public Gustav(Vector position) {
        this(position, 0.8);
    }

    public Gustav(Vector position, double size) {
        super(EnemyType.GUSTAV, position, size);
        animation.start();
    }

    @Override
    public void draw(Graphics2D g2d, Camera camera) {
        super.draw(g2d, camera);
        animation.draw(g2d, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void update(double delta) {
        super.update(delta);
        animation.update(delta);
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
