package de.thu.gpro.gugusto.scene.scenes.leveleditor.editor;

import de.thu.gpro.gugusto.game.object.GameObject;
import de.thu.gpro.gugusto.util.Vector;

public class LevelEditorConfig {

    private Vector cameraPosition;
    private double cameraScale;
    private GameObject.Type selectedType;
    private int selectedId;

    LevelEditorConfig(){
    }

    public void setSelected(int id, GameObject.Type type){
        selectedId = id;
        selectedType = type;
    }

    public void setCamera(Vector position, double scale){
        cameraPosition = position;
        cameraScale = scale;
    }

    public LevelEditorCamera getCamera(){
        return new LevelEditorCamera(cameraPosition, cameraScale);
    }

    public int getSelectedId(){
        return selectedId;
    }

    public GameObject.Type getSelectedType(){
        return selectedType;
    }

}
