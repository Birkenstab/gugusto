package de.thu.gpro.gugusto.game.level.io;

import de.thu.gpro.gugusto.game.level.Level;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public final class LevelLoader {

    private LevelLoader(){}

    public static void save(Level level, Path path){
        byte[] bytes = LevelFormat.encode(level);
        writeToFile(bytes, path);
    }

    public static Level load(Path path){
        byte[] bytes = readFromFile(path);
        Level level = LevelFormat.decode(bytes);

        if(level == null){
            if(LevelFormat.statusCode == LevelFormat.Status.NOT_GUG_FILE){
                throw new Error(String.format("\"%s\" is not a gugusto level file.", path));
            } else if(LevelFormat.statusCode == LevelFormat.Status.OUTDATED){
                throw new Error(String.format("\"%s\" version is outdated.", path));
            }
        }

        return level;
    }

    private static void writeToFile(byte[] data, Path path){
        try {
            Files.write(path, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static byte[] readFromFile(Path path){
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("File not found: " + path);

        return null;
    }
}
