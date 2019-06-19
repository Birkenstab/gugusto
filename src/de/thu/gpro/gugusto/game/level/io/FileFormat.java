package de.thu.gpro.gugusto.game.level.io;

import de.thu.gpro.gugusto.game.level.Chunk;
import de.thu.gpro.gugusto.game.level.ChunkList;
import de.thu.gpro.gugusto.game.level.Level;
import de.thu.gpro.gugusto.game.object.blocks.Block;
import de.thu.gpro.gugusto.game.object.blocks.BlockFactory;
import de.thu.gpro.gugusto.game.object.enemies.Enemy;
import de.thu.gpro.gugusto.game.object.enemies.EnemyFactory;
import de.thu.gpro.gugusto.util.Vector;

import java.util.ArrayList;
import java.util.List;

public final class FileFormat {

    enum Status { SUCCESS, NOT_GUG_FILE, OUTDATED }

    private FileFormat(){}

    private static final String FF_IDENTIFIER = "GUG";
    private static final short FF_MAJOR_VERSION = 0;
    private static final short FF_MINOR_VERSION = 3;
    private static final int FF_VERSION = (FF_MAJOR_VERSION << 16) | FF_MINOR_VERSION;
    private static final int FF_CHUNK_SEPARATOR = 0xFF;

    static Status statusCode;

    static byte[] encode(Level level){
        int size = getRequiredFileSize(level);
        DataView dataView = new DataView(size);

        dataView.writeStringAsCharSequence(FF_IDENTIFIER);
        dataView.writeUint32(FF_VERSION);
        dataView.writeString(level.getName());
        dataView.writeUint32((int)level.getStartPosition().getX());
        dataView.writeUint32((int)level.getStartPosition().getY());
        dataView.writeUint32(level.getChunkList().getWidth());
        dataView.writeUint32(level.getChunkList().getHeight());
        dataView.writeUint32(level.getChunkList().getBlockCount());
        saveChunks(dataView, level.getChunkList());
        dataView.writeUint32(level.getEnemies().size());
        saveEnemys(dataView, level.getEnemies());

        return dataView.getByteArray();
    }

    private static void saveChunks(DataView dataView, ChunkList chunkList){
        for(List<Chunk> list : chunkList.getChunks()){
            for(Chunk chunk : list){
                for(Block block : chunk.getBlocks()){
                    dataView.writeUint8((byte)block.getId());// Change as soon as ID's are implemented
                    dataView.writeUint32((int)block.getBoundingBox().getPosition().getX());
                    dataView.writeUint32((int)block.getBoundingBox().getPosition().getY());
                }
                dataView.writeUint8((byte)FF_CHUNK_SEPARATOR);
            }
        }
    }

    private static void saveEnemys(DataView dataView, List<Enemy> enemies){
        for(Enemy enemy : enemies){
            dataView.writeUint8((byte)enemy.getId());
            dataView.writeUint32((int)enemy.getBoundingBox().getPosition().getX());
            dataView.writeUint32((int)enemy.getBoundingBox().getPosition().getY());
        }
    }

    static Level decode(byte[] bytes){
        DataView dataView = new DataView(bytes);
        statusCode = isGugustoFile(dataView);
        if(statusCode != Status.SUCCESS) return null;

        String name = dataView.readString();
        Vector startPosition = new Vector(dataView.readUint32(), dataView.readUint32());
        ChunkList chunkList = readChunks(dataView);
        List<Enemy> enemies = readEnemies(dataView);

        return new Level(name, chunkList, enemies, startPosition);
    }

    private static ChunkList readChunks(DataView dataView){
        List<List<Chunk>> chunks = new ArrayList<>();

        int width = dataView.readUint32();
        int height = dataView.readUint32();
        int blockCount = dataView.readUint32();

        for(int x = 0; x < width; x++){
            chunks.add(new ArrayList<>());

            for(int y = 0; y < height; y++){
                chunks.get(x).add(readChunk(dataView));
            }
        }

        return new ChunkList(chunks, blockCount, width, height);
    }

    private static Chunk readChunk(DataView dataView){
        List<Block> blocks = new ArrayList<>();

        while(true){
            int blockId = dataView.readUint8();

            if(blockId == FF_CHUNK_SEPARATOR){
                return new Chunk(blocks);
            } else {
                Vector position = new Vector(dataView.readUint32(), dataView.readUint32());
                blocks.add(BlockFactory.create(blockId, position));
            }
        }
    }

    private static List<Enemy> readEnemies(DataView dataView){
        int enemyCount = dataView.readUint32();
        List<Enemy> enemies = new ArrayList<>();

        for(int i = 0; i < enemyCount; i++){
            int id = dataView.readUint8();
            Enemy enemy = EnemyFactory.create(id, new Vector(dataView.readUint32(), dataView.readUint32()));
            enemies.add(enemy);
        }

        return enemies;
    }

    static boolean isGugustoFile(byte[] bytes){
        DataView dataView = new DataView(bytes);
        return isGugustoFile(dataView) == Status.SUCCESS;
    }

    private static Status isGugustoFile(DataView dataView){
        String identifier = dataView.readCharSequenceAsString(FF_IDENTIFIER.length());
        int version = dataView.readUint32();

        if(!identifier.equals(FF_IDENTIFIER)) return Status.NOT_GUG_FILE;
        //else if(version != FF_VERSION) return Status.OUTDATED;

        return Status.SUCCESS;
    }

    private static int getRequiredFileSize(Level level){
        int size = 0;

        size += FF_IDENTIFIER.length() + 4;// Identifier and version
        size += 4 + level.getName().length();// Level name
        size += 4 + 4;// Player starting position;
        size += 4 + 4;// Level width and height
        size += 4 + 4; // Block and Enemy count
        size += (1 + 4 + 4) * level.getChunkList().getBlockCount();// Block position and id
        size += level.getChunkList().getWidth() * level.getChunkList().getHeight();// Chunk separator 0xff
        size += (1 + 4 + 4) * level.getEnemies().size();// Enemy Position and id

        return size;
    }

}
