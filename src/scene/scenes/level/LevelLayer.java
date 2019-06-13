package scene.scenes.level;

import collision.CollisionUtil;
import game.Camera;
import game.Game;
import game.level.Chunk;
import game.level.Level;
import game.object.DynamicGameObject;
import game.object.blocks.Coin;
import game.object.enemies.Enemy;
import game.object.enemies.Saw;
import input.event.InputEventType;
import scene.Layer;
import util.Size;
import util.Vector;

import java.util.ArrayList;
import java.util.List;

public class LevelLayer extends Layer {

    private Level level;
    private LevelAction levelAction;
    private Camera camera;

    public LevelLayer(Level level, LevelAction levelAction){
        this.level = level;
        this.levelAction = levelAction;

        camera = new Camera(level.getCameraStartPosition(32), 32);

        levelAction.setCamera(camera);
        addGameObjects();
        bindListeners();
    }

    private void addGameObjects(){
        level.getEnemys().add(new Saw(new Vector(0, 29)));
        gameObjects.addAll(level.getEnemys());
        gameObjects.add(level.getPlayer());
        level.getChunkList().getChunks().get(0).get(0).addBlock(new Coin(new Vector(2, 26)));

        for(List<Chunk> chunks : level.getChunkList().getChunks()){
            for(Chunk chunk : chunks) gameObjects.addAll(chunk.getBlocks());
        }

    }

    private void bindListeners(){
        addListener(InputEventType.KEY_DOWN, level.getPlayer()::onKeyDown);
        addListener(InputEventType.KEY_UP, level.getPlayer()::onKeyUp);
    }

    @Override
    public void update(double delta) {
        super.update(delta);
        handleCollisions();
        updateCamera();

        if(!level.getPlayer().isAlive()) levelAction.resetLevel();
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
