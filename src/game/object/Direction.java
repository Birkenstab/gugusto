package game.object;

public enum Direction {

    NONE,
    LEFT,
    RIGHT,
    UP,
    DOWN;

    public static Direction get(char c){
        if(c == 'w') return UP;
        if(c == 's') return DOWN;
        if(c == 'a') return LEFT;
        if(c == 'd') return RIGHT;
        return NONE;
    }

    public static char get(Direction direction){
        if(direction == UP) return 'w';
        if(direction == DOWN) return 's';
        if(direction == LEFT) return 'a';
        if(direction == RIGHT) return 'd';
        return 0;
    }

}
