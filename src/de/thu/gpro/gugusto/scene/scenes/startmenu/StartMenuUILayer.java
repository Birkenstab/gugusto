package de.thu.gpro.gugusto.scene.scenes.startmenu;

import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.scene.UILayer;
import de.thu.gpro.gugusto.scene.scenes.credits.CreditsScene;
import de.thu.gpro.gugusto.scene.scenes.level.LevelScene;
import de.thu.gpro.gugusto.scene.scenes.leveleditor.selection.LevelEditorSelectionScene;
import de.thu.gpro.gugusto.scene.scenes.levelselection.LevelSelectionScene;
import de.thu.gpro.gugusto.ui.components.Panel;
import de.thu.gpro.gugusto.ui.components.button.TextButton;
import de.thu.gpro.gugusto.ui.components.LabelFactory;
import de.thu.gpro.gugusto.util.Size;
import de.thu.gpro.gugusto.util.Vector;

import java.awt.*;
import java.nio.file.Paths;

public class StartMenuUILayer extends UILayer {

    public StartMenuUILayer(){
        int buttonCenterX = (Game.INNER_WIDTH - 200) / 2;
        int centerY = Game.INNER_HEIGHT / 2;

        LabelFactory lf = new LabelFactory(200, new Vector(0, 20), 18);

        Vector playButtonPosition = new Vector(buttonCenterX, centerY - 190);
        Vector levelEditorButtonPosition = new Vector(buttonCenterX, centerY - 90);
        Vector creditsButtonPosition = new Vector(buttonCenterX, centerY + 10);
        Vector exitButtonPosition = new Vector(buttonCenterX, centerY + 110);

        TextButton playButton = new TextButton(lf.create(playButtonPosition, "Play"));
        TextButton levelEditorButton = new TextButton(lf.create(levelEditorButtonPosition, "Map Editor"));
        TextButton creditsButton = new TextButton(lf.create(creditsButtonPosition, "Credits"));
        TextButton exitButton = new TextButton(lf.create(exitButtonPosition, "Exit"));

        playButton.setClickListener(b -> Game.getInstance().getSceneManager().setScene(new LevelSelectionScene()));
        levelEditorButton.setClickListener(b -> Game.getInstance().getSceneManager().setScene(new LevelEditorSelectionScene()));
        creditsButton.setClickListener(b -> Game.getInstance().getSceneManager().setScene(new CreditsScene()));
        exitButton.setClickListener(b -> System.exit(0));

        addUIComponent(new Panel(new Vector(), new Size(Game.INNER_WIDTH, Game.INNER_HEIGHT), new Color(0.0f, 0.0f, 0.0f, 0.2f)));
        addUIComponent(playButton);
        addUIComponent(levelEditorButton);
        addUIComponent(creditsButton);
        addUIComponent(exitButton);
    }

}
