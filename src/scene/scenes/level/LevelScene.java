package scene.scenes.level;

import game.level.Level;
import game.level.LevelLoader;
import scene.Scene;

import java.nio.file.Paths;

public class LevelScene extends Scene {

    private LevelAction levelAction;

    public LevelScene(){
        //Level level = LevelLoader.loadTestLevel();
        Level level = LevelLoader.load(Paths.get("./test.gug"));
        levelAction = new LevelAction(level);
        addLayer(new LevelLayer(level, levelAction));
        addLayer(new LevelUILayer(levelAction));
    }

}
