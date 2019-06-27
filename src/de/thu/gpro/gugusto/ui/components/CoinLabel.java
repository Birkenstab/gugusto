package de.thu.gpro.gugusto.ui.components;

import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.game.object.blocks.Coin;
import de.thu.gpro.gugusto.graphic.animation.SpriteAnimation;
import de.thu.gpro.gugusto.util.Size;
import de.thu.gpro.gugusto.util.Vector;

import java.awt.*;

public class CoinLabel extends Panel {

    private static Size size = new Size(110, 40);
    private static Vector position = new Vector(Game.INNER_WIDTH - size.getWidth(), 0);
    private static int padding = 4;
    private static int coinSize = (int)(size.getHeight() - padding * 2);
    private static int coinAnimationDuration = 1000;

    private SpriteAnimation animation = Coin.createAnimation();
    private Label label;
    private double coinCount = 0;

    private double startCoins;
    private double startTime;
    private double coinsToAdd;

    public CoinLabel(){
        super(position, size, new Color(0, 0, 0, 80));

        LabelFactory lf = new LabelFactory(160, new Vector(coinSize + 20, 10), 20, Label.Alignment.LEFT);
        label = lf.create(new Vector().add(position), "x0");
        label.setColor(Color.WHITE);

        animation.start();
    }

    public void addCoins(int amount){
        setCoins(coinCount + amount);
    }

    public void animateCoins(int amount){
        coinsToAdd = amount;
        startCoins = coinCount;
        startTime = System.currentTimeMillis();
    }

    public void setCoins(double count){
        coinCount = count;
        label.setText("x" + (int)coinCount);
    }

    @Override
    public void update(double delta){
        super.update(delta);

        if(coinsToAdd != 0){
            double diff = System.currentTimeMillis() - startTime;
            if(diff >= coinAnimationDuration){
                setCoins(startCoins + coinsToAdd);
                coinsToAdd = 0;
            } else {
                setCoins(startCoins + (1 - (coinAnimationDuration - diff) / coinAnimationDuration) * coinsToAdd);
            }
        }

        animation.update(delta);
    }

    @Override
    public void draw(Graphics2D g2d){
        super.draw(g2d);

        animation.draw(g2d, getX() + padding, getY() + padding, coinSize, coinSize);
        label.draw(g2d);
    }

}
