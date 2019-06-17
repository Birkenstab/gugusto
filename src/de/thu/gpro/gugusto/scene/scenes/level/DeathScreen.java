package de.thu.gpro.gugusto.scene.scenes.level;

import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.input.event.InputEventType;
import de.thu.gpro.gugusto.input.event.KeyEvent;
import de.thu.gpro.gugusto.ui.components.DeathLabel;
import de.thu.gpro.gugusto.ui.components.Panel;
import de.thu.gpro.gugusto.util.Size;
import de.thu.gpro.gugusto.util.Vector;

import java.awt.*;

public class DeathScreen extends Panel {
    private DeathLabel deathLabel = new DeathLabel("Du bist gestorben", new Font("Arial", Font.PLAIN, 50));
    private double elapsedTime = 0;

    // Todo Beschreibungstext dass Space = Restart; Hauptmenü-Knopf
    public DeathScreen(LevelAction levelAction) {
        super(new Vector(), new Size(Game.WIDTH, Game.HEIGHT));
        addUIComponent(deathLabel);

        addListener(InputEventType.KEY_DOWN, (KeyEvent event) -> {
            if (!isVisible())
                return false;

            if (elapsedTime > 0.5) { // 0.5s Wartezeit bevor man restarten kann
                if (event.getChar() == KeyEvent.VK_SPACE) {
                    levelAction.restartLevel();
                }
            }
            return true;
        });

        setVisible(false);
    }

    public void show() {
        setVisible(true);
        deathLabel.resetAnimation();
        elapsedTime = 0;
    }

    @Override
    public void update(double delta) {
        super.update(delta);
        elapsedTime += delta;
    }
}
