package main;

import game.level.Level;
import game.level.LevelLoader;

import java.nio.file.Paths;

public class LevelLoaderTest {

    public static void main(String[] args){
        Level level1 = LevelLoader.loadTestLevel();
        LevelLoader.save(level1, Paths.get("./test.gug"));

        Level level = LevelLoader.load(Paths.get("./test.gug"));
        System.out.println(level.getChunkList().getBlockCount());
    }

}
