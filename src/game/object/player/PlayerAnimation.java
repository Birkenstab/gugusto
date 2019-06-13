package game.object.player;

import game.object.Direction;
import graphic.SpriteSheet;
import graphic.animation.SpriteAnimation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class PlayerAnimation {

    private static BufferedImage idleImage;
    private static BufferedImage jumpFallImage;
    private static BufferedImage walkImage;

    private SpriteAnimation currentAnimation;

    static {
        File idleFile = new File(".\\Gugusto Graphics\\idle.png");
        File jumpFallFile = new File(".\\Gugusto Graphics\\jumpfall.png");
        File walkFile = new File(".\\Gugusto Graphics\\walk.png");

        try {
            idleImage = ImageIO.read(idleFile);
            jumpFallImage = ImageIO.read(jumpFallFile);
            walkImage = ImageIO.read(walkFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private PlayerState state;
    private SpriteAnimation idleAnimation;
    private SpriteAnimation walkAnimation;
    private BufferedImage jumpImage;
    private BufferedImage fallImage;

    PlayerAnimation(PlayerState state){
        this.state = state;

        initializeAnimations();
    }

    private void initializeAnimations(){
        BufferedImage[] jumpfall = SpriteSheet.extract(jumpFallImage, 2, 1, 76, 78);
        jumpImage = jumpfall[0];
        fallImage = jumpfall[1];
        idleAnimation = new SpriteAnimation(SpriteSheet.extract(idleImage, 11, 1, 72, 74), 1000);
        idleAnimation.setAfterLoopSleepTime(1500);
        walkAnimation = new SpriteAnimation(SpriteSheet.extract(walkImage, 6, 1, 76, 76), 1000);
        currentAnimation = idleAnimation;
    }

    void update(double delta){
        SpriteAnimation lastAnimation = currentAnimation;

        if(state.getState() == Player.State.IDLE) currentAnimation = idleAnimation;
        else if(state.getState() == Player.State.WALK) currentAnimation = walkAnimation;
        else currentAnimation = null;

        if(lastAnimation != currentAnimation){
            if(lastAnimation != null) lastAnimation.stop();
            if(currentAnimation != null) currentAnimation.start();
        }

        if(currentAnimation != null) currentAnimation.update(delta);
    }

    void draw(Graphics2D g2d, int x, int y, int width, int height) {
        boolean flip = state.getDirection() == Direction.LEFT;
        int calcWidth = flip ? -width : width;
        int calcX = flip ? x + width : x;

        if (currentAnimation != null) currentAnimation.draw(g2d, calcX, y, calcWidth, height);
        else if (state.getState() == Player.State.JUMP) g2d.drawImage(jumpImage, calcX, y, calcWidth, height, null);
        else if (state.getState() == Player.State.FALL) g2d.drawImage(fallImage, calcX, y, calcWidth, height, null);
    }
}
