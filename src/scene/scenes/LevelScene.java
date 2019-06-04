package scene.scenes;

import collision.CollisionUtil;
import game.level.Level;
import game.object.DynamicGameObject;
import game.object.FpsCounter;
import graphic.GraphicSystem;
import scene.Scene;

import java.util.ArrayList;
import java.util.List;

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
        gameObjects.add(new FpsCounter());
    }

    @Override
    public void update(double delta) {
        super.update(delta);
        handleCollisions();
    }

    private void handleCollisions() {
        List<DynamicGameObject> dynamicObjs = new ArrayList<>(level.getEnemys());
        dynamicObjs.add(level.getPlayer());

        for (DynamicGameObject obj : dynamicObjs) {
            obj.setOnGround(false);
            CollisionUtil.handleStaticCollisions(obj, level.getChunkList().getNearby(obj.getBoundingBox().getPosition()));
        }

        CollisionUtil.handleDynamicCollisions(dynamicObjs);
    }



    @Override
    public void draw(GraphicSystem graphicSystem) {
        super.draw(graphicSystem);
    }

}
