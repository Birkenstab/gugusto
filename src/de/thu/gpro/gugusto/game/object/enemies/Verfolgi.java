package de.thu.gpro.gugusto.game.object.enemies;

import de.thu.gpro.gugusto.collision.CollisionUtil;
import de.thu.gpro.gugusto.game.Camera;
import de.thu.gpro.gugusto.game.level.Chunk;
import de.thu.gpro.gugusto.game.object.blocks.Block;
import de.thu.gpro.gugusto.util.Vector;

import java.awt.*;
import java.nio.file.attribute.PosixFileAttributes;

public class Verfolgi extends GenericWalkingEnemy {

    public Verfolgi(Vector position) {
        super(EnemyType.VERFOLGI, position);
    }

    @Override
    public void draw(Graphics2D g2d, Camera camera) {
        super.draw(g2d, camera);
        g2d.setColor(Color.BLUE);
        g2d.fillRect(getX(), getY(), getWidth(), getHeight());
    }

    private boolean isBlock(Vector position) {
        Chunk chunk = getChunkList().get(Chunk.getChunkNo(position.getX()), Chunk.getChunkNo(position.getY()));
        if (chunk == null)
            return false;
        for (Block block : chunk.getBlocks()) {
            if (!block.isSolid())
                continue;
            if (CollisionUtil.isPointInRect(position, block.getBoundingBox()))
                return true;
        }
        return false;
    }

    @Override
    protected void ai(double delta) {
        Vector target = boundingBox.getPosition().clone().add(new Vector(0, boundingBox.getSize().getHeight() + 0.5));
        Vector movement = new Vector(WALKING_SPEED / 15, 0);
        if (getPlayerPosition().getX() > boundingBox.getPosition().getX()) {
            walkRight();
            target.add(movement);
        } else {
            walkLeft();
            target.subtract(movement).add(new Vector(boundingBox.getSize().getWidth(), 0));
        }

        if (Math.abs(getPlayerPosition().getX() - boundingBox.getPosition().getX()) < 4) {
            jump();
        }

        if (!isBlock(target)) {
            jump();
        }

    }
}
