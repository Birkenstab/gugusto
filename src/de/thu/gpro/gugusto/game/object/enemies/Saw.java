package de.thu.gpro.gugusto.game.object.enemies;

import de.thu.gpro.gugusto.game.Camera;
import de.thu.gpro.gugusto.game.object.DynamicGameObject;
import de.thu.gpro.gugusto.game.object.GameObject;
import de.thu.gpro.gugusto.graphic.Texture;
import de.thu.gpro.gugusto.graphic.TextureLoader;
import de.thu.gpro.gugusto.graphic.animation.RotationAnimation;
import de.thu.gpro.gugusto.util.Size;
import de.thu.gpro.gugusto.util.Vector;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Saw extends Enemy {

    private static final BufferedImage texture = TextureLoader.get(Texture.ENEMY_SAW);
    private static final int ROTATION_SPEED = 2000;

    private RotationAnimation animation;

    public Saw(Vector position){
        this(position, 2);
    }

    public Saw(Vector position, int size) {
        super(EnemyType.SAW, position, new Size(size, size));
        setMovable(false);
        setGravity(false);

        animation = new RotationAnimation(texture, ROTATION_SPEED, RotationAnimation.Anchor.CENTER);
        animation.start();
    }

    @Override
    public void draw(Graphics2D g2d, Camera camera){
        super.draw(g2d, camera);
        animation.draw(g2d, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void update(double delta) {
        super.update(delta);
        animation.update(delta);
    }

    @Override
    public void collision(GameObject other) {
        super.collision(other);
        if (other instanceof DynamicGameObject && !(other instanceof Saw)) {
            ((DynamicGameObject) other).kill();
        }
    }
}
