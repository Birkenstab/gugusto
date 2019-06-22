package de.thu.gpro.gugusto.game.level.io;

import de.thu.gpro.gugusto.game.level.Level;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public final class LevelLoader {

    private LevelLoader(){}

    public static void save(Level level, Path path){
        byte[] bytes = LevelFormat.encode(level);
        IOUtil.write(bytes, path);
    }

    public static Level load(Path path){
        byte[] bytes = IOUtil.read(path);
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

}
