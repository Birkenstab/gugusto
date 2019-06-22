package de.thu.gpro.gugusto.util;

import de.thu.gpro.gugusto.collision.CollisionUtil;
import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.game.level.Chunk;
import de.thu.gpro.gugusto.game.level.ChunkList;
import de.thu.gpro.gugusto.game.object.blocks.Block;

public class AiUtil {

    public static ChunkList getChunkList() {
        return Game.getInstance().getCurrentLevel().getChunkList();
    }

    public static boolean isBlock(Vector position) {
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
}
