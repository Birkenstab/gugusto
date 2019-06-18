package de.thu.gpro.gugusto.collision;

import de.thu.gpro.gugusto.game.level.Chunk;
import de.thu.gpro.gugusto.game.object.DynamicGameObject;
import de.thu.gpro.gugusto.game.object.GameObject;
import de.thu.gpro.gugusto.util.DebugInfo;
import de.thu.gpro.gugusto.util.Vector;
import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.util.Size;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CollisionUtil {

    private CollisionUtil(){}

    /**
     * Check the dynamic GameObject against every StaticGameObject in the chunks provided
     * @param dynamicObj
     * @param chunks
     */
    public static void handleStaticCollisions(DynamicGameObject dynamicObj, List<Chunk> chunks, List<Collision> collisions) {
        for (Chunk chunk : chunks) {
            for (GameObject other : chunk.getBlocks()) {
                DebugInfo.checkedStaticCollisions++;
                double surf = CollisionUtil.surfRectToRect(dynamicObj.getBoundingBox(), other.getBoundingBox());
                if (surf > 0) {
                    collisions.add(new Collision(surf, dynamicObj, other));
                }
            }
        }
        DebugInfo.occurredStaticCollisions = collisions.size();
    }

    /**
     * Checks for collisions between those GameObjects and calls the collision method of both collision partners if there is a collision
     * @param objs
     */
    public static void handleDynamicCollisions(List<DynamicGameObject> objs, List<Collision> collisions) {
        for (int i = 0; i < objs.size(); i++) {
            for (int j = 0; j < objs.size(); j++) {
                if (i < j) {
                    GameObject x = objs.get(i);
                    GameObject y = objs.get(j);
                    DebugInfo.checkedDynamicCollisions++;
                    double surf = CollisionUtil.surfRectToRect(x.getBoundingBox(), y.getBoundingBox());
                    if (surf > 0) {
                        collisions.add(new Collision(surf, x, y));
                    }
                }
            }
        }
        DebugInfo.occurredDynamicCollisions = collisions.size();
    }

    public static void callCollisions(List<Collision> collisions) {
        collisions.sort(Collections.reverseOrder(Comparator.comparingDouble(o -> o.surface)));
        for (Collision collision : collisions) {
            collision.obj1.collision(collision.obj2);
            collision.obj2.collision(collision.obj1);
        }
    }

    public static boolean isColliding(GameObject obj1, GameObject obj2) {
        return isColliding(obj1.getBoundingBox(), obj2.getBoundingBox());
    }

    public static boolean isColliding(BoundingBox bb1, BoundingBox bb2){
        return getCollisionSurface(bb1, bb2) > 0;
    }

    public static double getCollisionSurface(GameObject obj1, GameObject obj2) {
        return getCollisionSurface(obj1.getBoundingBox(), obj2.getBoundingBox());
    }

    public static double getCollisionSurface(BoundingBox bb1, BoundingBox bb2) {
        if (bb1.getType() == BoundingBox.Type.RECTANGLE) {
            if (bb2.getType() == BoundingBox.Type.RECTANGLE)
                return surfRectToRect(bb1, bb2);
            else if (bb2.getType() == BoundingBox.Type.CIRCLE)
                return surfRectToCircle(bb1, bb2);
        } else if (bb1.getType() == BoundingBox.Type.CIRCLE) {
            if (bb2.getType() == BoundingBox.Type.RECTANGLE)
                return surfRectToCircle(bb2, bb1);
            else if (bb2.getType() == BoundingBox.Type.CIRCLE)
                return surfRectToRect(bb1, bb2);
        }

        throw new Error("Cannot check collisions of these BoundingBox types " + bb1.getType() + " and " + bb2.getType());
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

        if(nx < 0 || fx > Game.INNER_WIDTH) return -1;
        if(ny < 0 || fy > Game.INNER_HEIGHT) return 1;

        return 0;
    }

    private static double surfRectToRect(BoundingBox bb1, BoundingBox bb2) {
        Vector pos1 = bb1.getPosition();
        Vector pos2 = bb2.getPosition();
        Size size1 = bb1.getSize();
        Size size2 = bb2.getSize();

        double x1 = pos1.getX();
        if (x1 < pos2.getX() || x1 > pos2.getX() + size2.getWidth())
            x1 = pos2.getX();
        if (x1 < pos1.getX() || x1 > pos1.getX() + size1.getWidth())
            return 0;

        double x2 = pos1.getX() + size1.getWidth();
        if (x2 < pos2.getX() || x2 > pos2.getX() + size2.getWidth())
            x2 = pos2.getX() + size2.getWidth();
        if (x2 < pos1.getX() || x2 > pos1.getX() + size1.getWidth())
            return 0;


        double y1 = pos1.getY();
        if (y1 < pos2.getY() || y1 > pos2.getY() + size2.getHeight())
            y1 = pos2.getY();
        if (y1 < pos1.getY() || y1 > pos1.getY() + size1.getHeight())
            return 0;

        double y2 = pos1.getY() + size1.getHeight();
        if (y2 < pos2.getY() || y2 > pos2.getY() + size2.getHeight())
            y2 = pos2.getY() + size2.getHeight();
        if (y2 < pos1.getY() || y2 > pos1.getY() + size1.getHeight())
            return 0;


        return (x2 - x1) * (y2 - y1);
    }

    private static double surfRectToCircle(BoundingBox rect, BoundingBox circle) {
        // TODO
        throw new Error("Not implemented");
    }

    private static double surfCircleToCircle(BoundingBox obj1, BoundingBox obj2) {
        // TODO
        throw new Error("Not implemented");
    }

    /*private static boolean circleToCircle(BoundingBox bb1, BoundingBox bb2){
        double diffX = bb1.getPosition().getX() - bb2.getPosition().getX();
        double diffY = bb1.getPosition().getY() - bb2.getPosition().getY();
        double diff = Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2));

        return diff < bb1.getRadius() + bb2.getRadius();
    }*/

    public static boolean contains(Vector point, BoundingBox bb){
        if(bb.getType() == BoundingBox.Type.RECTANGLE) return isPointInRect(point, bb);
        else return isPointInCircle(point, bb);
    }

    public static boolean isPointInCircle(Vector point, BoundingBox bb){
        double diffX = point.getX() - bb.getPosition().getX();
        double diffY = point.getY() - bb.getPosition().getY();
        double diff = Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2));

        return diff < bb.getRadius();
    }

    public static boolean isPointInRect(Vector point, BoundingBox bb){
        Vector position = bb.getPosition();
        Size size = bb.getSize();

        return point.getX() >= position.getX() &&
                point.getX() <= position.getX() + size.getWidth() &&
                point.getY() >= position.getY() &&
                point.getY() <= position.getY() + size.getHeight();
    }

    public static class Collision {
        private double surface;
        private GameObject obj1;
        private GameObject obj2;

        public Collision(double surface, GameObject obj1, GameObject obj2) {
            this.surface = surface;
            this.obj1 = obj1;
            this.obj2 = obj2;
        }
    }
}
