package scene.scenes.startmenu;

import game.Game;
import scene.UILayer;
import scene.scenes.level.LevelScene;
import scene.scenes.mapeditor.MapEditorScene;
import ui.components.IconButton;
import ui.icon.Icon;
import util.Vector;

public class StartMenuLayer extends UILayer {

    public StartMenuLayer(){
        int buttonCenterY = (Game.HEIGHT - Icon.SIZE) / 2;
        int centerX = Game.WIDTH / 2;
        int buttonMargin = 10;

        Vector playButtonPosition = new Vector(centerX - Icon.DEFAULT_DRAW_SIZE - buttonMargin, buttonCenterY);
        Vector mapEditorButtonPosition = new Vector(centerX + buttonMargin, buttonCenterY);
        IconButton playButton = new IconButton(playButtonPosition, Icon.ARROW_HEAD_RIGHT);
        IconButton mapEditorButton = new IconButton(mapEditorButtonPosition, Icon.SETTINGS);

        playButton.setClickListener(b -> Game.getInstance().getSceneManager().setScene(new LevelScene()));
        mapEditorButton.setClickListener(b -> Game.getInstance().getSceneManager().setScene(new MapEditorScene()));

        addUIComponent(playButton);
        addUIComponent(mapEditorButton);
    }

}
