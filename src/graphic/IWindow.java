package graphic;

import input.event.EventCallback;
import input.event.KeyEvent;
import input.event.MouseEvent;

public interface IWindow {

    int getWidth();
    int getHeight();

    void setKeyListener(EventCallback<KeyEvent> eventCallback);
    void setMouseListener(EventCallback<MouseEvent> eventCallback);

}
