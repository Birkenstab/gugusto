package de.thu.gpro.gugusto.scene;

import de.thu.gpro.gugusto.graphic.GraphicSystem;
import de.thu.gpro.gugusto.scene.scenes.startmenu.StartMenuScene;

public class SceneManager {

    private Scene currentScene;

    public SceneManager(){
        currentScene = new StartMenuScene();
    }

    public void update(double delta){
        currentScene.update(delta);
    }

    public void draw(GraphicSystem graphicSystem){
        currentScene.draw(graphicSystem);
    }

    public void setScene(Scene scene){
        currentScene = scene;
    }

    public Scene getScene(){
        return currentScene;
    }

}
