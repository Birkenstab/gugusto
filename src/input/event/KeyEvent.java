package input.event;

public class KeyEvent implements InputEvent {

    private char key;
    private int keyCode;
    private InputEventType type;

    public KeyEvent(char key, int keyCode, InputEventType type){
        this.key = key;
        this.keyCode = keyCode;
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

    public char getChar(){
        return key;
    }

    public int getKeyCode(){
        return keyCode;
    }

}
