package input;

import graphic.Window;
import input.event.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputSystem {

    private List<InputEvent> events;
    private Map<InputEventType, List<EventCallback<InputEvent>>> listeners;
    private Window window;

    public InputSystem(Window window){
        events = new ArrayList<>();
        listeners = new HashMap<>();
        this.window = window;

        init();
    }

    private void init(){
        window.setMouseListener(event -> events.add(event));
        window.setKeyListener(event -> events.add(event));

        listeners.put(InputEventType.KEY_DOWN, new ArrayList<>());
        listeners.put(InputEventType.KEY_UP, new ArrayList<>());
        listeners.put(InputEventType.KEY_PRESS, new ArrayList<>());
        listeners.put(InputEventType.MOUSE_DOWN, new ArrayList<>());
        listeners.put(InputEventType.MOUSE_UP, new ArrayList<>());
        listeners.put(InputEventType.MOUSE_CLICK, new ArrayList<>());
        listeners.put(InputEventType.MOUSE_MOVE, new ArrayList<>());
    }

    public void dispatch(){
        List<InputEvent> _events = events;
        events = new ArrayList<>();

        for(InputEvent event : _events){
            if(event.getType().isKeyEvent()) dispatchKeyEvent((KeyEvent) event);
            else if(event.getType().isMouseEvent()) dispatchMouseEvent((MouseEvent) event);
        }
    }

    public <T extends InputEvent> void addListener(InputEventType type, EventCallback<T> callback){
        List<EventCallback<InputEvent>> list = listeners.get(type);
        if(list != null) list.add((EventCallback<InputEvent>)callback);
    }

    private <T extends InputEvent> void dispatchEvent(T event, List<EventCallback<T>> callbacks){
        for(EventCallback<T> callback : callbacks){
            InputEventType type = event.getType();
            if(event instanceof KeyEvent){
                if((type.equals(InputEventType.KEY_PRESS))){
                    KeyState.set(((KeyEvent) event).getKeyCode(), type.equals(InputEventType.KEY_DOWN));
                }

                ((EventCallback<KeyEvent>)callback).callback((KeyEvent) event);
            } else if(event instanceof MouseEvent){
                MouseEvent mouseEvent = (MouseEvent)event;
                MouseState.set(mouseEvent.getX(), mouseEvent.getY());

                if(type.equals(InputEventType.MOUSE_DOWN) || type.equals(InputEventType.MOUSE_UP)){
                    MouseState.set(mouseEvent.getButton(), type.equals(InputEventType.MOUSE_DOWN));
                }

                ((EventCallback<MouseEvent>)callback).callback((MouseEvent) event);
            }
        }
    }

    private void dispatchKeyEvent(KeyEvent event){
        if(event.getType().equals(InputEventType.KEY_DOWN)){
            dispatchEvent(event, listeners.get(InputEventType.KEY_DOWN));
        } else if(event.getType().equals(InputEventType.KEY_UP)){
            dispatchEvent(event, listeners.get(InputEventType.KEY_UP));
        } else if(event.getType().equals(InputEventType.KEY_PRESS)){
            dispatchEvent(event, listeners.get(InputEventType.KEY_PRESS));
        }
    }

    private void dispatchMouseEvent(MouseEvent event){
        if(event.getType().equals(InputEventType.MOUSE_DOWN)){
            dispatchEvent(event, listeners.get(InputEventType.MOUSE_DOWN));
        } else if(event.getType().equals(InputEventType.MOUSE_UP)){
            dispatchEvent(event, listeners.get(InputEventType.MOUSE_UP));
        } else if(event.getType().equals(InputEventType.MOUSE_CLICK)){
            dispatchEvent(event, listeners.get(InputEventType.MOUSE_CLICK));
        } else if(event.getType().equals(InputEventType.MOUSE_MOVE)){
            dispatchEvent(event, listeners.get(InputEventType.MOUSE_MOVE));
        }
    }
}
