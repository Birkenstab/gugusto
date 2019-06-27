package de.thu.gpro.gugusto.scene.scenes.leveleditor.editor;

import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.scene.UILayer;
import de.thu.gpro.gugusto.ui.components.Label;
import de.thu.gpro.gugusto.ui.components.LabelFactory;
import de.thu.gpro.gugusto.ui.components.button.IconButton;
import de.thu.gpro.gugusto.ui.icon.Icon;
import de.thu.gpro.gugusto.util.Vector;

class LevelEditorUILayer extends UILayer {

    private LevelEditorAction levelEditorAction;
    private ObjectPlacementSelector objectPlacementSelector;

    private Label modeLabel;

    LevelEditorUILayer(LevelEditorAction levelEditorAction){
        this.levelEditorAction = levelEditorAction;
        objectPlacementSelector = new ObjectPlacementSelector(new Vector(80, Game.INNER_HEIGHT - 300), levelEditorAction);

        int ids = Icon.DEFAULT_DRAW_SIZE;
        LabelFactory lf = new LabelFactory(new Vector(0, 0), 16);
        Vector testLevelPosition = new Vector(Game.INNER_WIDTH - ids - 10, Game.INNER_HEIGHT - 10 - ids);
        IconButton openOPSButton = new IconButton(new Vector(10, Game.INNER_HEIGHT - 10 - ids), Icon.OVERVIEW);
        IconButton testLevelButton = new IconButton(testLevelPosition, Icon.ARROW_HEAD_RIGHT);
        modeLabel = lf.create(new Vector(Game.INNER_WIDTH - 100, 10), "Mode: Single");

        openOPSButton.setClickListener(b -> objectPlacementSelector.setVisible(!objectPlacementSelector.isVisible()));
        testLevelButton.setClickListener(b -> levelEditorAction.toLevelScene());

        addUIComponent(modeLabel);
        addUIComponent(openOPSButton);
        addUIComponent(testLevelButton);
        addUIComponent(objectPlacementSelector);
        addUIComponent(new LevelEditorMenu(levelEditorAction));
    }

    @Override
    public void update(double delta){
        super.update(delta);

        modeLabel.setText(levelEditorAction.getModeString());
    }

}
