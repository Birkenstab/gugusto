package de.thu.gpro.gugusto.game.object.blocks;

import de.thu.gpro.gugusto.game.object.StaticGameObject;
import de.thu.gpro.gugusto.util.Size;
import de.thu.gpro.gugusto.util.Vector;

public abstract class Block extends StaticGameObject {

    private BlockType type;

    public Block(BlockType type, Vector position) {
        this(type, position, true);
    }

    public Block(BlockType type, Vector position, boolean solid) {
        super(position, new Size(1,1));

        this.type = type;
        setSolid(solid);
        checkForVectorConstructor();
    }

    private void checkForVectorConstructor(){
        try {
            getClass().getConstructor(Vector.class);
        } catch (NoSuchMethodException e) {
            System.err.println("Every Block subclass has to have an constructor matching BlockClass(Vector vector).");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @Override
    public void update(double delta){}

    public BlockType getBlockType(){
        return type;
    }

    public int getId(){
        return type.getId();
    }
}
