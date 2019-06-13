package scene.scenes.mapeditor;

import game.Camera;
import game.Game;
import game.level.Chunk;
import game.level.Level;
import graphic.Window;
import input.MouseState;
import input.event.EventCallback;
import input.event.InputEventType;
import input.event.KeyEvent;
import input.event.MouseEvent;
import scene.Layer;
import util.Vector;

import java.awt.*;
import java.util.List;

public class MapEditorLayer extends Layer {

    private Level level;
    private MapEditorAction mapEditorAction;
    private MapEditorCamera camera;
    private boolean showGrid = true;

    public MapEditorLayer(Level level, MapEditorAction mapEditorAction){
        this.level = level;
        this.mapEditorAction = mapEditorAction;
        camera = new MapEditorCamera(level.getCameraStartPosition(32), 32);

        addGameObjects();
        addListener(InputEventType.MOUSE_MOVE, this::onMouseMove);
        addListener(InputEventType.MOUSE_DOWN, this::onMouseDown);
        addListener(InputEventType.KEY_DOWN, (EventCallback<KeyEvent>) e -> {
            if(e.getChar() == 'g') showGrid = !showGrid;
            return e.getChar() == 'g';
        });

        addListener(InputEventType.KEY_DOWN, camera::onKeyDown);
        addListener(InputEventType.MOUSE_DOWN, camera::onMouseDown);
        addListener(InputEventType.MOUSE_UP, camera::onMouseUp);
        addListener(InputEventType.MOUSE_MOVE, camera::onMouseMove);
        addListener(InputEventType.MOUSE_SCROLL, camera::onMouseScroll);
    }

    private boolean onMouseMove(MouseEvent event){
        if(MouseState.isDown(MouseEvent.BUTTON1) || MouseState.isDown(MouseEvent.BUTTON3)){
            setBlock(camera.toWorldCoordinates(event.asVector()));
        }

        return false;
    }

    private boolean onMouseDown(MouseEvent event){
        setBlock(camera.toWorldCoordinates(event.asVector()));

        return false;
    }

    private void setBlock(Vector position){
        if(position.getX() > -1 && position.getY() > -1){
            Vector chunkPosition = position.clone().divide(Chunk.SIZE);

            if(MouseState.isDown(MouseEvent.BUTTON1)) mapEditorAction.primaryAction(position, chunkPosition, gameObjects);
            else if(MouseState.isDown(MouseEvent.BUTTON3)) mapEditorAction.secondaryAction(position, chunkPosition, gameObjects);
        }
    }

    private void addGameObjects(){
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
