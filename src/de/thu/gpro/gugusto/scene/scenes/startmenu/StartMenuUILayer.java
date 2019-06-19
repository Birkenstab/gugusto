package de.thu.gpro.gugusto.scene.scenes.startmenu;

import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.scene.UILayer;
import de.thu.gpro.gugusto.scene.scenes.level.LevelScene;
import de.thu.gpro.gugusto.scene.scenes.leveleditor.selection.LevelEditorSelectionScene;
import de.thu.gpro.gugusto.ui.components.button.TextButton;
import de.thu.gpro.gugusto.ui.components.LabelFactory;
import de.thu.gpro.gugusto.util.Vector;

import java.nio.file.Paths;

public class StartMenuUILayer extends UILayer {

    public StartMenuUILayer(){
        int buttonCenterX = (Game.INNER_WIDTH - 200) / 2;
        int centerY = Game.INNER_HEIGHT / 2;

        LabelFactory lf = new LabelFactory(200, new Vector(0, 20), 18);

        Vector playButtonPosition = new Vector(buttonCenterX, centerY - 140);
        Vector levelEditorButtonPosition = new Vector(buttonCenterX, centerY - 40);
        Vector exitButtonPosition = new Vector(buttonCenterX, centerY + 60);

        TextButton playButton = new TextButton(lf.create(playButtonPosition, "Play"));
        TextButton levelEditorButton = new TextButton(lf.create(levelEditorButtonPosition, "Map Editor"));
        TextButton exitButton = new TextButton(lf.create(exitButtonPosition, "Exit"));

        playButton.setClickListener(b -> Game.getInstance().getSceneManager().setScene(new LevelScene(Paths.get("./test.gug"))));
        levelEditorButton.setClickListener(b -> Game.getInstance().getSceneManager().setScene(new LevelEditorSelectionScene()));
        exitButton.setClickListener(b -> System.exit(0));

        addUIComponent(playButton);
        addUIComponent(levelEditorButton);
        addUIComponent(exitButton);
    }

}
