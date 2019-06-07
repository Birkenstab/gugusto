package scene.scenes.level;

import collision.BoundingBox;
import collision.CollisionUtil;
import game.Camera;
import game.Game;
import game.level.Chunk;
import game.level.Level;
import game.object.DynamicGameObject;
import input.event.InputEventType;
import scene.Layer;
import util.Size;
import util.Vector;

import java.util.ArrayList;
import java.util.List;

public class LevelLayer extends Layer {

    private Level level;
    private Action action;
    private Camera camera;

    public LevelLayer(Level level, Action action){
        this.level = level;
        this.action = action;

        camera = new Camera(level.getCameraStartPosition(32), 32);

        action.setCamera(camera);
        addListener(InputEventType.KEY_DOWN, camera::onKeyDown);
        addGameObjects();
    }

    private void addGameObjects(){
        gameObjects.add(level.getPlayer());

        for(List<Chunk> chunks : level.getChunkList().getChunks()){
            for(Chunk chunk : chunks) gameObjects.addAll(chunk.getBlocks());
        }
    }

    @Override
    public void update(double delta) {
        super.update(delta);
        handleCollisions();
        updateCamera();
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

    private void updateCamera(){
        Vector playerPos = camera.toScreenCoordinates(level.getPlayer().getBoundingBox().getPosition());
        Size playerSize = camera.toScreenCoordinates(level.getPlayer().getBoundingBox().getSize());
        Vector diff = playerPos.subtract(camera.getPosition());
        Vector border = new Vector(100, 200);
        Vector offset = new Vector();

        if(diff.getX() < border.getX()){
            offset.setX(diff.getX() - border.getX());
        } else {
            double offsetX = Game.WIDTH - diff.getX() - playerSize.getWidth();
            if(offsetX < border.getX()) offset.setX(border.getX() - offsetX);
        }

        if(diff.getY() < border.getY()){
            offset.setY(diff.getY() - border.getY());
        } else {
            double offsetY = Game.HEIGHT - diff.getY() - playerSize.getHeight();
            if(offsetY < border.getY()) offset.setY(border.getY() - offsetY);
        }

        camera.move(offset.divide(camera.getScaling()));
    }

    @Override
    protected Camera getCamera(){
        return camera;
    }

}
