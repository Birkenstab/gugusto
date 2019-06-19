package de.thu.gpro.gugusto.graphic.animation;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class RotationAnimation extends Animation {

    public enum Anchor { DEFAULT, CENTER };

    private BufferedImage image;
    private Anchor anchor;
    private double rotation = 0;
    private double maxAngle;

    public RotationAnimation(BufferedImage image, int duration){
        this(image, duration, Math.PI * 2, Anchor.DEFAULT);
    }

    public RotationAnimation(BufferedImage image, int duration, double maxAngle){
        this(image, duration, maxAngle, Anchor.DEFAULT);
    }

    public RotationAnimation(BufferedImage image, int duration, Anchor anchor){
        this(image, duration, Math.PI * 2, anchor);
    }

    public RotationAnimation(BufferedImage image, int duration, double maxAngle, Anchor anchor){
        this.image = image;
        this.duration = duration;
        this.maxAngle = maxAngle;
        this.anchor = anchor;
    }

    @Override
    public void updatePerFrame(double delta, long now){
        long timeDiff = now - animationStart;
        rotation = maxAngle * timeDiff / duration * (reverse ? -1 : 1);

        if(timeDiff >= duration) checkModeEnding();
    }

    @Override
    public void draw(Graphics2D g2d, int x, int y, int width, int height) {
        AffineTransform transform = new AffineTransform();
        AffineTransform old = g2d.getTransform();

        double anchorX = anchor == Anchor.DEFAULT ? x : (x + width / 2f);
        double anchorY = anchor == Anchor.DEFAULT ? y : (y + height / 2f);
        transform.rotate(rotation, anchorX, anchorY);

        g2d.transform(transform);
        g2d.drawImage(image, x, y, width, height, null);
        g2d.setTransform(old);
    }

    @Override
    public void reset(){
        rotation = 0;
    }

    public void setMaxAngle(double angle){
        maxAngle = angle;
    }

    public double getMaxAngle(){
        return maxAngle;
    }

}
