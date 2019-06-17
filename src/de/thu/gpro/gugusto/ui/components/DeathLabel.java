package de.thu.gpro.gugusto.ui.components;

import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.util.Size;
import de.thu.gpro.gugusto.util.Vector;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

// TODO Wir brauchen ein normales Label. Davon kann dann DeathLabel erben
public class DeathLabel extends UIComponent {
    private static final FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);

    private Font font;
    private String label;
    private Color color;

    private static Size calcSize(String text, Font font){
        Rectangle2D rect = font.getStringBounds(text, frc);
        return new Size(rect.getWidth(), rect.getHeight());
    }

    public DeathLabel(String label, Font font) {
        super(new Vector(), new Size());
        this.label = label;
        this.font = font;
        resetAnimation();

        Size size = calcSize(label, font);
        boundingBox.getSize().set(size);

        setPosition(new Vector((Game.INNER_WIDTH - size.getWidth()) / 2, (Game.INNER_HEIGHT - size.getHeight()) / 2));
    }

    public void resetAnimation() {
        this.color = new Color(255, 0, 0, 0);
    }

    @Override
    public void draw(Graphics2D g2d) {

        g2d.setColor(color);
        g2d.setFont(font);

        g2d.drawString(label, getX(), getY());
    }

    @Override
    public void update(double delta) {
        super.update(delta);
        int alphaDelta = (int)(255 * delta / 2);
        if (alphaDelta < 1)
            alphaDelta = 1;
        int alpha = color.getAlpha() + alphaDelta;
        if (alpha > 255)
            alpha = 255;

        color = new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }
}
