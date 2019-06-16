package de.thu.gpro.gugusto.scene.scenes.level;

import de.thu.gpro.gugusto.collision.BoundingBox;
import de.thu.gpro.gugusto.collision.CollisionUtil;
import de.thu.gpro.gugusto.game.Camera;
import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.game.level.Chunk;
import de.thu.gpro.gugusto.game.level.Level;
import de.thu.gpro.gugusto.game.object.DynamicGameObject;
import de.thu.gpro.gugusto.game.object.GameObject;
import de.thu.gpro.gugusto.game.object.blocks.Block;
import de.thu.gpro.gugusto.input.event.KeyEvent;
import de.thu.gpro.gugusto.util.Size;
import de.thu.gpro.gugusto.util.Vector;
import de.thu.gpro.gugusto.util.DebugInfo;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LevelLogic {
    private LevelAction levelAction;
    private Level level;
    private Camera camera;
    private boolean running = true;
    private List<DynamicGameObject> inactiveDynamicGameObjects = new ArrayList<>();
    private List<DynamicGameObject> activeDynamicGameObjects = new ArrayList<>();


    public LevelLogic(Level level, LevelAction levelAction) {
        this.level = level;
        this.levelAction = levelAction;
        camera = new Camera(level.getCameraStartPosition(32), 32);
        addGameObjects();
    }

    public void pause() {
        running = false;
    }

    public void resume() {
        running = true;
    }

    public void update(double delta) {
        if (running) {
            handleActivations();

            for (List<Chunk> chunks : level.getChunkList().getChunks()) {
                for (Chunk chunk : chunks)
                    for (Block block : chunk.getBlocks()) {
                        doUpdate(delta, block);
                    }
            }
            for (GameObject object : activeDynamicGameObjects) {
                doUpdate(delta, object);
            }

            handleRemovals();

            handleCollisions();
            updateCamera();
            handleOutOfWorld();
            if (!level.getPlayer().isAlive()) levelAction.restartLevel();
        }
        debugInfo();
    }

    public void draw(Graphics2D g2d) {
        for(List<Chunk> chunks : level.getChunkList().getChunks()){
            for(Chunk chunk : chunks)
                for (Block block : chunk.getBlocks()) {
                    doDraw(g2d, block);
                }
        }
        for(GameObject object : activeDynamicGameObjects){
            doDraw(g2d, object);
        }
    }

    private void debugInfo() {
        DebugInfo.activeDynamicGameObjects = activeDynamicGameObjects.size();
        DebugInfo.inactiveDynamicGameObjects = inactiveDynamicGameObjects.size();
        DebugInfo.running = running;
        DebugInfo.cameraPos = camera.getPosition();
    }

    private void doDraw(Graphics2D g2d, GameObject gameObject) {
        if(!gameObject.shouldBeRemoved())
            gameObject.draw(g2d, getCamera());
    }

    private void doUpdate(double delta, GameObject gameObject) {
        if(!gameObject.shouldBeRemoved())
            gameObject.update(delta);
    }

    private void handleRemovals() {
        activeDynamicGameObjects.removeIf(GameObject::shouldBeRemoved);
    }

    public boolean onKeyDown(KeyEvent event) {
        return level.getPlayer().onKeyDown(event);
    }

    public boolean onKeyUp(KeyEvent event) {
        return level.getPlayer().onKeyUp(event);
    }

    private void addGameObjects(){
        activeDynamicGameObjects.add(level.getPlayer());
        inactiveDynamicGameObjects.addAll(level.getEnemies());
    }

    private void handleActivations() {
        BoundingBox screenBoundingBox = new BoundingBox(camera.getPosition(), new Size(Game.WIDTH / camera.getScaling(),  Game.HEIGHT / camera.getScaling()));
        for (Iterator<DynamicGameObject> iterator = inactiveDynamicGameObjects.iterator(); iterator.hasNext(); ) {
            DynamicGameObject obj = iterator.next();
            if (CollisionUtil.isColliding(obj.getBoundingBox(), screenBoundingBox)) {
                iterator.remove();
                activeDynamicGameObjects.add(obj);
            }
        }
    }

    private void handleCollisions() {
        List<DynamicGameObject> dynamicObjs = new ArrayList<>(level.getEnemies());
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

    private void handleOutOfWorld() {
        activeDynamicGameObjects.forEach(gameObject -> {
            if (gameObject.getBoundingBox().getPosition().getY() > level.getChunkList().getHeight() * Chunk.SIZE) {
                gameObject.kill();
            }
        });
    }

    public Camera getCamera() {
        return camera;
    }
}
