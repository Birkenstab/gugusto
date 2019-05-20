package scene;

import game.object.GameObject;
import graphic.GraphicSystem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Scene {

    protected List<GameObject> gameObjects;

    protected Scene(){
        gameObjects = new ArrayList<>();
    }

    protected void update(double delta){
        for(Iterator<GameObject> iter = gameObjects.iterator(); iter.hasNext();){
            GameObject object = iter.next();
            if(object.shouldBeRemoved()) iter.remove();
            else object.update(delta);
        }
    }
    protected void draw(GraphicSystem graphicSystem){
        graphicSystem.draw(gameObjects);
    }

    public List<GameObject> getGameObjects(){
        return gameObjects;
    }

}
