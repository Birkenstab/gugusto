package de.thu.gpro.gugusto.game.object;

import de.thu.gpro.gugusto.util.Vector;
import de.thu.gpro.gugusto.util.Size;

/**
 * Gleiches Verhalten wie GameObject
 * Darf seine Position nur in sehr geringem Maße verändern, da es einem Chunk fest zugeordnet ist
 */
public abstract class StaticGameObject extends GameObject {
    public StaticGameObject(Vector position, Size size) {
        super(position, size);
    }
}
