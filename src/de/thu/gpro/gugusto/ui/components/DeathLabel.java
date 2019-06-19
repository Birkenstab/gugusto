package de.thu.gpro.gugusto.ui.components;

import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.graphic.animation.ColorAlphaAnimation;
import de.thu.gpro.gugusto.util.Size;
import de.thu.gpro.gugusto.util.Vector;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class DeathLabel extends Label {

    private ColorAlphaAnimation animation;

    public DeathLabel(String label, Font font) {
        super(new Vector(), label, new Vector(), font);

        animation = new ColorAlphaAnimation(new Color(255, 0, 0), 1000);

        Size size = getBoundingBox().getSize();
        setPosition(new Vector((Game.INNER_WIDTH - size.getWidth()) / 2, (Game.INNER_HEIGHT - size.getHeight()) / 2));
    }

    @Override
    public void update(double delta) {
        super.update(delta);

        animation.update(delta);
        color = animation.getColor();
    }

    @Override
    public void setVisible(boolean visible){
        super.setVisible(visible);

        if(visible){
            animation.reset();
            animation.start();
        }
    }

}
