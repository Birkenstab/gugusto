package game.object.player;

import game.Camera;
import game.object.Direction;
import game.object.DynamicGameObject;
import game.object.GameObject;
import game.object.blocks.GoalBlock;
import game.object.enemies.Saw;
import input.event.KeyEvent;
import util.Size;
import util.Vector;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends DynamicGameObject {

    public enum State { IDLE, WALK, JUMP, FALL }

    private static final Size size = new Size(1, 1);

    private PlayerAnimation animation;
    private PlayerState state;
    private boolean spaceDown = false;
    private boolean alive = true;

    public Player(Vector position){
        super(position, size);

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

        int step = 5;

        if(state.getDirection() == Direction.LEFT){
            boundingBox.getPosition().add(new Vector(- delta * step, 0));
        } else if(state.getDirection() == Direction.RIGHT){
            boundingBox.getPosition().add(new Vector(delta * step, 0));
        }

        if(spaceDown && isOnGround()) getVelocity().setY(-18);

        if(!isOnGround()){
            if(getVelocity().getY() > 0) state.setState(State.FALL);
            else state.setState(State.JUMP);
        } else if(state.getState() == State.FALL){
            if(state.getDirection() == Direction.NONE) state.setState(State.IDLE);
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
            System.out.println("Jo du hast das Ziel erreicht!");
        } else if(other instanceof Saw){
            System.out.println("Sääääääääääääääääge");
            alive = false;
        }
    }

    public void reset(){
        setOnGround(true);
        setVelocity(new Vector());
        alive = true;
    }

    public boolean isAlive(){
        return alive;
    }

}
