package game.object.blocks;

import game.object.StaticGameObject;
import util.Size;
import util.Vector;

public abstract class Block extends StaticGameObject {

    private BlockType type;
    private boolean isSolid;

    public Block(BlockType type, Vector position) {
        super(position, new Size(1,1));

        this.type = type;
        isSolid = true;
        checkForVectorConstructor();
    }

    public Block(BlockType type, Vector position, boolean isSolid) {
        super(position, new Size(1,1));

        this.type = type;
        this.isSolid = isSolid;
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

    public boolean isSolid(){
        return isSolid;
    }

}
