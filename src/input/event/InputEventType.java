package input.event;

public final class InputEventType {

    public static int KEY_DOWN = 1;
    public static int KEY_UP = 1 << 2;
    public static int KEY_PRESS = 1 << 3;
    public static int MOUSE_DOWN= 1 << 4;
    public static int MOUSE_UP= 1 << 5;
    public static int MOUSE_CLICK = 1 << 6;
    public static int MOUSE_MOVE= 1 << 7;

    public static boolean isKeyEvent(int type){
        return (type & (KEY_DOWN | KEY_UP | KEY_PRESS)) != 0;
    }

    public static boolean isMouseEvent(int type){
        return (type & (MOUSE_CLICK | MOUSE_UP | MOUSE_DOWN | MOUSE_MOVE)) != 0;
    }

    private InputEventType(){}

}
