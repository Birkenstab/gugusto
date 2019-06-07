package scene;

import game.Camera;
import game.object.GameObject;
import input.event.EventCallback;
import input.event.InputEvent;
import input.event.InputEventType;
import util.Vector;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Layer {

    private static final Camera defaultCamera = new Camera(new Vector(), 1);
    private List<List<EventCallback<InputEvent>>> listeners;
    protected List<GameObject> gameObjects;

    protected Layer(){
        gameObjects = new ArrayList<>();
        listeners = new ArrayList<>();
        for(int i = 0; i < 7; i++) listeners.add(new ArrayList<>());
    }

    protected void update(double delta){
        for(Iterator<GameObject> iter = gameObjects.iterator(); iter.hasNext();){
            GameObject object = iter.next();
            if(object.shouldBeRemoved()) iter.remove();
            else object.update(delta);
        }
    }

    public void draw(Graphics2D g2d){
        for(GameObject object : gameObjects){
            if(!object.shouldBeRemoved()) object.draw(g2d, getCamera());
        }
    }

    protected <T extends InputEvent> void addListener(InputEventType type, EventCallback<T> callback){
        List<EventCallback<InputEvent>> list = listeners.get(type.getId());
        if(list != null) list.add((EventCallback<InputEvent>)callback);
    }

    public boolean dispatchEvent(InputEvent event){
        List<EventCallback<InputEvent>> callbacks = listeners.get(event.getType().getId());

        for(EventCallback<InputEvent> callback : callbacks){
            if(callback.callback(event)) return true;
        }

        return false;
    }

    public List<GameObject> getGameObjects(){
        return gameObjects;
    }

    protected Camera getCamera(){
        return defaultCamera;
    }

}
