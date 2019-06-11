package game.object.enemies;

import collision.CollisionUtil;
import game.object.DynamicGameObject;
import util.Size;
import util.Vector;

public abstract class Enemy extends DynamicGameObject {

    public Enemy(Vector position, Size size) {
        super(position, size);
    }

    @Override
    public void update(double delta){

    }


}
