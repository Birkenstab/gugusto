package de.thu.gpro.gugusto.scene.scenes.leveleditor.editor;

import de.thu.gpro.gugusto.game.level.Level;
import de.thu.gpro.gugusto.game.level.io.LevelLoader;
import de.thu.gpro.gugusto.scene.Scene;

import java.nio.file.Path;
import java.nio.file.Paths;

public class LevelEditorScene extends Scene {

    private LevelEditorLayer levelEditorLayer;
    private LevelEditorUILayer levelEditorUILayer;

    private LevelEditorAction levelEditorAction;

    public LevelEditorScene(Path path){
        this(path, LevelLoader.load(path));
    }

    public LevelEditorScene(Path path, Level level){
        levelEditorAction = new LevelEditorAction(path, level);
        levelEditorLayer = new LevelEditorLayer(level, levelEditorAction);
        levelEditorUILayer = new LevelEditorUILayer(levelEditorAction);

        addLayer(levelEditorLayer);
        addLayer(levelEditorUILayer);
    }

}
