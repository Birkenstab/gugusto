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
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

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
