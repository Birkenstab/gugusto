package game.object;

public enum Direction {

    NONE,
    LEFT,
    RIGHT,
    UP,
    DOWN,
    HORIZONTAL,
    VERTICAL;

    public static Direction get(char c){
        if(c == 'a') return LEFT;
        if(c == 'd') return RIGHT;
        return NONE;
    }

    public static char get(Direction direction){
        if(direction == LEFT) return 'a';
        if(direction == RIGHT) return 'd';
        return 0;
    }

}
