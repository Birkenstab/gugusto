package de.thu.gpro.gugusto.game.object.blocks;

import de.thu.gpro.gugusto.game.Camera;
import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.util.Size;
import de.thu.gpro.gugusto.util.Vector;

import java.awt.*;

public class GoalCoin extends Coin {
    public static final double ZOOM_TIME = 1;

    private Vector fromPosition;
    private Size fromSize = new Size(0.6, 0.6);
    private double elapsed = -1;

    public GoalCoin(Vector position) {
        super(position, new Size(0.6, 0.6));
        fromPosition = position.clone().add(fromSize.toVector().multiply(0.5));
    }

    @Override
    public void update(double delta) {
        super.update(delta);
        elapsed += delta;
        if (elapsed > ZOOM_TIME) {
            elapsed = ZOOM_TIME;
        }
    }

    @Override
    public void draw(Graphics2D g2d, Camera camera) {
        if (elapsed >= 0) {
            Vector toPosition = camera.toWorldCoordinates(new Vector(Game.INNER_WIDTH / 2, Game.INNER_HEIGHT / 3));
            Size toSize = camera.toWorldCoordinates(new Size(150, 150));
            Size size = new Size(fromSize.getWidth() + (toSize.getWidth() - fromSize.getWidth()) * (elapsed / ZOOM_TIME),
                    fromSize.getHeight() + (toSize.getHeight() - fromSize.getHeight()) * (elapsed / ZOOM_TIME));

            Vector position = fromPosition.clone().add(toPosition.clone().subtract(fromPosition).multiply(elapsed / ZOOM_TIME));
            position.subtract(size.toVector().multiply(0.5));

            boundingBox.getSize().set(size);
            boundingBox.getPosition().set(position);
        }

        super.draw(g2d, camera);
    }

    public double getElapsed() {
        return elapsed;
    }
}
