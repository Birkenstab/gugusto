package de.thu.gpro.gugusto.scene.scenes.level;

import de.thu.gpro.gugusto.scene.UILayer;
import de.thu.gpro.gugusto.util.Vector;
import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.ui.components.DebugInfoText;
import de.thu.gpro.gugusto.ui.components.FpsCounter;

public class LevelUILayer extends UILayer {

    private LevelAction levelAction;

    public LevelUILayer(LevelAction levelAction){
        this.levelAction = levelAction;

        addUIComponent(new LevelMenu(levelAction));
        addUIComponent(new FpsCounter(new Vector(Game.INNER_WIDTH - FpsCounter.WIDTH - 10, 10)));
        addUIComponent(new DebugInfoText(new Vector(10, 10)));
    }

}
