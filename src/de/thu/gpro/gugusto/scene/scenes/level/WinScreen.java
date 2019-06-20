package de.thu.gpro.gugusto.scene.scenes.level;

import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.ui.components.FadingLabel;
import de.thu.gpro.gugusto.ui.components.LabelFactory;
import de.thu.gpro.gugusto.ui.components.Panel;
import de.thu.gpro.gugusto.ui.components.button.TextButton;
import de.thu.gpro.gugusto.util.Size;
import de.thu.gpro.gugusto.util.Vector;

import java.awt.*;

public class WinScreen extends Panel {
    private FadingLabel deathLabel = new FadingLabel("Level abgeschlossen", new Color(0, 255, 0));
    private double elapsedTime;
    private Panel buttonsPanel;

    public WinScreen(LevelAction levelAction) {
        super(new Vector(), new Size(Game.INNER_WIDTH, Game.INNER_HEIGHT));

        addUIComponent(deathLabel);

        LabelFactory lf = new LabelFactory(250, new Vector(0, 6), 16);

        buttonsPanel = new Panel(new Vector(Game.INNER_WIDTH / 2, Game.INNER_HEIGHT / 2 + 30), new Size(100, 300));
        TextButton backButton = new TextButton(lf.create(new Vector(), "Zurück zur Levelauswahl"));
        backButton.getBoundingBox().getPosition().set(buttonsPanel.getBoundingBox().getPosition().clone().subtract(backButton.getBoundingBox().getSize().toVector().multiply(0.5)).add(new Vector(20, 20)));
        backButton.setClickListener(button -> levelAction.backToLevelSelector());
        buttonsPanel.addUIComponent(backButton);
        TextButton restartButton = new TextButton(lf.create(new Vector(0, 100), "Level neustarten"));
        restartButton.getBoundingBox().getPosition().set(backButton.getBoundingBox().getPosition().clone().add(new Vector(0, 50)));
        restartButton.setClickListener(button -> {
            levelAction.restartLevel();
            setVisible(false);
        });
        buttonsPanel.addUIComponent(restartButton);
        addUIComponent(buttonsPanel);

        setVisible(false);
    }

    public void show() {
        setVisible(true);
        buttonsPanel.setVisible(false);
        elapsedTime = 0;
    }

    @Override
    public void update(double delta) {
        super.update(delta);
        elapsedTime += delta;
        if (elapsedTime >= 0.7 && isVisible())
            showButtons();
    }

    private void showButtons() {
        buttonsPanel.setVisible(true);
    }
}
