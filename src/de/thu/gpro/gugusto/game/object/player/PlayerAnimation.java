package de.thu.gpro.gugusto.game.object.player;

import de.thu.gpro.gugusto.game.object.Direction;
import de.thu.gpro.gugusto.graphic.SpriteSheet;
import de.thu.gpro.gugusto.graphic.Texture;
import de.thu.gpro.gugusto.graphic.TextureLoader;
import de.thu.gpro.gugusto.graphic.animation.SpriteAnimation;

import java.awt.*;
import java.awt.image.BufferedImage;

class PlayerAnimation {

    private static final BufferedImage idleImage = TextureLoader.get(Texture.PLAYER_IDLE);
    private static final BufferedImage jumpFallImage = TextureLoader.get(Texture.PLAYER_JUMP_FALL);
    private static final BufferedImage walkImage = TextureLoader.get(Texture.PLAYER_WALK);

    private static final BufferedImage[] idleFrames = SpriteSheet.extract(idleImage, 11, 1, 100, 80);
    private static final BufferedImage[] walkAnimationFrames = SpriteSheet.extract(walkImage, 18, 1, 100, 80);
    private static final BufferedImage[] jumpFallFrames = SpriteSheet.extract(jumpFallImage, 2, 1, 100, 80);

    static final BufferedImage TEXTURE = idleFrames[0];

    private PlayerState state;
    private SpriteAnimation currentAnimation;
    private SpriteAnimation idleAnimation;
    private SpriteAnimation walkAnimation;
    private BufferedImage jumpImage;
    private BufferedImage fallImage;

    PlayerAnimation(PlayerState state){
        this.state = state;

        initializeAnimations();
    }

    private void initializeAnimations(){
        jumpImage = jumpFallFrames[0];
        fallImage = jumpFallFrames[1];
        idleAnimation = new SpriteAnimation(idleFrames, 500);
        idleAnimation.setAfterLoopSleepTime(1500);
        walkAnimation = new SpriteAnimation(walkAnimationFrames, 600);
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
