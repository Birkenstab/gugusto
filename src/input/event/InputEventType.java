package input.event;

public enum InputEventType {

    KEY_DOWN(1),
    KEY_UP(1 << 1),
    KEY_PRESS(1 << 2),
    KEY(KEY_DOWN.id | KEY_UP.id | KEY_PRESS.id),
    MOUSE_DOWN(1 << 3),
    MOUSE_UP(1 << 4),
    MOUSE_CLICK(1 << 5),
    MOUSE_MOVE(1 << 6),
    MOUSE(MOUSE_DOWN.id | MOUSE_UP.id | MOUSE_CLICK.id | MOUSE_MOVE.id);

    private int id;

    InputEventType(int id){
        this.id = id;
    }


    public boolean isKeyEvent(){
        return (id & (KEY_DOWN.id | KEY_UP.id | KEY_PRESS.id)) != 0;
    }

    public boolean isMouseEvent(){
        return (id & (MOUSE_CLICK.id | MOUSE_UP.id | MOUSE_DOWN.id | MOUSE_MOVE.id)) != 0;
    }

    public int getId(){
        return id;
    }

}
