package game.level;

import game.object.Block;
import game.object.GameObject;
import util.Size;
import util.Vector;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LevelLoader {

    private static final String FF_IDENTIFIER = "GUG";
    private static final short FF_MAJOR_VERSION = 0;
    private static final short FF_MINOR_VERSION = 1;
    private static final int FF_VERSION = (FF_MAJOR_VERSION << 16) | FF_MINOR_VERSION;

    public static Level loadTestLevel() {
        int height = 2;
        int width = 10;
        List<List<Chunk>> chunks = new ArrayList<>();

        for (int x = 0; x < width; x++) {
            chunks.add(new ArrayList<>(height));
            for (int y = 0; y < height; y++) {
                List<GameObject> gameObjects = new ArrayList<>();

                for (int i = 0; i < 10; i++) {
                    gameObjects.add(new Block(new Vector(x * Chunk.SIZE + i * Block.SIZE, y * Chunk.SIZE + i * Block.SIZE)));
                }

                Chunk chunk = new Chunk(gameObjects);
                chunks.get(x).add(chunk);
            }
        }


        ChunkList chunkList = new ChunkList(chunks, width, height);
        return new Level("Test", chunkList, new Vector(40, 20));
    }

    public void save(Level level, Path path){
        List<GameObject> blocks = level.getChunkList().getBlocks();
        int size = getRequiredFileSize(level, blocks.size());
        ByteList bytes = new ByteList(size);

        for(char c : FF_IDENTIFIER.toCharArray()) bytes.writeUint8((byte)c);
        bytes.writeUint32(FF_VERSION);
        bytes.writeString(level.getName());
        bytes.writeUint32((int)level.getStartPosition().getX());
        bytes.writeUint32((int)level.getStartPosition().getY());
        bytes.writeUint32(level.getChunkList().getWidth());
        bytes.writeUint32(level.getChunkList().getHeight());

        for(GameObject object : blocks){
            bytes.writeUint8((byte)1);// Change as soon as ID's are implemented
            bytes.writeUint32((int)object.getBoundingBox().getPosition().getX());
            bytes.writeUint32((int)object.getBoundingBox().getPosition().getY());
        }

        writeToFile(bytes.getByteArray(), path);
    }

    public Level load(Path path){
        ByteList bytes = readFromFile(path);
        if(bytes == null) return null;
        if(!isGugustoFile(bytes)) return null;
        String name = bytes.readString();
        Vector startPosition = new Vector(bytes.readUint32(), bytes.readUint32());
        int width = bytes.readUint32();
        int height = bytes.readUint32();
        List<GameObject> gameObjects = new ArrayList<>();

        for(int i = 0; i < height * width + width; i++){
            byte blockId = bytes.readUint8();
            gameObjects.add(new Block(new Vector(bytes.readUint32(), bytes.readUint32())));
        }

        return new Level(name, new ChunkList(null, width, height), startPosition);
    }

    private boolean isGugustoFile(ByteList byteList){
        String identifier = new String(new byte[]{byteList.readUint8(), byteList.readUint8(), byteList.readUint8()});
        int version = byteList.readUint32();

        if(!identifier.equals(FF_IDENTIFIER)){
            System.out.println("File is not a gugusto map file.");
            return false;
        } else if(version != FF_VERSION){
            System.out.println("Outdated file version.");
            return false;
        }

        return true;
    }

    private int getRequiredFileSize(Level level, int blockCount){
        int size = 0;

        size += FF_IDENTIFIER.length() + 4;// Identifier and version
        size += 4 + level.getName().length();// Level name
        size += 4 + 4;// Player starting position;
        size += 4 + 4;// Level width and height
        size += (1 + 4 + 4) * blockCount;// Block position and id
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

    private ByteList readFromFile(Path path){
        try {
            return new ByteList(Files.readAllBytes(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("File not found: " + path);

        return null;
    }
}
