package de.thu.gpro.gugusto.scene.scenes.leveleditor.editor;

import de.thu.gpro.gugusto.game.Camera;
import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.game.level.Chunk;
import de.thu.gpro.gugusto.game.level.Level;
import de.thu.gpro.gugusto.game.object.GameObject;
import de.thu.gpro.gugusto.input.MouseState;
import de.thu.gpro.gugusto.input.event.EventCallback;
import de.thu.gpro.gugusto.input.event.InputEventType;
import de.thu.gpro.gugusto.input.event.KeyEvent;
import de.thu.gpro.gugusto.input.event.MouseEvent;
import de.thu.gpro.gugusto.scene.Layer;
import de.thu.gpro.gugusto.util.Vector;

import java.awt.*;
import java.util.List;

class LevelEditorLayer extends Layer {

    private Level level;
    private LevelEditorAction levelEditorAction;
    private LevelEditorCamera camera;
    private boolean showGrid = true;

    LevelEditorLayer(Level level, LevelEditorAction levelEditorAction){
        this(level, levelEditorAction, null);
    }

    LevelEditorLayer(Level level, LevelEditorAction levelEditorAction, LevelEditorCamera camera){
        this.level = level;
        this.levelEditorAction = levelEditorAction;

        if(camera == null) this.camera = new LevelEditorCamera(level.getCameraStartPosition(32), 32);
        else this.camera = camera;

        addGameObjects();
        addListeners();
    }

    private void addListeners(){
        MouseState.set(MouseEvent.BUTTON1, false);
        addListener(InputEventType.MOUSE_MOVE, this::onMouseMove);
        addListener(InputEventType.MOUSE_DOWN, this::onMouseDown);
        addListener(InputEventType.KEY_DOWN, this::onKeyDown);

        addListener(InputEventType.KEY_DOWN, camera::onKeyDown);
        addListener(InputEventType.MOUSE_DOWN, camera::onMouseDown);
        addListener(InputEventType.MOUSE_UP, camera::onMouseUp);
        addListener(InputEventType.MOUSE_MOVE, camera::onMouseMove);
        addListener(InputEventType.MOUSE_SCROLL, camera::onMouseScroll);
    }

    public void removeListeners(){
        removeListener(InputEventType.MOUSE_MOVE, this::onMouseMove);
        removeListener(InputEventType.MOUSE_DOWN, this::onMouseDown);
        removeListener(InputEventType.KEY_DOWN, this::onKeyDown);

        removeListener(InputEventType.KEY_DOWN, camera::onKeyDown);
        removeListener(InputEventType.MOUSE_DOWN, camera::onMouseDown);
        removeListener(InputEventType.MOUSE_UP, camera::onMouseUp);
        removeListener(InputEventType.MOUSE_MOVE, camera::onMouseMove);
        removeListener(InputEventType.MOUSE_SCROLL, camera::onMouseScroll);
    }

    private boolean onKeyDown(KeyEvent event){
        if(event.getChar() == 'g') showGrid = !showGrid;
        return event.getChar() == 'g';
    }

    private boolean onMouseMove(MouseEvent event){
        if(MouseState.isDown(MouseEvent.BUTTON1) || MouseState.isDown(MouseEvent.BUTTON3)){
            setBlock(event.asVector());
        }

        return false;
    }

    private boolean onMouseDown(MouseEvent event){
        setBlock(event.asVector());

        return false;
    }

    private void setBlock(Vector position){
        position = camera.toWorldCoordinates(position);

        if(position.getX() >= 0 && position.getY() >= 0){
            if(MouseState.isDown(MouseEvent.BUTTON1)) levelEditorAction.primaryAction(position, gameObjects);
            else if(MouseState.isDown(MouseEvent.BUTTON3)) levelEditorAction.secondaryAction(position, gameObjects);
        }
    }

    private void addGameObjects(){
        gameObjects.addAll(level.getEnemies());
        gameObjects.add(level.getPlayer());

        for(List<Chunk> chunks : level.getChunkList().getChunks()){
            for(Chunk chunk : chunks) gameObjects.addAll(chunk.getBlocks());
        }
    }

    @Override
    public void draw(Graphics2D g2d){
        super.draw(g2d);

        g2d.setColor(Color.BLACK);
        if(showGrid) drawGrid(g2d);
    }

    private void drawGrid(Graphics2D g2d){
        int gridSize = (int)camera.getScaling();

        Vector pos = new Vector(-camera.getScaledPosition().getX(), -camera.getScaledPosition().getY()).add(Game.WINDOW.getTopLeftInsets());
        int mapHeight = (int)(level.getChunkList().getHeight() * Chunk.SIZE * camera.getScaling());
        int mapWidth = (int)(level.getChunkList().getWidth() * Chunk.SIZE * camera.getScaling());

        for(int x = 0; x < mapWidth + gridSize; x += gridSize){
            Vector vPos = pos.clone().add(new Vector(x, 0));
            g2d.drawLine((int)vPos.getX(), (int)vPos.getY(), (int)(vPos.getX()), (int)(vPos.getY() + mapHeight));

            for(int y = 0; y < mapHeight + gridSize; y += gridSize){
                Vector xPos = pos.clone().add(new Vector(0, y));
                g2d.drawLine((int)xPos.getX(), (int)xPos.getY(), (int)(xPos.getX() + mapWidth), (int)xPos.getY());
            }
        }
    }

    @Override
    public void update(double delta){

    }

    @Override
    protected Camera getCamera(){
        return camera;
    }

}
