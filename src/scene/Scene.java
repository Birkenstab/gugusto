package scene;

import game.object.IGameObject;
import graphic.GraphicSystem;

import java.util.Iterator;
import java.util.List;

public abstract class Scene {

    protected List<IGameObject> gameObjects;

    protected void update(double delta){
        for(Iterator<IGameObject> iter = gameObjects.iterator(); iter.hasNext();){
            IGameObject object = iter.next();
            if(object.shouldBeRemoved()) iter.remove();
            else object.update(delta);
        }
    }
    protected void draw(GraphicSystem graphicSystem){
        graphicSystem.draw(gameObjects);
    }

    public List<IGameObject> getGameObjects(){
        return gameObjects;
    }

}
