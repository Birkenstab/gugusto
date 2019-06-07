package scene.scenes.mapeditor;

import game.Camera;
import game.Game;
import game.level.Chunk;
import game.level.Level;
import game.object.blocks.GrassBlock;
import graphic.Window;
import input.MouseState;
import input.event.InputEventType;
import input.event.MouseEvent;
import scene.Layer;
import util.Vector;

import java.awt.*;
import java.util.List;

public class MapEditorLayer extends Layer {

    private Level level;
    private Action action;
    private Camera camera;

    public MapEditorLayer(Level level, Action action){
        this.level = level;
        this.action = action;
        camera = new Camera(level.getCameraStartPosition(32), 32);
        addListener(InputEventType.KEY_DOWN, camera::onKeyDown);

        addGameObjects();
        addListener(InputEventType.MOUSE_MOVE, this::onMouseMove);
        addListener(InputEventType.MOUSE_DOWN, this::onMouseDown);
    }

    private boolean onMouseMove(MouseEvent event){
        if(MouseState.isDown(MouseEvent.BUTTON1) || MouseState.isDown(MouseEvent.BUTTON3)){
            Vector eventCoordinates = camera.toEventCoordinates(new Vector(event.getX(), event.getY()));
            setBlock(camera.toWorldCoordinates(eventCoordinates));
        }

        return false;
    }

    private boolean onMouseDown(MouseEvent event){
        Vector eventCoordinates = camera.toEventCoordinates(new Vector(event.getX(), event.getY()));
        setBlock(camera.toWorldCoordinates(eventCoordinates));

        return false;
    }

    private void setBlock(Vector position){
        if(position.getX() > -1 && position.getY() > -1){
            Vector chunkPosition = position.clone().divide(Chunk.SIZE);

            if(MouseState.isDown(MouseEvent.BUTTON1)) action.primaryAction(position, chunkPosition, gameObjects);
            else if(MouseState.isDown(MouseEvent.BUTTON3)) action.secondaryAction(position, chunkPosition, gameObjects);
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
        drawGrid(g2d);
    }

    private void drawGrid(Graphics2D g2d){
        int gridSize = (int)camera.getScaling();
        Vector o = getGridOffset();
        int oX = (int)o.getX();
        int oY = (int)o.getY();

        for(int x = 0; x < Game.WIDTH; x += gridSize){
            g2d.drawLine(x + oX, oY, x + oX, Game.HEIGHT + oY);

            for(int y = 0; y < Game.HEIGHT; y += gridSize){
                g2d.drawLine(oX, y + oY, Game.WIDTH + oX, y + oY);
            }
        }
    }

    private Vector getGridOffset(){
        Vector offset = new Vector(0, 1);
        Vector pos = camera.getPosition();
        double scaling = camera.getScaling();
        double titlebar = Window.TITLEBAR_HEIGHT / scaling;

        if(pos.getX() < 0){
            offset.setX(-pos.getX() * scaling);
        } else {
            double scrollX = level.getChunkList().getWidth() * Chunk.SIZE - Game.WIDTH / scaling - pos.getX();
            if(scrollX < 0) offset.setX(scrollX * scaling);
        }

        if(pos.getY() + titlebar < 0){
            offset.setY(-(pos.getY() - titlebar) * scaling - 1);
        } else {
            double scrollY = level.getChunkList().getHeight() * Chunk.SIZE - Game.HEIGHT / scaling - pos.getY() + titlebar;
            if(scrollY < 0) offset.setY((scrollY + titlebar) * scaling - 1);
        }

        return offset;
    }

    @Override
    public void update(double delta){

    }

    @Override
    protected Camera getCamera(){
        return camera;
    }


}
