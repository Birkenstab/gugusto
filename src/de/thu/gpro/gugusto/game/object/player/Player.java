package de.thu.gpro.gugusto.game.object.player;

import de.thu.gpro.gugusto.collision.BoundingBox;
import de.thu.gpro.gugusto.game.object.blocks.Chest;
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

    private static final Vector JUMP_VELOCITY = new Vector(0, -18);
    private static final Size size = new Size(0.8, 0.8);

    private PlayerAnimation animation;
    private PlayerState state;
    protected boolean spaceDown = false;
    private boolean alive = true;
    private WinState winState = WinState.NONE;

    public Player(Vector position){
        super(position, size.clone());
        drawBoundingBox = new BoundingBox(new Vector(-0.25, -0.2), new Size(1.25, 1));

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

        if (winState == WinState.NONE) {
            if (spaceDown && isOnGround())
                getVelocity().set(JUMP_VELOCITY);
        } else if (winState == WinState.WALKING) {
            state.setState(State.WALK);
            state.setDirection(Direction.RIGHT);

        } else {
            state.setState(State.IDLE);
            state.setDirection(Direction.NONE);
        }

        int step = 5;

        if (state.getDirection() == Direction.LEFT) {
            boundingBox.getPosition().add(new Vector(-delta * step, 0));
        } else if (state.getDirection() == Direction.RIGHT) {
            boundingBox.getPosition().add(new Vector(delta * step, 0));
        }

        if (!isOnGround()) {
            if (getVelocity().getY() > 0) state.setState(State.FALL);
            else state.setState(State.JUMP);
        } else if (state.getState() == State.FALL) {
            if (state.getDirection() == Direction.NONE) state.setState(State.IDLE);
            else state.setState(State.WALK);
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
            winState = WinState.WALKING;
        } else if(other instanceof Coin) {
            other.remove();
        }

        if (winState == WinState.WALKING) {
            if (other instanceof Chest) {
                ((Chest) other).open((state) -> {
                    winState = state;
                    if (winState == WinState.JUMP)
                        getVelocity().add(JUMP_VELOCITY);
                });
                winState = WinState.AT_CHEST;
            }
        }
    }

    @Override
    public void kill(GameObject by) {
        if (winState == WinState.NONE) {
            alive = false;
            remove();
        }
    }

    public boolean isAlive(){
        return alive;
    }

    public WinState getWinState() {
        return winState;
    }

    protected PlayerState getState() {
        return state;
    }
}
