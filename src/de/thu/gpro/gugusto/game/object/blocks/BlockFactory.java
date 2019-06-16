package de.thu.gpro.gugusto.game.object.blocks;

import de.thu.gpro.gugusto.util.Vector;

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

        throw new Error("\nSir, I'm afraid to inform you that I do not know the block '" + type + "' and therefore cannot create an instance of it\nIm sorry!\n\nSincerely BlockFactory\n");
    }

}
