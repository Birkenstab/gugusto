package de.thu.gpro.gugusto.ui.components;

import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.input.event.InputEventType;
import de.thu.gpro.gugusto.input.event.KeyEvent;
import de.thu.gpro.gugusto.ui.components.button.Button;
import de.thu.gpro.gugusto.ui.components.button.IconButton;
import de.thu.gpro.gugusto.ui.components.button.TextButton;
import de.thu.gpro.gugusto.ui.icon.Icon;
import de.thu.gpro.gugusto.util.Size;
import de.thu.gpro.gugusto.util.Vector;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public abstract class Menu extends Panel {

    private static final int headerHeight = 50;
    private static final int closeButtonSize = 30;
    private static final int menuItemHeight = 50;
    private static final int menuWidth = 280;
    private static final int menuItemPadding = 10;

    private static final LabelFactory lf;

    static {
        lf = new LabelFactory(menuWidth - menuItemPadding * 2, new Vector(0, 10), 16);
    }

    private IconButton closeButton;
    private List<TextButton> menuItems;

    protected Menu() {
        super(new Vector(), new Size(), Color.GRAY);

        closeButton = new IconButton(new Vector(), closeButtonSize, Icon.CLOSE);
        closeButton.setClickListener(e -> setVisible(false));
        menuItems = new ArrayList<>();

        addListener(InputEventType.KEY_DOWN, this::onKeyDown);
        addListener(InputEventType.KEY_UP, e -> isVisible());
        addListener(InputEventType.KEY_PRESS, e -> isVisible());
    }

    protected void addMenuEntry(String label, Button.OnClickListener listener){
        TextButton button = new TextButton(lf.create(new Vector(), label));
        button.setClickListener(listener);
        menuItems.add(button);
    }

    protected void build(){
        updatePositionAndSize();

        closeButton.getBoundingBox().getPosition().set(menuWidth - closeButtonSize - 10, 10);
        closeButton.getBoundingBox().getPosition().add(boundingBox.getPosition());

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

        double x = (Game.INNER_WIDTH - boundingBox.getSize().getWidth()) / 2;
        double y = (Game.INNER_HEIGHT - boundingBox.getSize().getHeight()) / 2;
        boundingBox.getPosition().set(x, y);
    }

    public boolean onKeyDown(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            setVisible(!isVisible());
            return true;
        }

        return isVisible();
    }

}
