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
import de.thu.gpro.gugusto.game.object.player.AiPlayer;
import de.thu.gpro.gugusto.game.object.player.WinState;
import de.thu.gpro.gugusto.graphic.Texture;
import de.thu.gpro.gugusto.graphic.TextureLoader;
import de.thu.gpro.gugusto.input.KeyState;
import de.thu.gpro.gugusto.input.event.EventCallback;
import de.thu.gpro.gugusto.input.event.InputEventType;
import de.thu.gpro.gugusto.input.event.KeyEvent;
import de.thu.gpro.gugusto.util.BackgroundUtil;
import de.thu.gpro.gugusto.util.Size;
import de.thu.gpro.gugusto.util.Vector;
import de.thu.gpro.gugusto.util.DebugInfo;

import java.awt.*;
import java.awt.geom.AffineTransform;
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
    private List<Chunk> visibleChunks = new ArrayList<>();
    private boolean showBoundingBoxes = false;
    private boolean aiMode;


    public LevelLogic(Level level, LevelAction levelAction, boolean aiMode) {
        this.level = level;
        this.levelAction = levelAction;
        this.aiMode = aiMode;
        camera = new Camera(level.getCameraStartPosition(32), 32);
        if (aiMode) {
            level.setPlayer(new AiPlayer(level.getPlayer().getBoundingBox().getPosition()));
        }
        addGameObjects();
    }

    public void pause() {
        running = false;
    }

    public void resume() {
        running = true;
    }

    public void update(double delta) {
        visibleChunks = getVisibleChunks();
        DebugInfo.visibleChunks = visibleChunks.size();

        if (KeyState.isDown('y')) // TODO Nur zum Debugging
            delta /= 10;
        double time = System.nanoTime();
        if (running) {
            handleActivations();

            for (Chunk chunk : visibleChunks) {
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
            if (!gameEnded) {
                if (!level.getPlayer().isAlive()) {
                    gameEnded = true;
                    if (aiMode)
                        levelAction.restartLevel();
                    else
                        levelAction.endLevelByDeath();
                }
                if (level.getPlayer().getWinState() == WinState.JUMP) {
                    if (aiMode)
                        levelAction.restartLevel();

                } else if (level.getPlayer().getWinState() == WinState.POST_ANIMATION) {
                    gameEnded = true;
                    if (aiMode)
                        levelAction.restartLevel();
                    else
                        levelAction.endLevelByWin();
                }
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
        BackgroundUtil.drawBackground(g2d, camera.getPosition());


        for(GameObject object : activeDynamicGameObjects){
            doDraw(g2d, object);
        }

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

        if (showBoundingBoxes) {
            Vector position = camera.toScreenCoordinates(gameObject.getBoundingBox().getPosition().clone().add(gameObject.getDrawBoundingBox().getPosition()));
            Size size = camera.toScreenCoordinates(gameObject.getDrawBoundingBox().getSize());

            Vector collPos = camera.toScreenCoordinates(gameObject.getBoundingBox().getPosition());
            Size collSize = camera.toScreenCoordinates(gameObject.getBoundingBox().getSize());
            g2d.setColor(Color.BLACK);
            g2d.drawRect((int) (collPos.getX() + Game.WINDOW.getTopLeftInsets().getX()), (int) (collPos.getY() + Game.WINDOW.getTopLeftInsets().getY()), (int) collSize.getWidth(), (int) collSize.getHeight());

            g2d.setColor(Color.CYAN);
            g2d.drawRect((int) (position.getX() + Game.WINDOW.getTopLeftInsets().getX()), (int) (position.getY() + Game.WINDOW.getTopLeftInsets().getY()), (int) size.getWidth(), (int) size.getHeight());
        }
    }

    private void doUpdate(double delta, GameObject gameObject) {
        if(!gameObject.shouldBeRemoved())
            gameObject.update(delta);
    }

    private void handleRemovals() {
        activeDynamicGameObjects.removeIf(GameObject::shouldBeRemoved);
        for (List<Chunk> chunks : level.getChunkList().getChunks()) {
            for (Chunk chunk : chunks)
                chunk.getBlocks().removeIf(Block::shouldBeRemoved);
        }
    }

    public boolean onKeyDown(KeyEvent event) {
        if(event.getChar() == 'b'){
            showBoundingBoxes = !showBoundingBoxes;
            return true;
        }
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

        List<CollisionUtil.Collision> collisions = new ArrayList<>();

        for (DynamicGameObject obj : activeDynamicGameObjects) {
            obj.setOnGround(false);
            CollisionUtil.handleStaticCollisions(obj, level.getChunkList().getNearby(obj.getBoundingBox().getPosition()), collisions);
        }

        CollisionUtil.handleDynamicCollisions(activeDynamicGameObjects, collisions);
        CollisionUtil.callCollisions(collisions);
    }

    private void updateCamera(){
        Vector border = new Vector(500, 250);

        Vector playerPos = camera.toScreenCoordinates(level.getPlayer().getBoundingBox().getPosition());
        Size playerSize = camera.toScreenCoordinates(level.getPlayer().getBoundingBox().getSize());
        Vector offset = new Vector();

        if(playerPos.getX() < border.getX()){
            offset.setX(playerPos.getX() - border.getX());
        } else {
            double offsetX = Game.INNER_WIDTH - playerPos.getX() - playerSize.getWidth();
            if(offsetX < border.getX()) offset.setX(border.getX() - offsetX);
        }

        if(playerPos.getY() < border.getY()){
            offset.setY(playerPos.getY() - border.getY());
        } else {
            double offsetY = Game.INNER_HEIGHT - playerPos.getY() - playerSize.getHeight();
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

    public Level getLevel() {
        return level;
    }
}
