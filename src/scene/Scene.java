package scene;

import game.Camera;
import game.object.GameObject;
import graphic.GraphicSystem;
import input.event.EventCallback;
import input.event.InputEvent;
import input.event.KeyEvent;
import input.event.MouseEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Scene {

    protected List<Layer> layers;

    protected Scene(){
        layers = new ArrayList<>();
    }

    protected void update(double delta){
        for(Layer layer : layers) layer.update(delta);
    }

    protected void draw(GraphicSystem graphicSystem){
        graphicSystem.draw(layers);
    }

    public void dispatchEvents(List<InputEvent> events){
        for(InputEvent event : events){
            for(int i = layers.size() - 1; i >= 0; i--){
                if(layers.get(i).dispatchEvent(event)) break;
            }
        }
    }

    protected void addLayer(Layer layer){
        layers.add(layer);
    }

}
