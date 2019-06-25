package de.thu.gpro.gugusto.ui.components;

import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.graphic.animation.ColorAlphaAnimation;
import de.thu.gpro.gugusto.util.Size;
import de.thu.gpro.gugusto.util.Vector;

import java.awt.*;

public class FadingLabel extends Label {

    private ColorAlphaAnimation animation;

    public FadingLabel(String label, Color color) {
        super(new Vector(), label, Game.INNER_WIDTH, new Vector(), new Font("Arial", Font.PLAIN, 50), Alignment.CENTER);

        animation = new ColorAlphaAnimation(color, 1000);

        Size size = getBoundingBox().getSize();
        setPosition(new Vector(0, (Game.INNER_HEIGHT - size.getHeight()) / 2));
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
