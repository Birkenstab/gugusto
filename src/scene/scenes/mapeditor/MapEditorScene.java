package scene.scenes.mapeditor;

import game.level.Level;
import game.level.LevelLoader;
import scene.Scene;

import java.nio.file.Paths;

public class MapEditorScene extends Scene {

    private MapEditorLayer mapEditorLayer;
    private MapEditorUILayer mapEditorUILayer;
    private Level level;
    private Action action;

    public MapEditorScene(){
        level = LevelLoader.load(Paths.get("./test.gug"));
        action = new Action(level);
        mapEditorLayer = new MapEditorLayer(level, action);
        mapEditorUILayer = new MapEditorUILayer(action);

        addLayer(mapEditorLayer);
        addLayer(mapEditorUILayer);
    }

}
