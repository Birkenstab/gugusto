package de.thu.gpro.gugusto.ui.icon;

import de.thu.gpro.gugusto.util.Vector;

public enum Icon {

    EMPTY(0, 0),
    SPEAKER_ON(2, 0),
    AVATAR(4, 0),
    ARROW_RIGHT(6, 0),
    QUESTION_MARK(8, 0),
    TIMER(10, 0),

    ARROW_HEAD_RIGHT(0, 1),
    SPEAKER_OFF(2, 1),
    FRIENDS(4, 1),
    ARROW_LEFT(6, 1),
    ARROW_HORIZONTAL(8, 1),
    OPEN_DOOR(10, 1),

    ARROW_HEAD_LEFT(0, 2),
    RELOAD(2, 2),
    INFO(4, 2),
    ARROW_UP(6, 2),
    ARROW_VERTICAL(8, 2),
    CHECK(10, 2),

    ARROW_HEAD_UP(0, 3),
    HOME(2, 3),
    MENU(4, 3),
    ARROW_DOWN(6, 3),
    ADD(8, 3),
    STAR(10, 3),

    ARROW_HEAD_DOWN(0, 4),
    SHOPPING_CART(2, 4),
    LOCK(4, 4),
    SETTINGS(6, 4),
    REMOVE(8, 4),
    TRASH(10, 4),

    PAUSE(0, 5),
    MUSIC(2, 5),
    PRICE(4, 5),
    OVERVIEW(6, 5),
    CLOSE(8, 5),
    INFO_CIRCLE(10, 5);


    public static final int DEFAULT_DRAW_SIZE = 64;
    public static final int SIZE = 128;
    private Vector position;

    Icon(int x, int y){
        position = new Vector(x, y).multiply(SIZE);
    }

    public Vector getPosition(){
        return position;
    }

    public static HoverIcon get(Icon icon){
        return IconUtil.getIcon(icon);
    }

}
