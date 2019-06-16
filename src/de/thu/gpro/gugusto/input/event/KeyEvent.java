package de.thu.gpro.gugusto.input.event;

public class KeyEvent implements InputEvent {

    public static final int VK_W = java.awt.event.KeyEvent.VK_W;
    public static final int VK_A = java.awt.event.KeyEvent.VK_A;
    public static final int VK_S = java.awt.event.KeyEvent.VK_S;
    public static final int VK_D = java.awt.event.KeyEvent.VK_D;
    public static final int VK_SPACE = java.awt.event.KeyEvent.VK_SPACE;
    public static final int VK_UP = java.awt.event.KeyEvent.VK_UP;
    public static final int VK_DOWN = java.awt.event.KeyEvent.VK_DOWN;
    public static final int VK_LEFT = java.awt.event.KeyEvent.VK_LEFT;
    public static final int VK_RIGHT = java.awt.event.KeyEvent.VK_RIGHT;
    public static final int VK_ESCAPE = java.awt.event.KeyEvent.VK_ESCAPE;
    public static final int VK_PLUS = java.awt.event.KeyEvent.VK_PLUS;
    public static final int VK_MINUS= java.awt.event.KeyEvent.VK_MINUS;

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
