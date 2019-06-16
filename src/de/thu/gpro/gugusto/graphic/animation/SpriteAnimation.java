package de.thu.gpro.gugusto.graphic.animation;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpriteAnimation extends Animation {

    private BufferedImage[] frames;
    private int currentFrameIndex = 0;

    public SpriteAnimation(BufferedImage[] frames, int duration){
        this.frames = frames;
        this.duration = duration / frames.length;
    }

    @Override
    public void updatePerDuration(double delta, long now) {
        currentFrameIndex++;

        if(currentFrameIndex == frames.length){
            if(mode == Mode.LOOP){
                enterAfterLoopSleep();
                currentFrameIndex = 0;
            } else {
                stop();
            }
        }
    }

    @Override
    public void draw(Graphics2D g2d, int x, int y, int width, int height) {
        g2d.drawImage(frames[currentFrameIndex], x, y, width, height, null);
    }

    @Override
    public void reset(){
        currentFrameIndex = 0;
    }

}
