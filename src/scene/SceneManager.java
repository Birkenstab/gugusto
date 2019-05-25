package scene;

import game.Camera;
import game.level.Level;
import game.level.LevelLoader;
import graphic.GraphicSystem;
import scene.scenes.LevelScene;
import util.Vector;

import java.nio.file.Paths;

public class SceneManager {

    private Scene currentScene;
    private Camera camera;

    public SceneManager(){
        LevelLoader levelLoader = new LevelLoader();
        Level level = levelLoader.load(Paths.get("./test.gug"));

        camera = new Camera(new Vector());
        currentScene = new LevelScene(level);
    }

    public void update(double delta){
        currentScene.update(delta);
    }

    public void draw(GraphicSystem graphicSystem){
        currentScene.draw(graphicSystem);
    }

    public void setScene(Scene scene){
        camera.set(new Vector());
        currentScene = scene;
    }

    public Scene getScene(){
        return currentScene;
    }

    public Camera getCamera(){
        return camera;
    }

}
