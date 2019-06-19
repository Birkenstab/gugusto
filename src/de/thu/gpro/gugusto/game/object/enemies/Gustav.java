package de.thu.gpro.gugusto.game.object.enemies;

import de.thu.gpro.gugusto.util.Vector;


public class Gustav extends GenericWalkingEnemy {
    private double lastDecisionTime;
    private boolean walkingLeft;

    public Gustav(Vector position) {
        this(position, 0.8);
    }

    public Gustav(Vector position, double size) {
        super(EnemyType.GUSTAV, position, size);
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
