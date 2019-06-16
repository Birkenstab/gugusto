package de.thu.gpro.gugusto.scene.scenes.mapeditor;

import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.scene.UILayer;
import de.thu.gpro.gugusto.ui.components.button.IconButton;
import de.thu.gpro.gugusto.ui.icon.Icon;
import de.thu.gpro.gugusto.util.Vector;

public class MapEditorUILayer extends UILayer {

    private MapEditorAction mapEditorAction;
    private ObjectPlacementSelector objectPlacementSelector;

    public MapEditorUILayer(MapEditorAction mapEditorAction){
        this.mapEditorAction = mapEditorAction;

        objectPlacementSelector = new ObjectPlacementSelector(new Vector(100, 100), mapEditorAction);
        IconButton openOPSButton = new IconButton(new Vector(10, Game.INNER_HEIGHT - 10 - Icon.DEFAULT_DRAW_SIZE), Icon.OVERVIEW);

        openOPSButton.setClickListener(b -> objectPlacementSelector.setVisible(!objectPlacementSelector.isVisible()));

        addUIComponent(openOPSButton);
        addUIComponent(objectPlacementSelector);
        addUIComponent(new MapEditorMenu(mapEditorAction));
    }

}
