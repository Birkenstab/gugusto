package scene.scenes.startmenu;

import game.Game;
import scene.UILayer;
import scene.scenes.level.LevelScene;
import scene.scenes.mapeditor.MapEditorScene;
import ui.components.button.TextButton;
import ui.components.button.TextButtonFactory;
import ui.icon.Icon;
import util.Vector;

import java.awt.*;

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

        playButton.setClickListener(b -> Game.getInstance().getSceneManager().setScene(new LevelScene()));
        mapEditorButton.setClickListener(b -> Game.getInstance().getSceneManager().setScene(new MapEditorScene()));
        exitButton.setClickListener(b -> System.exit(0));

        addUIComponent(playButton);
        addUIComponent(mapEditorButton);
        addUIComponent(exitButton);
    }

}
