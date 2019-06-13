package input.event;

public enum InputEventType {

    KEY_DOWN(0),
    KEY_UP(1),
    KEY_PRESS(2),
    MOUSE_DOWN(3),
    MOUSE_UP(4),
    MOUSE_CLICK(5),
    MOUSE_MOVE(6),
    MOUSE_SCROLL(7);

    private int id;

    InputEventType(int id){
        this.id = id;
    }

    public boolean isKeyEvent(){
        return id < 4;
    }

    public boolean isMouseEvent(){
        return id > 2;
    }

    public int getId(){
        return id;
    }

}
