package scene.scenes.mapeditor;

import game.level.Level;
import game.level.LevelLoader;
import scene.Scene;

import java.nio.file.Paths;

public class MapEditorScene extends Scene {

    private MapEditorLayer mapEditorLayer;
    private MapEditorUILayer mapEditorUILayer;
    private Level level;
    private MapEditorAction mapEditorAction;

    public MapEditorScene(){
        level = LevelLoader.load(Paths.get("./test.gug"));
        mapEditorAction = new MapEditorAction(level);
        mapEditorLayer = new MapEditorLayer(level, mapEditorAction);
        mapEditorUILayer = new MapEditorUILayer(mapEditorAction);

        addLayer(mapEditorLayer);
        addLayer(mapEditorUILayer);
    }

}
