package game.level;

import game.object.GameObject;
import game.object.Player;
import util.Vector;

import java.util.List;

public class Level {

    private String name;
    private ChunkList chunkList;
    private List<GameObject> enemys;
    private Player player;

    public Level(String name, ChunkList chunkList, Vector startPosition){
        this.name = name;
        this.chunkList = chunkList;
        player = new Player(startPosition);
    }

    public String getName() {
        return name;
    }

    public ChunkList getChunkList() {
        return chunkList;
    }

    public List<GameObject> getEnemys() {
        return enemys;
    }

    public Player getPlayer() {
        return player;
    }
}
