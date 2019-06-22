package de.thu.gpro.gugusto.game.object.blocks;

import de.thu.gpro.gugusto.collision.BoundingBox;
import de.thu.gpro.gugusto.game.Camera;
import de.thu.gpro.gugusto.game.object.player.WinState;
import de.thu.gpro.gugusto.graphic.SpriteSheet;
import de.thu.gpro.gugusto.graphic.Texture;
import de.thu.gpro.gugusto.graphic.TextureLoader;
import de.thu.gpro.gugusto.graphic.animation.Animation;
import de.thu.gpro.gugusto.graphic.animation.SpriteAnimation;
import de.thu.gpro.gugusto.util.Size;
import de.thu.gpro.gugusto.util.Vector;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class Chest extends Block {

    private static final BufferedImage[] ANIMATION_FRAMES = SpriteSheet.extract(TextureLoader.get(Texture.BLOCK_CHEST), 9, 1, 221, 224, 36, 0);
    public static final BufferedImage TEXTURE = ANIMATION_FRAMES[0];

    private SpriteAnimation openAnimation = new SpriteAnimation(ANIMATION_FRAMES, 1000);
    private GoalCoin coin;
    private Consumer<WinState> openCallback;

    public Chest(Vector position) {
        super(BlockType.CHEST, position, true, new Size(1, 1));
        drawBoundingBox = new BoundingBox(new Vector(-0.23, -0.5), new Size(1.5, 1.5));
        openAnimation.setMode(Animation.Mode.LINEAR);
        openAnimation.setDoneCallback(this::placeCoin);
    }

    @Override
    public void draw(Graphics2D g2d, Camera camera) {
        super.draw(g2d, camera);
        openAnimation.draw(g2d, getX(), getY(), getWidth(), getHeight());
        if (coin != null)
            coin.draw(g2d, camera);
    }

    @Override
    public void update(double delta) {
        super.update(delta);
        openAnimation.update(delta);
        if (coin != null) {
            coin.update(delta);
            if (coin.getElapsed() >= GoalCoin.ZOOM_TIME) {
                openCallback.accept(WinState.POST_ANIMATION);
            }
        }
    }

    public void open(Consumer<WinState> openCallback) {
        this.openCallback = openCallback;
        openAnimation.start();
    }

    private void placeCoin() {
        coin = new GoalCoin(boundingBox.getPosition().clone().add(new Vector(0.2, 0.2)));
        openCallback.accept(WinState.JUMP);
    }
}
