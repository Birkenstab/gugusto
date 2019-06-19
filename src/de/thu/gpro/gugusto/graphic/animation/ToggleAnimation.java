package de.thu.gpro.gugusto.graphic.animation;

public class ToggleAnimation extends Animation {

    private boolean toggle = false;

    public ToggleAnimation(int duration){
        setDuration(duration);
        setAfterLoopSleepTime(duration);
        start();
    }

    @Override
    protected void updatePerDuration(double delta, long now){
        long timeDiff = now - animationStart;
        toggle = true;
        if(timeDiff >= duration) checkModeEnding();
    }

    @Override
    public void reset() {
        toggle = false;
    }

    public boolean isToggled(){
        return toggle;
    }

}
