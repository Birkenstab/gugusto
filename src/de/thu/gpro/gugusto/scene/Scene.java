package de.thu.gpro.gugusto.scene;

import de.thu.gpro.gugusto.graphic.GraphicSystem;
import de.thu.gpro.gugusto.input.event.InputEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public abstract class Scene {

    private List<Layer> layers;

    protected Scene(){
        layers = new ArrayList<>();
    }

    protected void update(double delta){
        for(Layer layer : layers) layer.update(delta);
    }

    protected void draw(GraphicSystem graphicSystem){
        graphicSystem.draw(layers);
    }

    public void dispatchEvents(Queue<InputEvent> events){
        InputEvent event;
        while ((event = events.poll()) != null) {
            for(int i = layers.size() - 1; i >= 0; i--){
                if(layers.get(i).dispatchEvent(event)) break;
            }
        }
    }

    protected void addLayer(Layer layer){
        layers.add(layer);
    }

}
