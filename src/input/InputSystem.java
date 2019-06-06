package input;

import game.Game;
import graphic.Window;
import input.event.*;

import java.util.ArrayList;
import java.util.List;

public class InputSystem {

    private List<InputEvent> events;
    private Window window;

    public InputSystem(Window window){
        events = new ArrayList<>();
        this.window = window;

        init();
    }

    private void init(){
        window.setMouseListener(event -> events.add(event));
        window.setKeyListener(event -> events.add(event));
    }

    public void dispatch(){
        List<InputEvent> _events = events;
        events = new ArrayList<>();

        for(InputEvent event : _events){
            if(event.getType().isKeyEvent()) dispatchEvent(event);
            else if(event.getType().isMouseEvent()) dispatchEvent(event);
        }

        Game.getInstance().getScene().dispatchEvents(_events);
    }

    private <T extends InputEvent> void dispatchEvent(T event){
        InputEventType type = event.getType();
        if(type == InputEventType.KEY_UP || type == InputEventType.KEY_DOWN){
            KeyState.set(((KeyEvent) event).getKeyCode(), type.equals(InputEventType.KEY_DOWN));
        }

        if(type.equals(InputEventType.MOUSE_DOWN) || type.equals(InputEventType.MOUSE_UP)){
            MouseState.set(((MouseEvent)event).getButton(), type.equals(InputEventType.MOUSE_DOWN));
        }

        if(type.isMouseEvent()) MouseState.set(((MouseEvent)event).getX(), ((MouseEvent)event).getY());
    }

}
