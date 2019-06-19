package de.thu.gpro.gugusto.ui.components.input;

import de.thu.gpro.gugusto.graphic.animation.ToggleAnimation;
import de.thu.gpro.gugusto.input.event.InputEventType;
import de.thu.gpro.gugusto.input.event.KeyEvent;
import de.thu.gpro.gugusto.ui.components.Label;
import de.thu.gpro.gugusto.ui.components.LabelFactory;
import de.thu.gpro.gugusto.ui.components.Panel;
import de.thu.gpro.gugusto.util.Size;
import de.thu.gpro.gugusto.util.Vector;

import java.awt.*;
import java.awt.geom.Rectangle2D;


public class TextField extends Panel {

    private static final Font font = new Font("Arial", Font.BOLD, 16);
    private static final int padding = 5;

    private Label label;
    private String placeholder;
    private ToggleAnimation animation;

    protected String value = "";

    public TextField(Vector position, int width, String placeholder){
        super(position, new Size(width, 28), Color.WHITE);

        this.placeholder = placeholder;
        animation = new ToggleAnimation(750);

        LabelFactory lf = new LabelFactory(200, new Vector(padding, padding), font, Label.Alignment.LEFT);
        label = lf.create(position, placeholder);
        addUIComponent(label);

        addListener(InputEventType.KEY_DOWN, this::onKeyDown);
        addListener(InputEventType.KEY_UP, e -> isActive());
        addListener(InputEventType.KEY_PRESS, e -> isActive());
    }

    @Override
    public void update(double delta){
        super.update(delta);

        animation.update(delta);
    }

    private boolean onKeyDown(KeyEvent e){
        if(isActive()){
            if(isValidKey(e.getChar()) && isSpaceAvailable(value + e.getChar())){
                value += e.getChar();
            } else if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE && value.length() > 0){
                value = value.substring(0, value.length() - 1);
            } else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                setActive(false);
            }

            label.setText(value.equals("") ? placeholder : value);
            return true;
        }

        return false;
    }

    private boolean isSpaceAvailable(String text){
        int maxWidth = (int)(getBoundingBox().getSize().getWidth() - padding * 2);
        return Label.getBounds(text, font).getWidth() < maxWidth;
    }

    @Override
    public void draw(Graphics2D g2d){
        super.draw(g2d);

        g2d.setColor(Color.BLACK);
        g2d.drawRect(getX(), getY(), getWidth(), getHeight());

        if(isActive() && animation.isToggled()) drawCorsor(g2d);
    }

    private void drawCorsor(Graphics2D g2d){
        Rectangle2D bounds = label.getBounds();
        int corsorX = (int)(getX() + bounds.getWidth() + padding + 1);
        int corsorY = getY() + padding;

        g2d.setColor(Color.BLACK);
        g2d.drawLine(corsorX, corsorY, corsorX, corsorY + (int)bounds.getHeight());
    }

    protected boolean isValidKey(char c){
        return c > 31 && c < 127;
    }

    public String getText(){
        return value;
    }

}
