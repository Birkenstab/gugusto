package de.thu.gpro.gugusto.scene.scenes.leveleditor.selection;

import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.game.level.Chunk;
import de.thu.gpro.gugusto.game.level.ChunkList;
import de.thu.gpro.gugusto.game.level.Level;
import de.thu.gpro.gugusto.scene.scenes.leveleditor.editor.LevelEditorScene;
import de.thu.gpro.gugusto.ui.components.LabelFactory;
import de.thu.gpro.gugusto.ui.components.Panel;
import de.thu.gpro.gugusto.ui.components.button.TextButton;
import de.thu.gpro.gugusto.ui.components.input.NumberField;
import de.thu.gpro.gugusto.ui.components.input.TextField;
import de.thu.gpro.gugusto.util.Size;
import de.thu.gpro.gugusto.util.Vector;

import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class LevelEditorSelectionConfig extends Panel {

    private static final Size size = new Size(600, 50);
    private static final Vector position = new Vector((Game.INNER_WIDTH - size.getWidth()) / 2, 50);

    private TextField levelName;
    private NumberField levelWidth;
    private NumberField levelHeight;
    private TextButton createLevelButton;

    public LevelEditorSelectionConfig() {
        super(position, size, Color.GRAY);

        LabelFactory lf = new LabelFactory(120, new Vector(0, 5), 16);
        Vector levelNamePosition = new Vector(10, 10).add(position);
        Vector levelWidthPosition = new Vector(230, 10).add(position);
        Vector levelHeightPosition = new Vector(350, 10).add(position);
        Vector createLevelPosition = new Vector(470, 10).add(position);

        levelName = new TextField(levelNamePosition, 200, "Name");
        levelWidth = new NumberField(levelWidthPosition, 100, "Width");
        levelHeight = new NumberField(levelHeightPosition, 100, "Height");
        createLevelButton = new TextButton(lf.create(createLevelPosition, "Create Level"));
        rounded = true;

        createLevelButton.setClickListener(b -> createLevel());

        addUIComponent(levelName);
        addUIComponent(levelWidth);
        addUIComponent(levelHeight);
        addUIComponent(createLevelButton);
    }

    private void createLevel(){
        int chunkWidth = (int)Math.ceil((double)levelWidth.getInt() / Chunk.SIZE);
        int chunkHeight = (int)Math.ceil((double)levelHeight.getInt() / Chunk.SIZE);
        ChunkList chunkList = new ChunkList(chunkWidth, chunkHeight);
        Level level = new Level(levelName.getText(), chunkList, new ArrayList<>(), new Vector());
        Path path = Paths.get(String.format("./levels/%s.gug", levelName.getText()));
        Game.getInstance().getSceneManager().setScene(new LevelEditorScene(path, level));
    }

    public boolean isActive(){
        return levelName.isActive() || levelWidth.isActive() || levelHeight.isActive();
    }

}
