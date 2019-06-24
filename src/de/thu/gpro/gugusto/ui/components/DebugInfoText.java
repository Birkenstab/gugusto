package de.thu.gpro.gugusto.ui.components;

import de.thu.gpro.gugusto.input.event.EventCallback;
import de.thu.gpro.gugusto.input.event.InputEventType;
import de.thu.gpro.gugusto.input.event.KeyEvent;
import de.thu.gpro.gugusto.util.Size;
import de.thu.gpro.gugusto.util.Vector;
import de.thu.gpro.gugusto.util.DebugInfo;

import java.awt.*;
import java.security.Key;

public class DebugInfoText extends UIComponent {
    private static final Font font = new Font("Dialog", Font.PLAIN, 12);
    public static final int WIDTH = 64;
    public static final int HEIGHT = 16;

    public DebugInfoText(Vector position) {
        super(position, new Size(WIDTH, HEIGHT));

        addListener(InputEventType.KEY_DOWN, (EventCallback<KeyEvent>)e -> {
            if(e.getChar() == 'i'){
                setVisible(!isVisible());
                return true;
            }
            return false;
        });

        setVisible(false);
    }

    @Override
    public void draw(Graphics2D g2d){
        g2d.setFont(font);
        g2d.setColor(Color.MAGENTA);
        String text = DebugInfo.toText();

        int y = getY();
        for (String line : text.split("\n")) // Weil drawString alles in eine Zeile malt
            g2d.drawString(line, getX(), y += g2d.getFontMetrics().getHeight());
    }
}
