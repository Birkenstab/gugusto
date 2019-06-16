package de.thu.gpro.gugusto.scene.scenes.mapeditor;

import de.thu.gpro.gugusto.game.level.Level;
import de.thu.gpro.gugusto.game.level.LevelLoader;
import de.thu.gpro.gugusto.scene.Scene;

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
