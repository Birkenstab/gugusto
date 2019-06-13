package game.object.enemies;

import util.Vector;

public final class EnemyFactory {

    private EnemyFactory(){}

    public static Enemy create(int id, Vector position){
        return create(EnemyType.get(id), position);
    }

    public static Enemy create(EnemyType type, Vector position){
        switch (type){
            case NONE: return null;
            case SAW: return new Saw(position);
        }

        return null;
    }


}
