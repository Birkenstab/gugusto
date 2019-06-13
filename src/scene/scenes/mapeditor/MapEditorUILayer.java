package scene.scenes.mapeditor;

import game.Game;
import scene.UILayer;
import ui.components.button.IconButton;
import ui.icon.Icon;
import util.Vector;

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
