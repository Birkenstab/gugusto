package main;

import game.level.Level;
import game.level.LevelLoader;

import java.nio.file.Paths;

public class LevelLoaderTest {

    public static void main(String[] args){
        //Level level = LevelLoader.loadTestLevel();
        LevelLoader loader = new LevelLoader();

        //loader.save(level, Paths.get("./test.gug"));
        Level level = loader.load(Paths.get("./test.gug"));
        System.out.println(level.getName());
        System.out.println(level.getStartPosition());
    }

}
