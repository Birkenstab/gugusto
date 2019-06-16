package de.thu.gpro.gugusto.game;

import de.thu.gpro.gugusto.util.Size;
import de.thu.gpro.gugusto.util.Vector;

public class Camera {

    protected Vector position;
    protected double scaling;

    public Camera(Vector position, double scaling){
        this.position = position;
        this.scaling = scaling;
    }

    public void set(Vector position){
        this.position = position;
    }

    public void move(Vector offset){
        position.add(offset);
    }

    public Vector toScreenCoordinates(Vector worldCoordinates){
        return worldCoordinates.clone().subtract(position).multiply(scaling);
    }

    public Size toScreenCoordinates(Size size) {
        return new Size(size.getWidth() * scaling, size.getHeight() * scaling);
    }

    public Vector toWorldCoordinates(Vector screenCoordinates){
        return screenCoordinates.clone().add(getScaledPosition()).divide(scaling);
    }

    public Vector getPosition(){
        return position;
    }

    public Vector getScaledPosition(){
        return position.clone().multiply(scaling);
    }

    public double getScaling(){
        return scaling;
    }

}
