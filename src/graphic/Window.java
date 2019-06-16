package graphic;

import input.event.EventCallback;
import input.event.InputEventType;
import input.event.KeyEvent;
import input.event.MouseEvent;
import util.Vector;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;

import static java.awt.event.MouseWheelEvent.WHEEL_UNIT_SCROLL;

public class Window extends JFrame implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    private final Vector INSETS = new Vector();
    private final Vector TOTAL_INSETS = new Vector();

    private EventCallback<KeyEvent> keyEventEventCallback;
    private EventCallback<MouseEvent> mouseEventEventCallback;

    private int iX; //inset x
    private int iY; //inset y

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
        addMouseWheelListener(this);
        setResizable(false);

        createBufferStrategy(2);

        Insets is = getInsets();
        iX = is.left;
        iY = is.top;
        INSETS.set(iX, iY);
        TOTAL_INSETS.set(is.left + is.right, is.top + is.bottom);
    }

    public BufferStrategy getBufferStrategy(){
        return super.getBufferStrategy();
    }

    public void setKeyListener(EventCallback<KeyEvent> eventCallback) {
        keyEventEventCallback = eventCallback;
    }

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
        MouseEvent event = new MouseEvent(e.getX() - iX, e.getY() - iY, e.getButton(), InputEventType.MOUSE_CLICK);
        if(mouseEventEventCallback != null) mouseEventEventCallback.callback(event);
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        MouseEvent event = new MouseEvent(e.getX() - iX, e.getY() - iY, e.getButton(), InputEventType.MOUSE_DOWN);
        if(mouseEventEventCallback != null) mouseEventEventCallback.callback(event);
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
        MouseEvent event = new MouseEvent(e.getX() - iX, e.getY() - iY, e.getButton(), InputEventType.MOUSE_UP);
        if(mouseEventEventCallback != null) mouseEventEventCallback.callback(event);
    }

    @Override
    public void mouseMoved(java.awt.event.MouseEvent e) {
        MouseEvent event = new MouseEvent(e.getX() - iX, e.getY() - iY, e.getButton(), InputEventType.MOUSE_MOVE);
        if(mouseEventEventCallback != null) mouseEventEventCallback.callback(event);
    }

    @Override
    public void mouseDragged(java.awt.event.MouseEvent e) {
        MouseEvent event = new MouseEvent(e.getX() - iX, e.getY() - iY, e.getButton(), InputEventType.MOUSE_MOVE);
        if(mouseEventEventCallback != null) mouseEventEventCallback.callback(event);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if(e.getScrollType() == WHEEL_UNIT_SCROLL){
            MouseEvent event = new MouseEvent(e.getX() - iX, e.getY() - iY, e.getUnitsToScroll());
            if(mouseEventEventCallback != null) mouseEventEventCallback.callback(event);
        }
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {}

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {}

    public Vector getTopLeftInsets(){
        return INSETS;
    }

    public Vector getTotalInset(){
        return TOTAL_INSETS;
    }

}
