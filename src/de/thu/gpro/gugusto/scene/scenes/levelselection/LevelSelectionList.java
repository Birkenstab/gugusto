package de.thu.gpro.gugusto.scene.scenes.levelselection;

import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.game.level.io.LevelUtil;
import de.thu.gpro.gugusto.scene.scenes.level.LevelScene;
import de.thu.gpro.gugusto.ui.components.Label;
import de.thu.gpro.gugusto.ui.components.LabelFactory;
import de.thu.gpro.gugusto.ui.components.Panel;
import de.thu.gpro.gugusto.ui.components.button.TextButton;
import de.thu.gpro.gugusto.util.Size;
import de.thu.gpro.gugusto.util.Vector;

import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class LevelSelectionList extends Panel {

    private static final Font font = new Font("Arial", Font.BOLD, 20);
    private static final Vector position = new Vector(100, 100);
    private static final Size size = new Size(Game.INNER_WIDTH - 200, Game.INNER_HEIGHT - 200);

    public LevelSelectionList(){
        super(position, size);
        build();
    }

    public void build(){
        List<Path> levelPaths = LevelUtil.getPlayableLevels();
        Vector bPos = new Vector(29, 0).add(position);

        for(int i = 0; i < levelPaths.size(); i++){
            TextButton textButton = buildButton(bPos.clone(), i, true);
            textButton.setTag(levelPaths.get(i).toString());
            addUIComponent(textButton);
            bPos.add(new Vector(75 + 29, 0));
            if((i + 1) % 10 == 0) bPos.add(new Vector(0, 79)).setX(29 + position.getX());
        }

    }

    private TextButton buildButton(Vector position, int number, boolean unlocked){
        LabelFactory lf = new LabelFactory(75, new Vector(0, 15), font);
        Label label = lf.create(position, String.format("%02d", number));
        TextButton textButton = new TextButton(label);
        textButton.setClickListener(b -> Game.getInstance().getSceneManager().setScene(new LevelScene(Paths.get(b.getTag()))));
        textButton.setDisabled(!unlocked);
        return textButton;
    }

}
