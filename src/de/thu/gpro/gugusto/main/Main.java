package de.thu.gpro.gugusto.main;

import de.thu.gpro.gugusto.game.Game;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args){
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, Main.class.getResourceAsStream("/fonts/Mali-Bold.ttf")));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        Game.getInstance().run();
    }

}
