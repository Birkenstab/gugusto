package game.object;

import collision.BoundingBox;
import game.Game;
import input.InputUtils;
import input.KeyState;
import input.event.InputEventType;
import input.event.KeyEvent;
import input.event.MouseEvent;
import util.Vector;

import java.util.List;


public class Player extends Circle {

    private BoundingBox targetLocation;

    public Player(){
        super(100, 200);

        velocity = new Vector();

        init();
    }

    private void init(){
        Game.getInstance().getInputSystem().addListener(InputEventType.KEY_DOWN, this::onKeyDown);
        Game.getInstance().getInputSystem().addListener(InputEventType.KEY_UP, this::onKeyUp);
        Game.getInstance().getInputSystem().addListener(InputEventType.MOUSE_CLICK, this::onMouseClick);
    }

    private void onKeyDown(KeyEvent event){
        if(targetLocation != null && "wasd".indexOf(event.getChar()) != -1) clearTarget();

        if(event.getChar() == 'w') velocity.setY(1);
        else if(event.getChar() == 's') velocity.setY(-1);
        else if(event.getChar() == 'a') velocity.setX(1);
        else if(event.getChar() == 'd') velocity.setX(-1);
    }

    private void onKeyUp(KeyEvent event){
        if(event.getChar() == 'w'){
            if(KeyState.isDown('s')) velocity.setY(-1);
            else velocity.setY(0);
        } else if(event.getChar() == 's'){
            if(KeyState.isDown('w')) velocity.setY(1);
            else velocity.setY(0);
        } else if(event.getChar() == 'a'){
            if(KeyState.isDown('d')) velocity.setX(-1);
            else velocity.setX(0);
        } else if(event.getChar() == 'd'){
            if(KeyState.isDown('a')) velocity.setX(1);
            else velocity.setX(0);
        }
    }

    private void onMouseClick(MouseEvent event){
        velocity = InputUtils.getRelativeMouseDirection(boundingBox.getPosition());
        targetLocation = new BoundingBox(new Vector(event.getX(), event.getY()), speed / 60);
    }

    @Override
    public void update(double delta){
        move(delta);

        if(targetLocation != null ){
            if(Game.getInstance().getCollisionSystem().isPointInCircle(boundingBox.getPosition(), targetLocation)){
                clearTarget();
            }
        }

        List<IGameObject> objects = Game.getInstance().getCollisionSystem().getCollisions(this);
        for(IGameObject object : objects){
            object.setActive(false);
        }
    }

    private void clearTarget(){
        targetLocation = null;
        velocity = new Vector();
    }

}
