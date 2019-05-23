package game.level;

import game.object.GameObject;
import util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ChunkList {

    private List<List<Chunk>> chunks;
    private int width;
    private int height;

    public ChunkList(List<List<Chunk>> chunks, int width, int height){
        this.chunks = chunks;
        this.width = width;
        this.height = height;
    }

    public Chunk get(int x, int y){
        if(x < 0 || x > width - 1 || y < 0 || y > height - 1) return null;
        return chunks.get(x).get(y);
    }

    public List<Chunk> getNearby(GameObject object){
        List<Chunk> nearby = new ArrayList<>();
        Vector pos = object.getBoundingBox().getPosition();

        int chunkX = (int)(pos.getX() / Chunk.SIZE);
        int chunkY = (int)(pos.getY() / Chunk.SIZE);
        int modX = (int)(pos.getX() % Chunk.SIZE);
        int modY = (int)(pos.getY() % Chunk.SIZE);

        nearby.add(get(chunkX, chunkY));

        if(modX == 0 && chunkX != 0) nearby.add(get(chunkX - 1, chunkY));
        if(modX == Chunk.SIZE - 1 && chunkX != width - 1) nearby.add(get(chunkX + 1, chunkY));
        if(modY == 0 && chunkY != 0) nearby.add(get(chunkX, chunkY - 1));
        if(modX == Chunk.SIZE - 1 && chunkX != height - 1) nearby.add(get(chunkX, chunkY + 1));

        return nearby;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
