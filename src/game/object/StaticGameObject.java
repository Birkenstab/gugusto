package game.object;

import util.Size;
import util.Vector;

/**
 * Gleiches Verhalten wie GameObject
 * Darf seine Position nur in sehr geringem Maße verändern, da es einem Chunk fest zugeordnet ist
 */
public abstract class StaticGameObject extends GameObject {
    public StaticGameObject(Vector position, Size size) {
        super(position, size);
    }
}
