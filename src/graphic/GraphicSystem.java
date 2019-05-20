package graphic;

import game.Game;
import game.object.IGameObject;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.List;

public class GraphicSystem implements IGraphicSystem {

    private Window window;
    private BufferStrategy bs;

    public GraphicSystem(){
        init();
    }

    private void init() {
        window = new Window(Game.WIDTH, Game.HEIGHT);
        bs = window.getBufferStrategy();
    }

    @Override
    public void draw(List<IGameObject> gameObjects) {
        do {
            Graphics2D g2d = (Graphics2D) bs.getDrawGraphics();

            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, window.getWidth(), window.getHeight());

            for(IGameObject object : gameObjects) if(!object.shouldBeRemoved()) object.draw(g2d);

            g2d.dispose();
        } while(bs.contentsRestored());

        bs.show();
    }

    public Window getWindow(){
        return window;
    }

}
