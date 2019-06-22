package de.thu.gpro.gugusto.game.object.player;

import de.thu.gpro.gugusto.game.object.Direction;
import de.thu.gpro.gugusto.input.event.KeyEvent;
import de.thu.gpro.gugusto.util.AiUtil;
import de.thu.gpro.gugusto.util.Vector;

public class AiPlayer extends Player {
    private double lastJump;

    public AiPlayer(Vector position) {
        super(position);
    }

    @Override
    public boolean onKeyDown(KeyEvent event) {
        return false;
    }

    @Override
    public boolean onKeyUp(KeyEvent event) {
        return false;
    }


    @Override
    public void update(double delta) {
        lastJump += delta;
        Vector target = boundingBox.getPosition().clone().add(new Vector(0, boundingBox.getSize().getHeight() + 0.5));
        Vector movement = new Vector(5.0 / 15, 0);
        if (true) {
            getState().setDirection(Direction.RIGHT);
            target.add(movement);
        } else {
            getState().setDirection(Direction.LEFT);
            target.subtract(movement).add(new Vector(boundingBox.getSize().getWidth(), 0));
        }

        spaceDown = false;

        if (Math.random() < 0.1 && lastJump > 3) {
            jump();
        }

        if (!AiUtil.isBlock(target)) {
            jump();
        }

        if (AiUtil.isBlock(target.add(new Vector(0.7, -1))))
            jump();

        super.update(delta);
    }

    private void jump() {
        spaceDown = true;
        lastJump = 0;
    }
}
