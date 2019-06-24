package de.thu.gpro.gugusto.scene.scenes.levelselection;

import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.scene.Layer;
import de.thu.gpro.gugusto.util.BackgroundUtil;
import de.thu.gpro.gugusto.util.Vector;

import java.awt.*;

public class BackgroundLayer extends Layer {
    private Vector position;

    public BackgroundLayer() {
        try {
            this.position = Game.getInstance().getCamera().getPosition();
        } catch (Exception e) {
            this.position = null;
        }

        if (this.position == null)
            this.position = new Vector();
    }

    @Override
    public void draw(Graphics2D g2d) {
        super.draw(g2d);
        BackgroundUtil.drawBackground(g2d, position);
    }
}
