package de.thu.gpro.gugusto.game.object.enemies;

import de.thu.gpro.gugusto.game.Camera;
import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.game.level.ChunkList;
import de.thu.gpro.gugusto.game.object.Direction;
import de.thu.gpro.gugusto.game.object.GameObject;
import de.thu.gpro.gugusto.game.object.player.Player;
import de.thu.gpro.gugusto.util.Size;
import de.thu.gpro.gugusto.util.Vector;

import java.awt.*;

public abstract class GenericWalkingEnemy extends Enemy {
    protected static final double WALKING_SPEED = 3;
    private Direction direction = Direction.NONE;
    private boolean jumping;

    public GenericWalkingEnemy(EnemyType enemyType, Vector position) {
        this(enemyType, position, 0.8);
    }

    public GenericWalkingEnemy(EnemyType enemyType, Vector position, double size) {
        super(enemyType, position, new Size(size, size));
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

        jumping = false;
        direction = Direction.NONE;

        ai(delta);

        double speed = 0;
        if (direction == Direction.LEFT)
            speed = -WALKING_SPEED;
        else if (direction == Direction.RIGHT)
            speed = WALKING_SPEED;

        boundingBox.getPosition().add(new Vector(delta * speed, 0));

        if (isOnGround() && jumping) {
            getVelocity().setY(-12.5);
        }
    }

    protected Vector getPlayerPosition() {
        return Game.getInstance().getCurrentLevel().getPlayer().getBoundingBox().getPosition();
    }

    protected ChunkList getChunkList() {
        return Game.getInstance().getCurrentLevel().getChunkList();
    }

    protected abstract void ai(double delta);

    protected void walkLeft() {
        direction = Direction.LEFT;
    }

    protected void walkRight() {
        direction = Direction.RIGHT;
    }

    protected void jump() {
        jumping = true;
    }

    @Override
    public void collision(GameObject other) {
        super.collision(other);
        if (other instanceof Player) {
            double playerBottom = other.getBoundingBox().getPosition().getY() + other.getBoundingBox().getSize().getHeight();
            if (playerBottom < boundingBox.getPosition().getY() + boundingBox.getSize().getHeight() / 4) {
                kill(other);
                ((Player) other).getVelocity().setY(-15);
            } else {
                ((Player) other).kill(this);
            }
        }
    }
}
