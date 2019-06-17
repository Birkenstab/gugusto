package de.thu.gpro.gugusto.game.level;

import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.game.object.enemies.Enemy;
import de.thu.gpro.gugusto.game.object.player.Player;
import de.thu.gpro.gugusto.util.Vector;

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
        Vector window = new Vector(Game.INNER_WIDTH, Game.INNER_HEIGHT);
        return startPosition.clone().subtract(window.divide(new Vector(scale * 2.5, scale * 2)));
    }

}
