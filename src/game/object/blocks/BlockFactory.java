package game.object.blocks;

import util.Vector;

public final class BlockFactory {

    private BlockFactory(){}

    public static Block create(int id, Vector position){
        return create(BlockType.get(id), position);
    }

    public static Block create(BlockType type, Vector position){
        switch (type){
            case NONE: return null;
            case GRASS: return new GrassBlock(position);
            case GOAL: return new GoalBlock(position);
            case COIN: return new Coin(position);
        }

        return null;
    }

}
