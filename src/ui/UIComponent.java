package ui;

import collision.CollisionUtil;
import game.object.GameObject;
import util.Size;
import util.Vector;

public abstract class UIComponent extends GameObject {

    public UIComponent(Vector position, Size size) {
        super(position, size);
    }

    public boolean contains(Vector point){
        return CollisionUtil.contains(point, boundingBox);
    }

}
