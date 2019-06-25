package de.thu.gpro.gugusto.scene.scenes.leveleditor.selection;

import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.ui.components.Label;
import de.thu.gpro.gugusto.ui.components.LabelFactory;
import de.thu.gpro.gugusto.ui.components.Panel;
import de.thu.gpro.gugusto.ui.components.button.Button;
import de.thu.gpro.gugusto.ui.components.button.TextButton;
import de.thu.gpro.gugusto.util.Size;
import de.thu.gpro.gugusto.util.Vector;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfirmationPopup extends Panel {

    private static Size isize = new Size(320, 90);
    private static Vector iposition = new Vector((Game.INNER_WIDTH - isize.getWidth()) / 2, (Game.INNER_HEIGHT - isize.getHeight()) / 2);

    private static Vector position = new Vector();
    private static Size size = new Size(Game.INNER_WIDTH, Game.INNER_HEIGHT);
    private static Color backgroundColor = new Color(0, 0, 0, 150);

    private Path levelPath;
    private Button.OnClickListener callback;

    public ConfirmationPopup(){
        super(position, size, backgroundColor);

        LabelFactory lf0 = new LabelFactory(new Vector(), 16);
        LabelFactory lf1 = new LabelFactory(100, new Vector(0, 5), 16);

        Vector labelPosition = new Vector(10, 10).add(iposition);
        Vector yesPosition = new Vector(50, 50).add(iposition);
        Vector noPosition = new Vector(175, 50).add(iposition);

        Label label = lf0.create(labelPosition, "Are you sure you wanna delete this level?");
        Label yesLabel = lf1.create(yesPosition, "Yes");
        Label noLabel = lf1.create(noPosition, "No");

        TextButton yesButton = new TextButton(yesLabel);
        TextButton noButton = new TextButton(noLabel);

        yesButton.setClickListener(b -> {
            deleteFile();
            setVisible(false);
        });
        noButton.setClickListener(b -> setVisible(false));

        addUIComponent(new Panel(iposition, isize, Color.GRAY));
        addUIComponent(label);
        addUIComponent(yesButton);
        addUIComponent(noButton);

        setVisible(false);
    }

    @Override
    public void draw(Graphics2D g2d){
        g2d.setColor(backgroundColor);
        g2d.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

        super.draw(g2d);
    }

    private void deleteFile(){
        if(levelPath != null){
            try {
                Files.delete(levelPath);
                callback.onClick(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void prompt(Path levelPath){
        this.levelPath = levelPath;
        setVisible(true);
    }

    public void setCallback(Button.OnClickListener callback){
        this.callback = callback;
    }

}
