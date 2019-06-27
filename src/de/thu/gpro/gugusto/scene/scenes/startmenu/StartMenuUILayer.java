package de.thu.gpro.gugusto.scene.scenes.startmenu;

import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.scene.UILayer;
import de.thu.gpro.gugusto.scene.scenes.credits.CreditsScene;
import de.thu.gpro.gugusto.scene.scenes.leveleditor.selection.LevelEditorSelectionScene;
import de.thu.gpro.gugusto.scene.scenes.levelselection.LevelSelectionScene;
import de.thu.gpro.gugusto.ui.components.*;
import de.thu.gpro.gugusto.ui.components.Label;
import de.thu.gpro.gugusto.ui.components.Panel;
import de.thu.gpro.gugusto.ui.components.button.IconButton;
import de.thu.gpro.gugusto.ui.icon.Icon;
import de.thu.gpro.gugusto.util.Size;
import de.thu.gpro.gugusto.util.Vector;

import java.awt.*;

public class StartMenuUILayer extends UILayer {

    public StartMenuUILayer(){
        int centerY = Game.INNER_HEIGHT - Icon.DEFAULT_DRAW_SIZE - 20;
        int centerX = Game.INNER_WIDTH / 2;

        Font titleFont = new Font("Mali Bold", Font.PLAIN, 150);
        LabelFactory lf = new LabelFactory(Game.INNER_WIDTH, new Vector(0, 20), titleFont);

        Vector titlePosition = new Vector();
        Vector playButtonPosition = new Vector(centerX - Icon.DEFAULT_DRAW_SIZE - 20, centerY);
        Vector levelEditorButtonPosition = new Vector(centerX + 20, centerY);
        Vector creditsButtonPosition = new Vector(Game.INNER_WIDTH - Icon.DEFAULT_DRAW_SIZE - 20, 20);
        Vector exitButtonPosition = new Vector(Game.INNER_WIDTH - Icon.DEFAULT_DRAW_SIZE - 20,
                                                Game.INNER_HEIGHT - Icon.DEFAULT_DRAW_SIZE - 20);

        Label titleShadow = lf.create(new Vector(3, 3).add(titlePosition), "Gugusto");
        Label title = lf.create(titlePosition, "Gugusto");
        IconButton playButton = new IconButton(playButtonPosition, Icon.ARROW_HEAD_RIGHT);
        IconButton levelEditorButton = new IconButton(levelEditorButtonPosition, Icon.SETTINGS);
        IconButton creditsButton = new IconButton (creditsButtonPosition, Icon.INFO);
        IconButton exitButton = new IconButton(exitButtonPosition, Icon.OPEN_DOOR);

        titleShadow.setColor(new Color(0, 0, 0, 60));
        title.setColor(Color.WHITE);
        playButton.setClickListener(b -> Game.getInstance().getSceneManager().setScene(new LevelSelectionScene()));
        levelEditorButton.setClickListener(b -> Game.getInstance().getSceneManager().setScene(new LevelEditorSelectionScene()));
        creditsButton.setClickListener(b -> Game.getInstance().getSceneManager().setScene(new CreditsScene()));
        exitButton.setClickListener(b -> System.exit(0));

        addUIComponent(new BackgroundCurtain());
        addUIComponent(titleShadow);
        addUIComponent(title);
        addUIComponent(playButton);
        addUIComponent(levelEditorButton);
        addUIComponent(creditsButton);
        addUIComponent(exitButton);
    }

}
