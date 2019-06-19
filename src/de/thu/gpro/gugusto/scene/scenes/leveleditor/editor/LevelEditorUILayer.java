package de.thu.gpro.gugusto.scene.scenes.leveleditor.editor;

import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.scene.UILayer;
import de.thu.gpro.gugusto.scene.scenes.level.LevelScene;
import de.thu.gpro.gugusto.ui.components.button.IconButton;
import de.thu.gpro.gugusto.ui.icon.Icon;
import de.thu.gpro.gugusto.util.Vector;

class LevelEditorUILayer extends UILayer {

    private LevelEditorAction levelEditorAction;
    private ObjectPlacementSelector objectPlacementSelector;

    LevelEditorUILayer(LevelEditorAction levelEditorAction){
        this.levelEditorAction = levelEditorAction;
        objectPlacementSelector = new ObjectPlacementSelector(new Vector(100, 100), levelEditorAction);

        int ids = Icon.DEFAULT_DRAW_SIZE;
        Vector testLevelPosition = new Vector(Game.INNER_WIDTH - ids - 10, Game.INNER_HEIGHT - 10 - ids);
        IconButton openOPSButton = new IconButton(new Vector(10, Game.INNER_HEIGHT - 10 - ids), Icon.OVERVIEW);
        IconButton testLevel = new IconButton(testLevelPosition, Icon.ARROW_HEAD_RIGHT);

        openOPSButton.setClickListener(b -> objectPlacementSelector.setVisible(!objectPlacementSelector.isVisible()));
        testLevel.setClickListener(b -> levelEditorAction.toLevelScene());

        addUIComponent(openOPSButton);
        addUIComponent(testLevel);
        addUIComponent(objectPlacementSelector);
        addUIComponent(new LevelEditorMenu(levelEditorAction));
    }

}
