package de.thu.gpro.gugusto.scene.scenes.credits;

import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.scene.UILayer;
import de.thu.gpro.gugusto.scene.scenes.startmenu.StartMenuScene;
import de.thu.gpro.gugusto.ui.components.MultilineLabel;
import de.thu.gpro.gugusto.ui.components.UIComponent;
import de.thu.gpro.gugusto.ui.components.button.BackToStartButton;
import de.thu.gpro.gugusto.util.BackgroundUtil;
import de.thu.gpro.gugusto.util.Size;
import de.thu.gpro.gugusto.util.Vector;

import java.awt.*;

public class CreditsLayer extends UILayer {
    private static final String CREDITS_TEXT = "GUGUSTO\n\n\n\n" +
            "DEVELOPED BY\n\n" +
            "Luca Nimmrichter\n" +
            "Moritz Beck\n" +
            "David Janoschka\n" +
            "Marvin Geier\n\n\n" +
            "Head of UI Design Operations\n" +
            "David Janoschka\n\n" +
            "Vice President of Management\n" +
            "Marvin Geier\n\n" +
            "Senior Executive Lead Programmer\n" +
            "Moritz Beck\n\n" +
            "Software Design Architecture Specialist\n" +
            "Luca Nimmrichter\n\n\n\n" +
            "TEXTURES\n\n" +
            "Kindly provided by\n" +
            "Free Platform Game Assets\n" +
            "by BAYAT GAMES on Unity Asset Store\n\n" +
            "Modified and adapted by\n" +
            "David Janoschka\n\n" +
            "Goal block texture from\n" +
            "SuperTux\n\n\n\n" +
            "LEVEL DESIGN\n\n" +
            "David Janoschka\n\n" +
            "Moritz Beck\n\n\n\n" +
            "BETA TESTER\n\n" +
            "Jochen Schmidt\n\n" +
            "Tobias Schröder\n\n\n\n" +
            "SPECIAL THANKS TO\n\n" +
            "Thorsten Hasbargen\n\n" +
            "Technische Hochschule Ulm\n\n\n\n" +
            "THANKS TO\n\n" +
            "Jetbrains\n\n" +
            "Github\n\n" +
            "Alphabet Inc.\n\n" +
            "Discord\n\n\n\n" +
            "This project was created as part\n" +
            "of the game programming lecture\n" +
            "of Computer Science at the THU\n" +
            "within 6 weeks\n\n\n\n" +
            "ADDITIONAL LINKS\n\n" +
            "Github Repo\n" +
            "github.com/Birkenstab/gugusto\n\n" +
            "Website of Moritz Beck\n" +
            "birkenstab.de\n\n\n\n" +
            "FEEDBACK BUGREPORTS ISSUES\n\n" +
            "gugusto@ultron.dev\n\n\n\n" +
            "THANKS FOR PLAYING!\n\n\n\n\n\n" +
            "Copyright 2019 Moritz Beck, Marvin Geier, Luca Nimmrichter, David Janoschka"; // TODO leveldesign, Sounds, Betatests, Tobi Leveldesign
    private double position;
    private MultilineLabel label;
    private MultilineLabel labelShadow;
    private double initialY = Game.INNER_HEIGHT + 20;

    public CreditsLayer() {
        addUIComponent(new UIComponent(new Vector(), new Size()) {
            @Override
            public void draw(Graphics2D g2d) {
                super.draw(g2d);
                BackgroundUtil.drawBackground(g2d, new Vector(0, position * 2));
            }
        });
        label = new MultilineLabel(new Vector(0, initialY), Game.INNER_WIDTH, CREDITS_TEXT, new Vector(10, 8), new Font("Arial", Font.BOLD, 32));
        label.setColor(Color.WHITE);
        labelShadow = new MultilineLabel(new Vector(2, initialY + 2), Game.INNER_WIDTH, CREDITS_TEXT, new Vector(10, 8), new Font("Arial", Font.BOLD, 32));
        labelShadow.setColor(new Color(0f, 0f, 0f, 0.4f));
        addUIComponent(labelShadow);
        addUIComponent(label);

        addUIComponent(new BackToStartButton());
    }

    @Override
    protected void update(double delta) {
        super.update(delta);
        position += delta;
        Vector labelPos = new Vector(0, initialY - position * 70);
        label.setPosition(labelPos);
        labelShadow.setPosition(new Vector(2, 2).add(labelPos));
        if (label.getBoundingBox().getPosition().getY() < - label.getBoundingBox().getSize().getHeight() - 100)
            Game.getInstance().getSceneManager().setScene(new StartMenuScene());
    }
}
