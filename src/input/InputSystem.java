package input;

import graphic.Window;
import input.event.*;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputSystem {

    private List<InputEvent> events;
    private Map<Integer, List<EventCallback<InputEvent>>> listeners;
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
            if(InputEventType.isKeyEvent(event.getType())) dispatchKeyEvent((KeyEvent) event);
            else if(InputEventType.isMouseEvent(event.getType())) dispatchMouseEvent((MouseEvent) event);
        }
    }

    public <T extends InputEvent> void addListener(int type, EventCallback<T> callback){
        List<EventCallback<InputEvent>> list = listeners.get(type);
        if(list != null) list.add((EventCallback<InputEvent>)callback);
    }

    private <T extends InputEvent> void dispatchEvent(T event, List<EventCallback<T>> callbacks){
        for(EventCallback<T> callback : callbacks){
            int type = event.getType();
            if(event instanceof KeyEvent){
                if((type & InputEventType.KEY_PRESS) == 0){
                    KeyState.set(((KeyEvent) event).getKeyCode(), (type & InputEventType.KEY_DOWN) != 0);
                }

                ((EventCallback<KeyEvent>)callback).callback((KeyEvent) event);
            } else if(event instanceof MouseEvent){
                MouseEvent mouseEvent = (MouseEvent)event;
                MouseState.set(mouseEvent.getX(), mouseEvent.getY());

                if((type & InputEventType.MOUSE_DOWN | InputEventType.MOUSE_UP) != 0){
                    MouseState.set(mouseEvent.getButton(), (type & InputEventType.MOUSE_DOWN) != 0);
                }

                ((EventCallback<MouseEvent>)callback).callback((MouseEvent) event);
            }
        }
    }

    private void dispatchKeyEvent(KeyEvent event){
        if((event.getType() & InputEventType.KEY_DOWN) != 0){
            dispatchEvent(event, listeners.get(InputEventType.KEY_DOWN));
        } else if((event.getType() & InputEventType.KEY_UP) != 0){
            dispatchEvent(event, listeners.get(InputEventType.KEY_UP));
        } else if((event.getType() & InputEventType.KEY_PRESS) != 0){
            dispatchEvent(event, listeners.get(InputEventType.KEY_PRESS));
        }
    }

    private void dispatchMouseEvent(MouseEvent event){
        if((event.getType() & InputEventType.MOUSE_DOWN) != 0){
            dispatchEvent(event, listeners.get(InputEventType.MOUSE_DOWN));
        } else if((event.getType() & InputEventType.MOUSE_UP) != 0){
            dispatchEvent(event, listeners.get(InputEventType.MOUSE_UP));
        } else if((event.getType() & InputEventType.MOUSE_CLICK) != 0){
            dispatchEvent(event, listeners.get(InputEventType.MOUSE_CLICK));
        } else if((event.getType() & InputEventType.MOUSE_MOVE) != 0){
            dispatchEvent(event, listeners.get(InputEventType.MOUSE_MOVE));
        }
    }


}
