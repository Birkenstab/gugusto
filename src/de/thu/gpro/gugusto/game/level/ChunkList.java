package de.thu.gpro.gugusto.game.level;

import de.thu.gpro.gugusto.game.object.blocks.Block;
import de.thu.gpro.gugusto.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChunkList {

    private List<List<Chunk>> chunks;
    private int blockCount;
    private int width;
    private int height;

    public ChunkList(int width, int height){
        this.width = width;
        this.height = height;
        blockCount = 0;
        chunks = new ArrayList<>();

        for(int x = 0; x < width; x++){
            chunks.add(new ArrayList<>());

            for(int y = 0; y < height; y++){
                chunks.get(x).add(new Chunk(new ArrayList<>()));
            }
        }
    }

    public ChunkList(List<List<Chunk>> chunks, int blockCount, int width, int height){
        this.chunks = chunks;
        this.blockCount = blockCount;
        this.width = width;
        this.height = height;
    }

    public Chunk get(int x, int y){
        if(x < 0 || x > width - 1 || y < 0 || y > height - 1) return null;
        return chunks.get(x).get(y);
    }

    public List<Chunk> getNearby(Vector pos){
        List<Chunk> nearby = new ArrayList<>();

        int chunkX = Chunk.getChunkNo(pos.getX());
        int chunkY = Chunk.getChunkNo(pos.getY());
        int modX = (int) Chunk.getPositionInChunk(pos.getX());
        int modY = (int) Chunk.getPositionInChunk(pos.getY());

        nearby.add(get(chunkX, chunkY));


        if(modX == 0) // Chunk links
            nearby.add(get(chunkX - 1, chunkY));
        if(modX == Chunk.SIZE - 1) // Chunk rechts
            nearby.add(get(chunkX + 1, chunkY));
        if(modY == 0) // Chunk oben
            nearby.add(get(chunkX, chunkY - 1));
        if(modY == Chunk.SIZE - 1) // Chunk unten
            nearby.add(get(chunkX, chunkY + 1));

        if(modX == 0 && modY == 0) // Chunk links oben
            nearby.add(get(chunkX - 1, chunkY - 1));
        if(modX == 0 && modY == Chunk.SIZE - 1) // Chunk links unten
            nearby.add(get(chunkX - 1, chunkY + 1));
        if(modX == Chunk.SIZE - 1 && modY == 0) // Chunk rechts oben
            nearby.add(get(chunkX + 1, chunkY - 1));
        if(modX == Chunk.SIZE - 1 && modY == Chunk.SIZE - 1) // Chunk rechts unten
            nearby.add(get(chunkX + 1, chunkY + 1));

        nearby.removeIf(Objects::isNull); // nicht existente Chunks löschen

        return nearby;
    }

    public boolean addBlock(Block block, Vector chunkPosition){
        Chunk chunk = get((int)chunkPosition.getX(), (int)chunkPosition.getY());

        if(chunk != null && chunk.addBlock(block)){
            blockCount++;
            return true;
        }

        return false;
    }


    public Block removeBlock(Vector position, Vector chunkPosition){
        Chunk chunk = get((int)chunkPosition.getX(), (int)chunkPosition.getY());

        if(chunk != null){
            Block block = chunk.removeBlock(position);

            if(block != null){
                blockCount--;
                return block;
            }
        }

        return null;
    }

    public int getBlockCount(){
        return blockCount;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<List<Chunk>> getChunks(){
        return chunks;
    }
}
