package input.event;

import util.Vector;

public class MouseEvent implements InputEvent {

    private int x;
    private int y;
    private InputEventType type;
    private int button;

    public MouseEvent(int x, int y, int button, InputEventType type){
        this.x = x;
        this.y = y;
        this.button = button;
        this.type = type;
    }

    @Override
    public InputEventType getType() {
        return type;
    }

    @Override
    public boolean isConsumed() {
        return false;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getButton(){
        return button;
    }

}
