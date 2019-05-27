package game.level;

import game.object.Block;
import game.object.GameObject;
import game.object.StaticGameObject;
import util.Vector;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LevelLoader {

    private static final String FF_IDENTIFIER = "GUG";
    private static final short FF_MAJOR_VERSION = 0;
    private static final short FF_MINOR_VERSION = 2;
    private static final int FF_VERSION = (FF_MAJOR_VERSION << 16) | FF_MINOR_VERSION;
    private static final int FF_CHUNK_SEPARATOR = 0xFF;

    public static Level loadTestLevel() {
        int height = 2;
        int width = 10;
        List<List<Chunk>> chunks = new ArrayList<>();

        for (int x = 0; x < width; x++) {
            chunks.add(new ArrayList<>(height));
            for (int y = 0; y < height; y++) {
                List<StaticGameObject> gameObjects = new ArrayList<>();

                for (int i = 0; i < 10; i++) {
                    gameObjects.add(new Block(new Vector(x * Chunk.SIZE + i, y * Chunk.SIZE + i)));
                }

                Chunk chunk = new Chunk(gameObjects);
                chunks.get(x).add(chunk);
            }
        }


        ChunkList chunkList = new ChunkList(chunks, 200, width, height);
        return new Level("Test", chunkList, new Vector(40, 20));
    }

    public void save(Level level, Path path){
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

        writeToFile(dataView.getByteArray(), path);
    }

    private void saveChunks(DataView dataView, ChunkList chunkList){
        for(List<Chunk> list : chunkList.getChunks()){
            for(Chunk chunk : list){
                for(GameObject object : chunk.getGameObjects()){
                    dataView.writeUint8((byte)1);// Change as soon as ID's are implemented
                    dataView.writeUint32((int)object.getBoundingBox().getPosition().getX());
                    dataView.writeUint32((int)object.getBoundingBox().getPosition().getY());
                }
                dataView.writeUint8((byte)FF_CHUNK_SEPARATOR);
            }
        }
    }

    public Level load(Path path){
        DataView dataView = readFromFile(path);
        if(dataView == null || !isGugustoFile(dataView)) return null;

        String name = dataView.readString();
        Vector startPosition = new Vector(dataView.readUint32(), dataView.readUint32());
        ChunkList chunkList = readChunks(dataView);

        return new Level(name, chunkList, startPosition);
    }

    private ChunkList readChunks(DataView dataView){
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

    private Chunk readChunk(DataView dataView){
        List<StaticGameObject> gameObjects = new ArrayList<>();

        while(true){
            int blockId = dataView.readUint8();

            if(blockId == FF_CHUNK_SEPARATOR){
                return new Chunk(gameObjects);
            } else {
                gameObjects.add(new Block(new Vector(dataView.readUint32(), dataView.readUint32())));
            }
        }
    }

    private boolean isGugustoFile(DataView dataView){
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

    private int getRequiredFileSize(Level level){
        int size = 0;

        size += FF_IDENTIFIER.length() + 4;// Identifier and version
        size += 4 + level.getName().length();// Level name
        size += 4 + 4;// Player starting position;
        size += 4 + 4;// Level width and height
        size += 4; // Block count
        size += (1 + 4 + 4) * level.getChunkList().getBlockCount();// Block position and id
        size += level.getChunkList().getWidth() * level.getChunkList().getHeight();// Chunk separator 0xff
        size += (1 + 4 + 4) * level.getEnemys().size();// Enemy Position and id

        return size;
    }

    private void writeToFile(byte[] data, Path path){
        try {
            Files.write(path, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private DataView readFromFile(Path path){
        try {
            return new DataView(Files.readAllBytes(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("File not found: " + path);

        return null;
    }
}
