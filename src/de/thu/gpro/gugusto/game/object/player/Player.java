package de.thu.gpro.gugusto.game.object.player;

import de.thu.gpro.gugusto.input.event.KeyEvent;
import de.thu.gpro.gugusto.util.Vector;
import de.thu.gpro.gugusto.game.Camera;
import de.thu.gpro.gugusto.game.object.Direction;
import de.thu.gpro.gugusto.game.object.DynamicGameObject;
import de.thu.gpro.gugusto.game.object.GameObject;
import de.thu.gpro.gugusto.game.object.blocks.Coin;
import de.thu.gpro.gugusto.game.object.blocks.GoalBlock;
import de.thu.gpro.gugusto.util.Size;

import java.awt.*;

public class Player extends DynamicGameObject {

    public enum State { IDLE, WALK, JUMP, FALL }

    private static final Size size = new Size(0.8, 0.8); // TODO Gezeichnete Größe sollte aber 1x1 sein

    private PlayerAnimation animation;
    private PlayerState state;
    private boolean spaceDown = false;
    private boolean alive = true;
    private boolean won = false;

    public Player(Vector position){
        super(position, size.clone());

        state = new PlayerState();
        animation = new PlayerAnimation(state);
    }

    public boolean onKeyDown(KeyEvent event){
        Direction direction = Direction.get(event.getChar());

        if(direction != Direction.NONE){
            state.setDirection(direction);
            if(isOnGround()) state.setState(State.WALK);
        } else if(event.getKeyCode() == 32){
            spaceDown = true;
        }

        return false;
    }

    public boolean onKeyUp(KeyEvent event){
        if(event.getKeyCode() == 32){
            spaceDown = false;
        } else if(Direction.get(event.getChar()) == state.getDirection()){
            state.setDirection(Direction.NONE);
            state.setState(State.IDLE);
        }

        return false;
    }

    @Override
    public void update(double delta){
        super.update(delta);

        if (won) {
            Size size = getBoundingBox().getSize();
            if (size.getWidth() < 4) {
                double change = 2 * delta;
                size.setWidth(size.getWidth() + change);
                size.setHeight(size.getHeight() + change);
                getBoundingBox().getPosition().subtract(new Vector(change / 2, change / 2));
            }
            if (isOnGround()) {
                getVelocity().setY(-5);
            }
            state.setState(State.JUMP);
            state.setDirection(Direction.RIGHT);

        } else {

            int step = 5;

            if (state.getDirection() == Direction.LEFT) {
                boundingBox.getPosition().add(new Vector(-delta * step, 0));
            } else if (state.getDirection() == Direction.RIGHT) {
                boundingBox.getPosition().add(new Vector(delta * step, 0));
            }

            if (spaceDown && isOnGround()) getVelocity().setY(-18);

            if (!isOnGround()) {
                if (getVelocity().getY() > 0) state.setState(State.FALL);
                else state.setState(State.JUMP);
            } else if (state.getState() == State.FALL) {
                if (state.getDirection() == Direction.NONE) state.setState(State.IDLE);
                else state.setState(State.WALK);
            }

        }

        animation.update(delta);
    }

    @Override
    public void draw(Graphics2D g2d, Camera camera) {
        super.draw(g2d, camera);
        animation.draw(g2d, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void collision(GameObject other) {
        super.collision(other);
        if (other instanceof GoalBlock) {
            won = true;
        } else if(other instanceof Coin) {
            other.remove();
        }
    }

    @Override
    public void kill(GameObject by) {
        if (!won) {
            alive = false;
            remove();
        }
    }

    public boolean isAlive(){
        return alive;
    }

    public boolean isWon() {
        return won;
    }
}
