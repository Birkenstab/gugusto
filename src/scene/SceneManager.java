package scene;

import game.Camera;
import game.level.Level;
import graphic.GraphicSystem;
import scene.scenes.LevelScene;
import scene.scenes.StartMenuScene;
import util.Vector;

public class SceneManager {

    private Scene currentScene;
    private Camera camera;

    public SceneManager(){
        Level level = new Level("Test", null, new Vector(100, 100));
        currentScene = new LevelScene(level);
        camera = new Camera(new Vector());
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
