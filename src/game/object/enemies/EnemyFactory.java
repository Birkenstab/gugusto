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
            case GUSTAV: return new Gustav(position);
        }

        throw new Error("\nSir, I'm afraid to inform you that I do not know the Enemy '" + type + "' and therefore cannot create an instance of its type\nIm sorry!\n\nSincerely EnemyFactory\n");

    }


}
