package de.thu.gpro.gugusto.scene.scenes.leveleditor.editor;

import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.game.level.Chunk;
import de.thu.gpro.gugusto.game.object.GameObject;
import de.thu.gpro.gugusto.input.MouseState;
import de.thu.gpro.gugusto.input.event.MouseEvent;
import de.thu.gpro.gugusto.util.Size;
import de.thu.gpro.gugusto.util.Vector;

import java.awt.*;
import java.util.List;

public class LevelEditorMode {

    enum Mode {SINGLE, AREA }

    private LevelEditorAction action;
    private LevelEditorCamera camera;
    private List<GameObject> gameObjects;
    private Mode mode = Mode.SINGLE;

    private int areaButton;
    private boolean areaActive = false;
    private Vector downPosition;
    private Vector drawPosition;
    private Size drawSize;

    LevelEditorMode(LevelEditorAction action, LevelEditorCamera camera, List<GameObject> gameObjects){
        this.action = action;
        this.camera = camera;
        this.gameObjects = gameObjects;
    }

    private void handleNormalMode(Vector position, int button){
        position = camera.toWorldCoordinates(position);
        Vector chunkPosition = position.clone().divide(Chunk.SIZE);

        if(position.getX() >= 0 && position.getY() >= 0){
            if(button == MouseEvent.BUTTON1) action.placeObject(position, chunkPosition, gameObjects);
            else if(button == MouseEvent.BUTTON3) action.removeObject(position, chunkPosition, gameObjects);
        }
    }

    private void updateDrawMetrics(){
        Vector pos = MouseState.getPosition();
        double x1 = downPosition.getX();
        double y1 = downPosition.getY();
        double x2 = pos.getX();
        double y2 = pos.getY();

        if(pos.getX() < x1) x1 = pos.getX();
        if(pos.getY() < y1) y1 = pos.getY();
        if(downPosition.getX() > x2) x2 = downPosition.getX();
        if(downPosition.getY() > y2) y2 = downPosition.getY();

        drawPosition = new Vector(x1, y1).add(Game.WINDOW.getTopLeftInsets());
        drawSize = new Size(x2 - x1, y2 - y1);
    }

    private void calcAreaAndApplyAction(){
        drawPosition.subtract(Game.WINDOW.getTopLeftInsets());
        Vector bPos = camera.toWorldCoordinates(drawPosition);
        Size bSize = camera.toWorldCoordinates(drawSize);
        Vector bSizePos = bPos.clone().add(bSize.toVector()).floor();
        Vector diff = bSizePos.subtract(bPos.clone().floor());

        if(areaButton == MouseEvent.BUTTON1) fillArea(bPos, diff);
        else if(areaButton == MouseEvent.BUTTON3) clearArea(bPos, diff);
    }

    private void fillArea(Vector origin, Vector diff){
        for(int y = 0; y < diff.getY() + 1; y++){
            for(int x = 0; x < diff.getX() + 1; x++){
                Vector position = new Vector(x, y).add(origin);
                Vector chunkPosition = position.clone().divide(Chunk.SIZE);
                if(position.getX() < 0 || position.getY() < 0) continue;
                action.placeObject(new Vector(x, y).add(origin), chunkPosition, gameObjects);
            }
        }
    }

    private void clearArea(Vector origin, Vector diff){
        for(int y = 0; y < diff.getY() + 1; y++){
            for(int x = 0; x < diff.getX() + 1; x++){
                Vector position = new Vector(x, y).add(origin);
                Vector chunkPosition = position.clone().divide(Chunk.SIZE);
                action.removeObject(new Vector(x, y).add(origin), chunkPosition, gameObjects);
            }
        }
    }

    public boolean onMouseMove(MouseEvent event){
        if(mode == Mode.AREA) if(areaActive) updateDrawMetrics();

        return false;
    }

    public boolean onMouseDown(MouseEvent event){
        if(event.getButton() == MouseEvent.BUTTON1 || event.getButton() == MouseEvent.BUTTON3){
            if(mode == Mode.SINGLE){
                handleNormalMode(event.asVector(), event.getButton());
            } else if(mode == Mode.AREA){
                areaActive = true;
                downPosition = event.asVector();
                areaButton = event.getButton();
                updateDrawMetrics();
            }
        }

        return false;
    }

    public boolean onMouseUp(MouseEvent event){
        if((event.getButton() == MouseEvent.BUTTON1 || event.getButton() == MouseEvent.BUTTON3) && areaActive){
            areaActive = false;
            calcAreaAndApplyAction();
        }

        return false;
    }

    public void toggle(){
        if(mode == Mode.SINGLE) mode = Mode.AREA;
        else mode = Mode.SINGLE;
    }

    public Mode getMode(){
        return mode;
    }

    public void setMode(Mode mode){
        this.mode = mode;
    }

    public void draw(Graphics2D g2d){
        if(mode == Mode.AREA && areaActive){
            g2d.setColor(Color.RED);
            g2d.drawRect((int)drawPosition.getX(), (int)drawPosition.getY(), (int)drawSize.getWidth(), (int)drawSize.getHeight());
        }
    }

}
