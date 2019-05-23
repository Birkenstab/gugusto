package scene;

import game.Camera;
import game.level.Level;
import game.level.LevelLoader;
import graphic.GraphicSystem;
import scene.scenes.LevelScene;
import util.Vector;

public class SceneManager {

    private Scene currentScene;
    private Camera camera;

    public SceneManager(){
        Level level = LevelLoader.loadTestLevel();
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
