package scene.scenes;

import game.level.Level;
import graphic.GraphicSystem;
import scene.Scene;

public class LevelScene extends Scene {

    private Level level;

    public LevelScene(Level level){
        this.level = level;
        gameObjects.add(level.getPlayer());
    }

    @Override
    public void update(double delta) {
        super.update(delta);
    }

    @Override
    public void draw(GraphicSystem graphicSystem) {
        super.draw(graphicSystem);
    }

}
