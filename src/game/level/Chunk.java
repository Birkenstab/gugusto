package game.level;

import game.object.blocks.Block;
import util.Vector;

import java.util.Iterator;
import java.util.List;

public class Chunk {

    public static final int SIZE = 32;

    /**
     * Gibt den Chunk zurück, in dem sich die Position befindet
     * @param position
     * @return
     */
    public static int getChunkNo(double position) {
        double no = position / Chunk.SIZE;
        return (int)Math.floor(no);
    }

    /**
     * Gibt innerhalb des Chunks die Position zurück (relativ zur oberen linken Ecke des Chunks)
     * @param position
     * @return
     */
    public static double getPositionInChunk(double position) {
        double mod = position % Chunk.SIZE;
        if (mod >= 0)
            return mod;
        return mod + Chunk.SIZE;
    }

    private List<Block> blocks;

    public Chunk(List<Block> blocks) {
        this.blocks = blocks;
    }

    public List<Block> getBlocks(){
        return blocks;
    }

    public boolean addBlock(Block newBlock){
        for(Block block : blocks){
            if(block.getBoundingBox().getPosition().equals(newBlock.getBoundingBox().getPosition())) return false;
        }
        blocks.add(newBlock);

        return true;
    }

    public Block removeBlock(Vector position){
        for(Iterator<Block> iter = blocks.iterator(); iter.hasNext();){
            Block block = iter.next();

            if(block.getBoundingBox().getPosition().equals(position)){
                iter.remove();
                return block;
            }
        }

        return null;
    }

}
