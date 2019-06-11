package ui.components;

import game.Game;
import input.event.KeyEvent;
import ui.components.button.Button;
import ui.components.button.IconButton;
import ui.components.button.TextButton;
import ui.components.button.TextButtonFactory;
import ui.icon.Icon;
import util.Size;
import util.Vector;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public abstract class Menu extends UIComponent {

    private static final int headerHeight = 50;
    private static final int closeButtonSize = 30;
    private static final int menuItemHeight = 50;
    private static final int menuWidth = 280;
    private static final int menuItemPadding = 10;

    private static final TextButtonFactory tbf;

    static {
        tbf = new TextButtonFactory(menuWidth - menuItemPadding * 2, new Vector(0, 10), 16);
    }

    private IconButton closeButton;
    private List<TextButton> menuItems;

    protected Menu() {
        super(new Vector(), new Size());

        closeButton = new IconButton(new Vector(), closeButtonSize, Icon.CLOSE);
        closeButton.setClickListener(e -> setVisible(false));
        menuItems = new ArrayList<>();
    }

    protected void addMenuEntry(String label, Button.OnClickListener listener){
        TextButton button = tbf.create(new Vector(), label);
        button.setClickListener(listener);
        menuItems.add(button);
    }

    protected void build(){
        updatePositionAndSize();

        closeButton.getBoundingBox().getPosition().set(menuWidth - closeButtonSize - 10, 10);
        closeButton.getBoundingBox().getPosition().add(boundingBox.getPosition());

        addUIComponent(new Panel(boundingBox.getPosition(), boundingBox.getSize(), Color.GRAY));
        addUIComponent(closeButton);

        for(int i = 0; i < menuItems.size(); i++){
            TextButton button = menuItems.get(i);
            button.getBoundingBox().getPosition().set(menuItemPadding, headerHeight + menuItemHeight * i).add(getBoundingBox().getPosition());
            addUIComponent(button);
        }

        setVisible(false);
    }

    private void updatePositionAndSize(){
        boundingBox.getSize().set(menuWidth, menuItems.size() * menuItemHeight + headerHeight);

        double x = (Game.WIDTH - boundingBox.getSize().getWidth()) / 2;
        double y = (Game.HEIGHT - boundingBox.getSize().getHeight()) / 2;
        boundingBox.getPosition().set(x, y);
    }

    @Override
    public boolean onKeyDown(KeyEvent e){
        if(e.getKeyCode() == java.awt.event.KeyEvent.VK_ESCAPE){
            setVisible(!isVisible());
            return true;
        }

        return isVisible();
    }

    @Override
    public boolean onKeyUp(KeyEvent e){
        return isVisible();
    }

    @Override
    public boolean onKeyPress(KeyEvent e){
        return isVisible();
    }

}
