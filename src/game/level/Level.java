package game.level;

import game.Game;
import game.object.enemies.Enemy;
import game.object.player.Player;
import util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Level {

    private String name;
    private ChunkList chunkList;
    private List<Enemy> enemies;
    private Vector startPosition;
    private Player player;

    public Level(String name, ChunkList chunkList, List<Enemy> enemies, Vector startPosition){
        this.name = name;
        this.chunkList = chunkList;
        this.enemies = enemies;
        this.startPosition = startPosition;
        player = new Player(startPosition.clone());
    }

    public String getName() {
        return name;
    }

    public ChunkList getChunkList() {
        return chunkList;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public Player getPlayer() {
        return player;
    }

    public Vector getStartPosition(){
        return startPosition;
    }

    public Vector getCameraStartPosition(double scale){
        Vector window = new Vector(Game.WIDTH, Game.HEIGHT);
        return startPosition.clone().subtract(window.divide(new Vector(scale * 2.5, scale * 2)));
    }

}
