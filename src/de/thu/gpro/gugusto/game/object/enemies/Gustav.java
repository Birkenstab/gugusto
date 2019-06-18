package de.thu.gpro.gugusto.game.object.enemies;

import de.thu.gpro.gugusto.game.Camera;
import de.thu.gpro.gugusto.game.object.GameObject;
import de.thu.gpro.gugusto.game.object.player.Player;
import de.thu.gpro.gugusto.util.Size;
import de.thu.gpro.gugusto.util.Vector;

import java.awt.*;

public class Gustav extends Enemy {
    private static final double WALKING_SPEED = 3;
    private boolean walkingLeft = true;
    private double lastDecisionTime;

    public Gustav(Vector position) {
        this(position, 1);
    }

    public Gustav(Vector position, int size) {
        super(EnemyType.GUSTAV, position, new Size(size, size));
        setSolid(true);
    }

    @Override
    public void draw(Graphics2D g2d, Camera camera) {
        super.draw(g2d, camera);
        g2d.setColor(Color.RED);
        g2d.fillRect(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void update(double delta) {
        super.update(delta);

        if (isOnGround()) {
            double speed = WALKING_SPEED;
            if (walkingLeft)
                speed = -speed;

            boundingBox.getPosition().add(new Vector(-delta * speed, 0));
        }

        lastDecisionTime += delta;
        if (lastDecisionTime > 2) {
            lastDecisionTime = 0;
            if (Math.random() < 0.4)
                walkingLeft = !walkingLeft;
        }
    }

    @Override
    public void collision(GameObject other) {
        super.collision(other);
        if (other instanceof Player) {
            ((Player) other).kill();
        }
    }
}
