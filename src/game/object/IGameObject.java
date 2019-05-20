package game.object;

import collision.BoundingBox;

import java.awt.*;

public interface IGameObject {

    void draw(Graphics2D g2d);
    void update(double delta);

    BoundingBox getBoundingBox();
    void remove();
    boolean shouldBeRemoved();

}
