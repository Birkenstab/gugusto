package de.thu.gpro.gugusto.game.object.enemies;


import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum EnemyType {

    NONE(0),
    SAW(1),
    GUSTAV(2),
    VERFOLGI(3),
    MOVING_PLATFORM(4);

    private static final Map<Integer, EnemyType> map = new HashMap<>();

    static {
        for(EnemyType type : EnumSet.allOf(EnemyType.class)) map.put(type.id, type);
    }

    private int id;

    EnemyType(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public static EnemyType get(int id){
        return map.get(id);
    }

}
