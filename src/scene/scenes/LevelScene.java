package scene.scenes;

import game.Camera;
import game.level.Level;
import game.level.LevelLoader;
import scene.Scene;
import scene.layers.LevelLayer;
import scene.layers.TestUILayer;
import util.Vector;

public class LevelScene extends Scene {

    public LevelScene(){
        LevelLoader levelLoader = new LevelLoader();
        Level level = LevelLoader.loadTestLevel();
        //Level level = levelLoader.load(Paths.get("./test.gug"));

        addLayer(new LevelLayer(level));
        addLayer(new TestUILayer());
    }

}
