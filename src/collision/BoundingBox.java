package collision;

import util.Size;
import util.Vector;

public class BoundingBox {

    public enum Type {
        RECTANGLE,
        CIRCLE
    };

    private Vector position;
    private double radius;
    private Size size;
    private Type type;

    public BoundingBox(Vector position, Size size){
        this.position = position;
        this.size = size;
        type = Type.RECTANGLE;
    }

    public BoundingBox(Vector position, double radius){
        this.position = position;
        this.radius = radius;
        type = Type.CIRCLE;
    }

    public Vector getPosition(){
        return position;
    }

    public Size getSize(){
        return size;
    }

    public double getRadius(){
        return radius;
    }

    public Type getType(){
        return type;
    }

}
