package collision;

import game.Game;
import game.object.GameObject;
import util.Size;
import util.Vector;

import java.util.ArrayList;
import java.util.List;

public class CollisionSystem {

    public CollisionSystem(){ }

    public boolean isColliding(GameObject object1, GameObject object2){
        BoundingBox bb1 = object1.getBoundingBox();
        BoundingBox bb2 = object2.getBoundingBox();
        BoundingBox.Type bbt1 = bb1.getType();
        BoundingBox.Type bbt2 = bb2.getType();

        if(bbt1 == BoundingBox.Type.RECTANGLE){
            if(bbt2 == BoundingBox.Type.RECTANGLE) return rectToRect(bb1, bb2);
            else if(bbt2 == BoundingBox.Type.CIRCLE) return rectToCircle(bb1, bb2);
        } else if(bbt1 == BoundingBox.Type.CIRCLE){
            if(bbt2 == BoundingBox.Type.RECTANGLE) return rectToRect(bb2, bb1);
            else if(bbt2 == BoundingBox.Type.CIRCLE) return circleToCircle(bb1, bb2);
        }

        return false;
    }

    public int isCollidingWithScreen(GameObject object){
        double nx, fx;
        double ny, fy;
        BoundingBox bb = object.getBoundingBox();
        Vector bbp = bb.getPosition();
        Size bbs = bb.getSize();

        if(bb.getType() == BoundingBox.Type.RECTANGLE){
            nx = bbp.getX();
            fx = bbp.getX() + bbs.getWidth();
            ny = bbp.getY();
            fy = bbp.getY() + bbs.getHeight();
        } else if(bb.getType() == BoundingBox.Type.CIRCLE){
            nx = bbp.getX() - bb.getRadius();
            fx = bbp.getX() + bb.getRadius();
            ny = bbp.getY() - bb.getRadius();
            fy = bbp.getY() + bb.getRadius();
        } else {
            return 0;
        }

        if(nx < 0 || fx > Game.WIDTH) return -1;
        if(ny < 0 || fy > Game.HEIGHT) return 1;

        return 0;
    }

    private boolean rectToRect(BoundingBox bb1, BoundingBox bb2){
        Vector bbp1 = bb1.getPosition();
        Vector bbp2 = bb2.getPosition();
        Size bbs1 = bb1.getSize();
        Size bbs2 = bb2.getSize();

        return bbp1.getX() < bbp2.getX() + bbs2.getWidth() &&
                bbp1.getX() + bbs1.getWidth() > bbp2.getX() &&
                bbp1.getY() < bbp2.getY() + bbs2.getHeight() &&
                bbp1.getY() + bbs1.getHeight() > bbp2.getY();
    }

    private boolean rectToCircle(BoundingBox bb1, BoundingBox bb2){
        // TODO
        return false;
    }

    private boolean circleToCircle(BoundingBox bb1, BoundingBox bb2){
        double diffX = bb1.getPosition().getX() - bb2.getPosition().getX();
        double diffY = bb1.getPosition().getY() - bb2.getPosition().getY();
        double diff = Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2));

        return diff < bb1.getRadius() + bb2.getRadius();
    }

    public boolean isPointInCircle(Vector point, BoundingBox bb){
        double diffX = point.getX() - bb.getPosition().getX();
        double diffY = point.getY() - bb.getPosition().getY();
        double diff = Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2));

        return diff < bb.getRadius();
    }

    public List<GameObject> getCollisions(GameObject object){
        List<GameObject> collidedObjects = new ArrayList<>();
        List<GameObject> gameObjects = Game.getInstance().getScene().getGameObjects();

        for(GameObject obj : gameObjects){
            if(obj.equals(object)) continue;
            if(isColliding(obj, object)) collidedObjects.add(obj);
        }

        return collidedObjects;
    }

}
