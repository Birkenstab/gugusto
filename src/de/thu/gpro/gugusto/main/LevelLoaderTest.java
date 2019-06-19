package de.thu.gpro.gugusto.main;

import de.thu.gpro.gugusto.game.level.io.LevelUtil;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class LevelLoaderTest {

    public static void main(String[] args){
        try {
            List<Path> levelPathList = LevelUtil.getAllLevels(Paths.get("./levels"));
            for(Path path : levelPathList) System.out.println(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
