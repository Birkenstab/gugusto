package input.event;

public class KeyEvent implements InputEvent {

    private char key;
    private int keyCode;
    private int type;

    public KeyEvent(char key, int keyCode, int type){
        this.key = key;
        this.keyCode = keyCode;
        this.type = type;
    }

    @Override
    public int getType() {
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
