package ui.components;

import game.Camera;
import input.event.KeyEvent;
import util.Size;
import util.Vector;

import java.awt.*;
import java.util.List;


public class Menu extends UIComponent {

    public Menu(Vector position, Size size, List<UIComponent> components) {
        super(position, size);
        addUIComponent(new Panel(position, size, Color.GRAY));
        addUIComponent(components);
    }

    @Override
    public boolean onKeyDown(KeyEvent e){
        if(e.getKeyCode() == java.awt.event.KeyEvent.VK_ESCAPE){
            setVisible(!isVisible());
            return true;
        }

        return false;
    }

}
