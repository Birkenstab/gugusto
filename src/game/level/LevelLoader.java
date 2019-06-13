package game.level;

import game.object.blocks.Block;
import game.object.blocks.BlockFactory;
import game.object.blocks.GoalBlock;
import game.object.blocks.GrassBlock;
import game.object.enemies.Enemy;
import game.object.enemies.EnemyFactory;
import util.Vector;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class LevelLoader {

    private LevelLoader(){}

    private static final String FF_IDENTIFIER = "GUG";
    private static final short FF_MAJOR_VERSION = 0;
    private static final short FF_MINOR_VERSION = 2;
    private static final int FF_VERSION = (FF_MAJOR_VERSION << 16) | FF_MINOR_VERSION;
    private static final int FF_CHUNK_SEPARATOR = 0xFF;

    public static Level loadTestLevel() {
        int height = 2;
        int width = 2;
        List<List<Chunk>> chunks = new ArrayList<>();

        for (int x = 0; x < width; x++) {
            chunks.add(new ArrayList<>(height));
            for (int y = 0; y < height; y++) {
                List<Block> blocks = new ArrayList<>();

                for (int i = 0; i < 10; i++) {
                    blocks.add(new GrassBlock(new Vector(x * Chunk.SIZE + i, y * Chunk.SIZE + 30)));
                }

                blocks.add(new GrassBlock(new Vector(x * Chunk.SIZE + 10, y * Chunk.SIZE + 29)));
                blocks.add(new GrassBlock(new Vector(x * Chunk.SIZE + 10, y * Chunk.SIZE + 28)));
                blocks.add(new GrassBlock(new Vector(x * Chunk.SIZE + 10, y * Chunk.SIZE + 27)));
                blocks.add(new GrassBlock(new Vector(x * Chunk.SIZE + 11, y * Chunk.SIZE + 26)));
                blocks.add(new GrassBlock(new Vector(x * Chunk.SIZE + 11, y * Chunk.SIZE + 25)));
                blocks.add(new GrassBlock(new Vector(x * Chunk.SIZE + 12, y * Chunk.SIZE + 24)));
                blocks.add(new GrassBlock(new Vector(x * Chunk.SIZE + 12, y * Chunk.SIZE + 23)));
                blocks.add(new GrassBlock(new Vector(x * Chunk.SIZE + 13, y * Chunk.SIZE + 22)));
                blocks.add(new GrassBlock(new Vector(x * Chunk.SIZE + 13, y * Chunk.SIZE + 21)));
                blocks.add(new GrassBlock(new Vector(x * Chunk.SIZE + 14, y * Chunk.SIZE + 20)));

                for (int i = 15; i < 25; i++) {
                    blocks.add(new GrassBlock(new Vector(x * Chunk.SIZE + i, y * Chunk.SIZE + 20)));
                }

                if (x == width - 1) {
                    for (int i = 0; i < Chunk.SIZE; i++) {
                        blocks.add(new GoalBlock(new Vector(x * Chunk.SIZE + 15, y * Chunk.SIZE + i)));
                    }
                }

                Chunk chunk = new Chunk(blocks);
                chunks.get(x).add(chunk);
            }
        }


        ChunkList chunkList = new ChunkList(chunks, 200, width, height);
        return new Level("Test", chunkList, new ArrayList<>(), new Vector(4, 25));
    }

    public static void save(Level level, Path path){
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

        writeToFile(dataView.getByteArray(), path);
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

    public static Level load(Path path){
        DataView dataView = readFromFile(path);
        if(dataView == null || !isGugustoFile(dataView)) return null;

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

    private static boolean isGugustoFile(DataView dataView){
        String identifier = dataView.readCharSequenceAsString(FF_IDENTIFIER.length());
        int version = dataView.readUint32();

        if(!identifier.equals(FF_IDENTIFIER)){
            System.out.println("File is not a gugusto map file.");
            return false;
        } else if(version != FF_VERSION){
            System.out.println("Outdated file version.");
            return false;
        }

        return true;
    }

    private static int getRequiredFileSize(Level level){
        int size = 0;

        size += FF_IDENTIFIER.length() + 4;// Identifier and version
        size += 4 + level.getName().length();// Level name
        size += 4 + 4;// Player starting position;
        size += 4 + 4;// Level width and height
        size += 4; // Block count
        size += (1 + 4 + 4) * level.getChunkList().getBlockCount();// Block position and id
        size += level.getChunkList().getWidth() * level.getChunkList().getHeight();// Chunk separator 0xff
        size += (1 + 4 + 4) * level.getEnemies().size();// Enemy Position and id

        return size;
    }

    private static void writeToFile(byte[] data, Path path){
        try {
            Files.write(path, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static DataView readFromFile(Path path){
        try {
            return new DataView(Files.readAllBytes(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("File not found: " + path);

        return null;
    }
}
