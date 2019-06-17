package de.thu.gpro.gugusto.input;

import de.thu.gpro.gugusto.graphic.Window;
import de.thu.gpro.gugusto.input.event.InputEvent;
import de.thu.gpro.gugusto.input.event.InputEventType;
import de.thu.gpro.gugusto.input.event.KeyEvent;
import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.input.event.MouseEvent;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class InputSystem {

    private Queue<InputEvent> events;
    private Window window;

    public InputSystem(Window window){
        events = new ConcurrentLinkedQueue<>();
        this.window = window;

        init();
    }

    private void init(){
        window.setMouseListener(event -> events.add(event));
        window.setKeyListener(event -> events.add(event));
    }

    public void dispatch(){

        for(InputEvent event : events){
            if(event.getType().isKeyEvent()) dispatchEvent(event);
            else if(event.getType().isMouseEvent()) dispatchEvent(event);
        }

        Game.getInstance().getSceneManager().getScene().dispatchEvents(events);
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
