package scene.scenes;

import game.level.Level;
import graphic.GraphicSystem;
import scene.Scene;

public class LevelScene extends Scene {

    private Level level;

    public LevelScene(Level level){
        this.level = level;
        gameObjects.add(level.getPlayer());
        for (int x = 0; x < level.getChunkList().getWidth(); x++) {
            for (int y = 0; y < level.getChunkList().getHeight(); y++) {
                gameObjects.addAll(level.getChunkList().get(x, y).getGameObjects());
            }
        }
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
