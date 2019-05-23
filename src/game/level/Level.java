package game.level;

import game.object.GameObject;
import game.object.Player;
import util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Level {

    private String name;
    private ChunkList chunkList;
    private List<GameObject> enemys;
    private Vector startPosition;
    private Player player;

    public Level(String name, ChunkList chunkList, Vector startPosition){
        this.name = name;
        this.chunkList = chunkList;
        this.startPosition = startPosition;
        enemys = new ArrayList<>();
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

    public Vector getStartPosition(){
        return startPosition;
    }
}
