package de.thu.gpro.gugusto.graphic.animation;

import java.awt.*;

public class ColorAlphaAnimation extends Animation {

    private Color originalColor;
    private Color color;

    public ColorAlphaAnimation(Color color, int duration){
        super(Mode.LINEAR);

        originalColor = color;
        this.color = color;
        setDuration(duration);
    }

    @Override
    protected void updatePerFrame(double delta, long now){
        long timeDiff = now - animationStart;
        int alpha = (int)(255f / duration * timeDiff);

        if(alpha < 256){
            color = new Color(originalColor.getRed(), originalColor.getGreen(), originalColor.getBlue(), alpha);
        } else {
            checkModeEnding();
        }
    }

    @Override
    public void reset() {
        color = originalColor;
    }

    public Color getColor(){
        return color;
    }

}
