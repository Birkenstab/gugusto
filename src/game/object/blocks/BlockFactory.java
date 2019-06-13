package game.object.blocks;

import util.Vector;

public final class BlockFactory {

    private BlockFactory(){}

    public static Block createBlock(int id, Vector position){
        return createBlock(BlockType.get(id), position);
    }

    public static Block createBlock(BlockType type, Vector position){
        switch (type){
            case NONE: return null;
            case GRASS: return new GrassBlock(position);
            case GOAL: return new GoalBlock(position);
            case COIN: return new Coin(position);
        }

        return null;
    }

}
