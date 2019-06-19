package de.thu.gpro.gugusto.graphic.animation;

import java.awt.*;

public abstract class Animation {

    public enum Mode {
        LINEAR,
        LINEAR_RESET,
        LOOP,
    }

    private long lastDurationFrame;
    private long lengthBeforePause;

    protected long animationStart;
    protected boolean afterLoopSleeping = false;

    protected Mode mode;
    protected boolean reverse = false;
    protected boolean running = false;
    protected int duration;
    protected int afterLoopSleepTime = 0;

    public Animation(){
        this(Mode.LOOP);
    }

    public Animation(Mode mode){
        this.mode = mode;
        lastDurationFrame = System.currentTimeMillis();
    }

    public final void update(double delta){
        if(running){
            long now = System.currentTimeMillis();

            if(!afterLoopSleeping){
                if(now - lastDurationFrame >= duration){
                    updatePerDuration(delta, now);
                    lastDurationFrame = now;
                }

                updatePerFrame(delta, now);
            } else if(now - animationStart >= afterLoopSleepTime){
                afterLoopSleeping = false;
                animationStart = now;
            }
        }
    }

    protected final void enterAfterLoopSleep(){
        animationStart = System.currentTimeMillis();
        afterLoopSleeping = true;
    }

    protected void updatePerDuration(double delta, long now){}
    protected void updatePerFrame(double delta, long now){}

    public abstract void draw(Graphics2D g2d, int x, int y, int width, int height);

    public void start(){
        reset();
        running = true;
        animationStart = System.currentTimeMillis();
    }

    public void resume(){
        running = true;
        animationStart = System.currentTimeMillis() - lengthBeforePause;
    }

    public void pause(){
        running = false;
        lengthBeforePause = System.currentTimeMillis() - animationStart;
    }

    public void stop(){
        running = false;
        if(mode == Mode.LINEAR_RESET) reset();
    }

    public abstract void reset();

    public void setMode(Mode mode){
        this.mode = mode;
    }

    public Mode getMode(){
        return mode;
    }

    public void setReverse(boolean reverse){
        this.reverse = reverse;
    }

    public boolean isReverse(){
        return reverse;
    }

    public void setDuration(int duration){
        this.duration = duration;
    }

    public int getDuration(){
        return duration;
    }

    public void setAfterLoopSleepTime(int sleepTime){
        afterLoopSleepTime = sleepTime;
    }

    public int getAfterLoopSleepTime(){
        return afterLoopSleepTime;
    }

}
