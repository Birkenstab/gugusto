package ui.components;

import util.DebugInfo;
import util.Size;
import util.Vector;

import java.awt.*;

public class DebugInfoText extends UIComponent {
    private static final Font font = new Font("Dialog", Font.PLAIN, 12);
    public static final int WIDTH = 64;
    public static final int HEIGHT = 16;

    public DebugInfoText(Vector position) {
        super(position, new Size(WIDTH, HEIGHT));
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
