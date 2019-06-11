package graphic.animation;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class RotationAnimation {

    public enum Anchor { DEFAULT, CENTER };

    private BufferedImage image;
    private double speed;
    private Anchor anchor;
    private double rotation = 0;

    public RotationAnimation(BufferedImage image, double speed){
        this(image, speed, Anchor.DEFAULT);
    }

    public RotationAnimation(BufferedImage image, double speed, Anchor anchor){
        this.image = image;
        this.speed = speed;
        this.anchor = anchor;
    }

    public void update(double delta){
        rotation += delta * speed;
    }

    public void draw(Graphics2D g2d, int x, int y, int width, int height) {
        AffineTransform transform = new AffineTransform();
        AffineTransform old = g2d.getTransform();

        transform.rotate(rotation, x + width / 2f, y + height / 2f);
        g2d.transform(transform);
        g2d.drawImage(image, x, y, width, height, null);
        g2d.setTransform(old);
    }

}
