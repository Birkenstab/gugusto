package de.thu.gpro.gugusto.graphic;

import de.thu.gpro.gugusto.scene.Layer;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.List;

public class GraphicSystem {

    private de.thu.gpro.gugusto.graphic.Window window;
    private BufferStrategy bs;

    public GraphicSystem(de.thu.gpro.gugusto.graphic.Window window){
        this.window = window;
        bs = window.getBufferStrategy();
    }

    public void draw(List<Layer> layers) {
        do {
            Graphics2D g2d = (Graphics2D) bs.getDrawGraphics();

            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, window.getWidth(), window.getHeight());

            for(Layer layer : layers){
                layer.draw(g2d);
            }

            g2d.dispose();
        } while(bs.contentsRestored());

        bs.show();
    }

    public Window getWindow(){
        return window;
    }

}
