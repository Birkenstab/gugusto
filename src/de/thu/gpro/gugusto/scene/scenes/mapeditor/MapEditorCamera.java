package de.thu.gpro.gugusto.scene.scenes.mapeditor;

import de.thu.gpro.gugusto.game.Camera;
import de.thu.gpro.gugusto.input.MouseState;
import de.thu.gpro.gugusto.input.event.KeyEvent;
import de.thu.gpro.gugusto.input.event.MouseEvent;
import de.thu.gpro.gugusto.util.Vector;

public class MapEditorCamera extends Camera {

    private Vector lastDownPosition;
    private Vector lastDownCameraPosition;

    public MapEditorCamera(Vector position, double scaling) {
        super(position, scaling);
    }

    public boolean onKeyDown(KeyEvent event){
        int step = 1;
        if(event.getKeyCode() == KeyEvent.VK_W) move(new Vector(0, -step));
        else if(event.getKeyCode() == KeyEvent.VK_S) move(new Vector(0, step));
        else if(event.getKeyCode() == KeyEvent.VK_A) move(new Vector(-step, 0));
        else if(event.getKeyCode() == KeyEvent.VK_D) move(new Vector(step, 0));
        else if(event.getKeyCode() == KeyEvent.VK_PLUS) zoom(-3);
        else if(event.getKeyCode() == KeyEvent.VK_MINUS) zoom(3);

        return false;
    }

    public boolean onMouseDown(MouseEvent event){
        if(event.getButton() == MouseEvent.BUTTON2){
            lastDownPosition = event.asVector();
            lastDownCameraPosition = position.clone();
            return true;
        }

        return false;
    }

    public boolean onMouseUp(MouseEvent event){
        if(event.getButton() == MouseEvent.BUTTON2){
            lastDownPosition = null;
            return true;
        }

        return false;
    }

    public boolean onMouseMove(MouseEvent event){
        if(lastDownPosition != null){
            Vector offset = event.asVector().subtract(lastDownPosition).divide(-scaling);
            position = offset.add(lastDownCameraPosition);
            return true;
        }

        return false;
    }

    public boolean onMouseScroll(MouseEvent event){
        return zoom(event.getUnitsToScroll());
    }

    private boolean zoom(int units){
        if(units > 0 && scaling > 5 || units < 0 && scaling < 35){
            Vector from = MouseState.getPosition().clone().divide(scaling);
            scaling -= units;
            Vector to = MouseState.getPosition().clone().divide(scaling);
            Vector offset = from.subtract(to);
            position.add(offset);
            return true;
        }

        return false;
    }

    public void setScaling(double scaling){
        this.scaling = scaling;
    }

}
