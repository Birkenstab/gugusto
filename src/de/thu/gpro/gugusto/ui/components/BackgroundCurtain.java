package de.thu.gpro.gugusto.ui.components;

import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.util.BackgroundUtil;
import de.thu.gpro.gugusto.util.Size;
import de.thu.gpro.gugusto.util.Vector;

import java.awt.*;

public class BackgroundCurtain extends Panel {

    public BackgroundCurtain() {
        super(new Vector(), new Size(Game.INNER_WIDTH, Game.INNER_HEIGHT), new Color(0.0f, 0.0f, 0.0f, 0.2f));
    }

}
