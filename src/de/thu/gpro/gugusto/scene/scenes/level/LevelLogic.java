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
    private boolean gameEnded = false;
    private double updateTimeSum = 0;
    private int updateTimeCount = 0;
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
        double time = System.nanoTime();
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
            if (!gameEnded && !level.getPlayer().isAlive()) {
                gameEnded = true;
                levelAction.endLevelByDeath();
            }
        }
        debugInfo();
        updateTimeSum += (System.nanoTime() - time) / 1e3;
        updateTimeCount++;
        if (updateTimeCount >= 30) {
            DebugInfo.avgLevelLogicUpdateTime = updateTimeSum / updateTimeCount;
            updateTimeCount = 0;
            updateTimeSum = 0;
        }
    }

    public void draw(Graphics2D g2d) {
        for(GameObject object : activeDynamicGameObjects){
            doDraw(g2d, object);
        }

        List<Chunk> visibleChunks = getVisibleChunks();
        DebugInfo.visibleChunks = visibleChunks.size();

        for(Chunk chunk : visibleChunks){
            for (Block block : chunk.getBlocks()) {
                doDraw(g2d, block);
            }
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
        BoundingBox screenBoundingBox = new BoundingBox(camera.getPosition(), new Size(Game.INNER_WIDTH / camera.getScaling(),  Game.INNER_HEIGHT / camera.getScaling()));
        for (Iterator<DynamicGameObject> iterator = inactiveDynamicGameObjects.iterator(); iterator.hasNext(); ) {
            DynamicGameObject obj = iterator.next();
            if (CollisionUtil.isColliding(obj.getBoundingBox(), screenBoundingBox)) { // Ist auf dem Bildschirm sichtbar
                iterator.remove();
                activeDynamicGameObjects.add(obj);
            }
        }
    }

    private void handleCollisions() {
        DebugInfo.checkedStaticCollisions = 0;
        DebugInfo.checkedDynamicCollisions = 0;
        DebugInfo.occurredStaticCollisions = 0;
        DebugInfo.occurredDynamicCollisions = 0;

        for (DynamicGameObject obj : activeDynamicGameObjects) {
            obj.setOnGround(false);
            CollisionUtil.handleStaticCollisions(obj, level.getChunkList().getNearby(obj.getBoundingBox().getPosition()));
        }

        CollisionUtil.handleDynamicCollisions(activeDynamicGameObjects);
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
            double offsetX = Game.INNER_WIDTH - diff.getX() - playerSize.getWidth();
            if(offsetX < border.getX()) offset.setX(border.getX() - offsetX);
        }

        if(diff.getY() < border.getY()){
            offset.setY(diff.getY() - border.getY());
        } else {
            double offsetY = Game.INNER_HEIGHT - diff.getY() - playerSize.getHeight();
            if(offsetY < border.getY()) offset.setY(border.getY() - offsetY);
        }

        camera.move(offset.divide(camera.getScaling()));
        int levelHeight = level.getChunkList().getHeight() * Chunk.SIZE;
        if (camera.toWorldCoordinates(new Vector(0, Game.INNER_HEIGHT)).getY() > levelHeight) { // Camera soll keinen Bereich unter dem untersten Chunk zeigen
            camera.set(new Vector(camera.getPosition().getX(), (levelHeight * camera.getScaling() - Game.INNER_HEIGHT) / camera.getScaling()));
        }
    }

    private void handleOutOfWorld() {
        activeDynamicGameObjects.forEach(gameObject -> {
            if (gameObject.getBoundingBox().getPosition().getY() > level.getChunkList().getHeight() * Chunk.SIZE) {
                gameObject.kill();
            }
        });
    }

    private List<Chunk> getVisibleChunks() {
        List<Chunk> list = new ArrayList<>();
        Vector topLeft = camera.toWorldCoordinates(new Vector(0, 0));
        Vector bottomRight = camera.toWorldCoordinates(new Vector(Game.INNER_WIDTH, Game.INNER_HEIGHT));

        int x1 = Chunk.getChunkNo(topLeft.getX());
        int x2 = Chunk.getChunkNo(bottomRight.getX());
        int y1 = Chunk.getChunkNo(topLeft.getY());
        int y2 = Chunk.getChunkNo(bottomRight.getY());

        for (int x = x1; x <= x2; x++) {
            for (int y = y1; y <= y2; y++) {
                Chunk chunk = level.getChunkList().get(x, y);
                if (chunk != null)
                    list.add(chunk);
            }
        }

        return list;
    }

    public Camera getCamera() {
        return camera;
    }
}
