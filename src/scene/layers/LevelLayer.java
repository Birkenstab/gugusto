package scene.layers;

import collision.CollisionUtil;
import game.Camera;
import game.level.Chunk;
import game.level.Level;
import game.object.DynamicGameObject;
import game.object.FpsCounter;
import graphic.GraphicSystem;
import input.event.InputEventType;
import scene.Layer;
import util.Vector;

import java.util.ArrayList;
import java.util.List;

public class LevelLayer extends Layer {

    private Camera camera;
    private Level level;

    public LevelLayer(Level level){
        this.level = level;
        camera = new Camera(new Vector(0, 16), 32);
        addListener(InputEventType.KEY_DOWN, camera::onKeyDown);
        addGameObjects();
    }

    private void addGameObjects(){
        gameObjects.add(level.getPlayer());

        for(List<Chunk> chunks : level.getChunkList().getChunks()){
            for(Chunk chunk : chunks) gameObjects.addAll(chunk.getGameObjects());
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
    protected Camera getCamera(){
        return camera;
    }

}
