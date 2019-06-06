package game.object;

import game.Camera;
import util.Size;
import util.Vector;

import java.awt.*;

public class GoalBlock extends StaticGameObject {

    public GoalBlock(Vector position) {
        super(position, new Size(1, 1));
    }

    @Override
    protected void draw(Graphics2D g2d, Camera camera, Vector position, Size size) {
        g2d.setColor(Color.GRAY);
        g2d.drawRect((int)position.getX(), (int)position.getY(), (int)size.getWidth(), (int)size.getHeight());
        // Todo nur vorrübergehend zum Testen sichtbar
    }

    @Override
    public void update(double delta) {}
}
