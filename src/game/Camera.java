package game;

import input.event.InputEventType;
import util.Size;
import util.Vector;

import java.awt.event.KeyEvent;

public class Camera {

    private Vector position;
    private double scaling;

    public Camera(Vector position, double scaling){
        this.position = position;
        this.scaling = scaling;

        Game.getInstance().getInputSystem().addListener(InputEventType.KEY_DOWN, this::onKeyDown);
    }

    private void onKeyDown(input.event.KeyEvent e){
        int step = 5;
        if(e.getKeyCode() == KeyEvent.VK_UP) move(new Vector(0, -step));
        else if(e.getKeyCode() == KeyEvent.VK_DOWN) move(new Vector(0, step));
        else if(e.getKeyCode() == KeyEvent.VK_LEFT) move(new Vector(-step, 0));
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT) move(new Vector(step, 0));
    }

    public void set(Vector position){
        this.position = position;
    }

    public void move(Vector offset){
        position.add(offset);
    }

    public Vector toScreenCoordinates(Vector worldCoordinates){
        return worldCoordinates.clone().subtract(position).multiply(scaling).add(new Vector(0, 22)); // 22 dazuzählen, weil sonst der Ursprung in der Titelzeile vom Fenster wäre. Unter Windows evtl. anders
    }

    public Size toScreenCoordinates(Size size) {
        return new Size(size.getWidth() * scaling, size.getHeight() * scaling);
    }

}
