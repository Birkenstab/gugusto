package game.object.enemies;

import collision.CollisionUtil;
import game.object.DynamicGameObject;
import util.Size;
import util.Vector;

public abstract class Enemy extends DynamicGameObject {

    private EnemyType type;

    public Enemy(EnemyType type, Vector position, Size size) {
        super(position, size);
        this.type = type;

        checkForVectorConstructor();
    }

    private void checkForVectorConstructor(){
        try {
            getClass().getConstructor(Vector.class);
        } catch (NoSuchMethodException e) {
            System.err.println("Every Enemy subclass has to have an constructor matching Enemy*(Vector vector).");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public EnemyType getBlockType(){
        return type;
    }

    public int getId(){
        return type.getId();
    }

}
