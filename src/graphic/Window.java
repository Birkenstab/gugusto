package graphic;

import input.event.EventCallback;
import input.event.InputEventType;
import input.event.KeyEvent;
import input.event.MouseEvent;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;

public class Window extends JFrame implements IWindow, KeyListener, MouseListener, MouseMotionListener {

    public static final int TITLEBAR_HEIGHT = 22;

    private EventCallback<KeyEvent> keyEventEventCallback;
    private EventCallback<MouseEvent> mouseEventEventCallback;

    public Window(int width, int height){
        super("Game");

        setSize(width, height);
        setBackground(Color.WHITE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);

        createBufferStrategy(2);
    }

    public BufferStrategy getBufferStrategy(){
        return super.getBufferStrategy();
    }

    @Override
    public void setKeyListener(EventCallback<KeyEvent> eventCallback) {
        keyEventEventCallback = eventCallback;
    }

    @Override
    public void setMouseListener(EventCallback<MouseEvent> eventCallback) {
        mouseEventEventCallback = eventCallback;
    }

    @Override
    public void keyTyped(java.awt.event.KeyEvent e) {
        KeyEvent event = new KeyEvent(e.getKeyChar(), e.getKeyCode(), InputEventType.KEY_PRESS);
        if(keyEventEventCallback != null) keyEventEventCallback.callback(event);
    }

    @Override
    public void keyPressed(java.awt.event.KeyEvent e) {
        KeyEvent event = new KeyEvent(e.getKeyChar(), e.getKeyCode(), InputEventType.KEY_DOWN);
        if(keyEventEventCallback != null) keyEventEventCallback.callback(event);
    }

    @Override
    public void keyReleased(java.awt.event.KeyEvent e) {
        KeyEvent event = new KeyEvent(e.getKeyChar(), e.getKeyCode(), InputEventType.KEY_UP);
        if(keyEventEventCallback != null) keyEventEventCallback.callback(event);
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        MouseEvent event = new MouseEvent(e.getX(), e.getY(), e.getButton(), InputEventType.MOUSE_CLICK);
        if(mouseEventEventCallback != null) mouseEventEventCallback.callback(event);
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        MouseEvent event = new MouseEvent(e.getX(), e.getY(), e.getButton(), InputEventType.MOUSE_DOWN);
        if(mouseEventEventCallback != null) mouseEventEventCallback.callback(event);
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
        MouseEvent event = new MouseEvent(e.getX(), e.getY(), e.getButton(), InputEventType.MOUSE_UP);
        if(mouseEventEventCallback != null) mouseEventEventCallback.callback(event);
    }

    @Override
    public void mouseMoved(java.awt.event.MouseEvent e) {
        MouseEvent event = new MouseEvent(e.getX(), e.getY(), e.getButton(), InputEventType.MOUSE_MOVE);
        if(mouseEventEventCallback != null) mouseEventEventCallback.callback(event);
    }

    @Override
    public void mouseDragged(java.awt.event.MouseEvent e) {
        MouseEvent event = new MouseEvent(e.getX(), e.getY(), e.getButton(), InputEventType.MOUSE_MOVE);
        if(mouseEventEventCallback != null) mouseEventEventCallback.callback(event);
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {}

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {}

}
