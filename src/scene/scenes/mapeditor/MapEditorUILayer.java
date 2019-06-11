package scene.scenes.mapeditor;

import scene.UILayer;

public class MapEditorUILayer extends UILayer {

    private MapEditorAction mapEditorAction;

    public MapEditorUILayer(MapEditorAction mapEditorAction){
        this.mapEditorAction = mapEditorAction;

        addUIComponent(new MapEditorMenu(mapEditorAction));
    }


}
