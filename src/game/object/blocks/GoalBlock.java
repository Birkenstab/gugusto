package game.object.blocks;

import game.Camera;
import util.Vector;

import java.awt.*;

public class GoalBlock extends Block {

    public GoalBlock(Vector position) {
        super(BlockType.GOAL, position, false);
    }

    @Override
    public void draw(Graphics2D g2d, Camera camera) {
        super.draw(g2d, camera);

        g2d.setColor(Color.GRAY);
        g2d.drawRect(getX(), getY(), getWidth(), getHeight());
        // Todo nur vorrübergehend zum Testen sichtbar
    }

}
