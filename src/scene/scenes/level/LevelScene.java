package scene.scenes.level;

import game.level.Level;
import game.level.LevelLoader;
import scene.Scene;

import java.nio.file.Paths;

public class LevelScene extends Scene {

    private Action action;

    public LevelScene(){
        //Level level = LevelLoader.loadTestLevel();
        Level level = LevelLoader.load(Paths.get("./test.gug"));
        action = new Action(level);
        addLayer(new LevelLayer(level, action));
        addLayer(new LevelUILayer(action));
    }

}
