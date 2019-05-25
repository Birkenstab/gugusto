package main;

import game.level.Level;
import game.level.LevelLoader;

import java.nio.file.Paths;

public class LevelLoaderTest {

    public static void main(String[] args){
        LevelLoader loader = new LevelLoader();

        Level level1 = LevelLoader.loadTestLevel();
        loader.save(level1, Paths.get("./test.gug"));

        Level level = loader.load(Paths.get("./test.gug"));
        System.out.println(level.getChunkList().getBlockCount());
    }

}
