package de.thu.gpro.gugusto.scene.scenes.leveleditor.editor;

import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.scene.UILayer;
import de.thu.gpro.gugusto.ui.components.button.IconButton;
import de.thu.gpro.gugusto.ui.icon.Icon;
import de.thu.gpro.gugusto.util.Vector;

class LevelEditorUILayer extends UILayer {

    private LevelEditorAction levelEditorAction;
    private ObjectPlacementSelector objectPlacementSelector;

    LevelEditorUILayer(LevelEditorAction levelEditorAction){
        this.levelEditorAction = levelEditorAction;

        objectPlacementSelector = new ObjectPlacementSelector(new Vector(100, 100), levelEditorAction);
        IconButton openOPSButton = new IconButton(new Vector(10, Game.INNER_HEIGHT - 10 - Icon.DEFAULT_DRAW_SIZE), Icon.OVERVIEW);

        openOPSButton.setClickListener(b -> objectPlacementSelector.setVisible(!objectPlacementSelector.isVisible()));

        addUIComponent(openOPSButton);
        addUIComponent(objectPlacementSelector);
        addUIComponent(new LevelEditorMenu(levelEditorAction));
    }

}
