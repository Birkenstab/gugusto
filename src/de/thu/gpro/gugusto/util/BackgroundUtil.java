package de.thu.gpro.gugusto.util;

import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.graphic.Texture;
import de.thu.gpro.gugusto.graphic.TextureLoader;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class BackgroundUtil {

    public static void drawBackground(Graphics2D g2d, Vector playerPos) {
        double imageWidth = 1536;
        Vector position = playerPos.clone().multiply(-2);
        int multiplier = -(int) (position.getX() / imageWidth + 100) + 100;

        position.setY(position.getY() - 30);

        if (position.getY() < -144) {
            position.setY(-144);
        }
        if (position.getY() > 0)
            position.setY(0);


        // So ein Transform Dings damit wir auf Subpixel-Level translaten können, sonst sah es ziemlich ruckelig aus
        AffineTransform t = new AffineTransform();
        t.translate(imageWidth * (multiplier - 1)+ position.getX(), Game.WINDOW.getTopLeftInsets().getY() + position.getY());
        t.scale(0.8, 0.8);
        g2d.drawImage(TextureLoader.get(Texture.BACKGROUND), t, null);


        t.setToIdentity();
        t.translate(imageWidth * multiplier + position.getX(), Game.WINDOW.getTopLeftInsets().getY() + position.getY());
        t.scale(0.8, 0.8);
        g2d.drawImage(TextureLoader.get(Texture.BACKGROUND), t, null);
    }
}
