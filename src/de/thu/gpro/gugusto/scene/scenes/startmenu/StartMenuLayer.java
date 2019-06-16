package de.thu.gpro.gugusto.scene.scenes.startmenu;

import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.scene.UILayer;
import de.thu.gpro.gugusto.scene.scenes.level.LevelScene;
import de.thu.gpro.gugusto.ui.components.button.TextButton;
import de.thu.gpro.gugusto.ui.components.button.TextButtonFactory;
import de.thu.gpro.gugusto.util.Vector;
import de.thu.gpro.gugusto.scene.scenes.mapeditor.MapEditorScene;

import java.nio.file.Paths;

public class StartMenuLayer extends UILayer {

    public StartMenuLayer(){
        int buttonCenterX = (Game.WIDTH - 200) / 2;
        int centerY = Game.HEIGHT / 2;

        TextButtonFactory tbf = new TextButtonFactory(200, new Vector(0, 20), 18);

        Vector playButtonPosition = new Vector(buttonCenterX, centerY - 140);
        Vector mapEditorButtonPosition = new Vector(buttonCenterX, centerY - 40);
        Vector exitButtonPosition = new Vector(buttonCenterX, centerY + 60);

        TextButton playButton = tbf.create(playButtonPosition, "Play");
        TextButton mapEditorButton = tbf.create(mapEditorButtonPosition, "Map Editor");
        TextButton exitButton = tbf.create(exitButtonPosition, "Exit");

        playButton.setClickListener(b -> Game.getInstance().getSceneManager().setScene(new LevelScene(Paths.get("./test.gug"))));
        mapEditorButton.setClickListener(b -> Game.getInstance().getSceneManager().setScene(new MapEditorScene()));
        exitButton.setClickListener(b -> System.exit(0));

        addUIComponent(playButton);
        addUIComponent(mapEditorButton);
        addUIComponent(exitButton);
    }

}
