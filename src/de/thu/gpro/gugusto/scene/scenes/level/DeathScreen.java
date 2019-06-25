package de.thu.gpro.gugusto.scene.scenes.level;

import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.input.event.InputEventType;
import de.thu.gpro.gugusto.input.event.KeyEvent;
import de.thu.gpro.gugusto.ui.components.FadingLabel;
import de.thu.gpro.gugusto.ui.components.Label;
import de.thu.gpro.gugusto.ui.components.Panel;
import de.thu.gpro.gugusto.util.Size;
import de.thu.gpro.gugusto.util.Vector;

import java.awt.*;

public class DeathScreen extends Panel {
    private FadingLabel deathLabel = new FadingLabel("You died", new Color(255, 0, 0));
    private Label description = new Label(new Vector(0, Game.INNER_HEIGHT / 2 + 20), "Press space to retry", Game.INNER_WIDTH, new Vector(0, 0), new Font("Arial", Font.PLAIN, 25), Label.Alignment.CENTER);
    private double elapsedTime = 0;

    public DeathScreen(LevelAction levelAction) {
        super(new Vector(), new Size(Game.INNER_WIDTH, Game.INNER_HEIGHT));

        description.setColor(Color.GRAY);

        addUIComponent(deathLabel);
        addUIComponent(description);

        addListener(InputEventType.KEY_DOWN, (KeyEvent event) -> {
            if (isVisible() && elapsedTime > 0.5) { // 0.5s Wartezeit bevor man restarten kann
                if (event.getChar() == KeyEvent.VK_SPACE) {
                    levelAction.restartLevel();
                    setVisible(false);
                    return true;
                }
            }

            return false;
        });

        setVisible(false);
    }

    public void show() {
        setVisible(true);
        description.setVisible(false);
        elapsedTime = 0;
    }

    @Override
    public void update(double delta) {
        super.update(delta);
        elapsedTime += delta;
        if (isVisible() && elapsedTime > 0.7)
            description.setVisible(true);
    }
}
