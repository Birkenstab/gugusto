package game;

import collision.CollisionSystem;
import game.object.Circle;
import game.object.IGameObject;
import game.object.Player;
import graphic.GraphicSystem;
import input.InputSystem;
import scene.SceneManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Game {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private static volatile Game INSTANCE;

    private boolean running = true;
    private GraphicSystem graphicSystem;
    private InputSystem inputSystem;
    private CollisionSystem collisionSystem;
    private SceneManager sceneManager;

    public static Game getInstance(){
        if(INSTANCE == null){
            synchronized (Game.class){
                if(INSTANCE == null) new Game();
            }
        }
        return INSTANCE;
    }

    private Game(){
        INSTANCE = this;
        graphicSystem = new GraphicSystem();
        inputSystem = new InputSystem(graphicSystem.getWindow());
        collisionSystem = new CollisionSystem();
    }

    public void run(){
        long lastFrame = System.currentTimeMillis();

        while(running){
            long currentFrame = System.currentTimeMillis();
            double delta = (lastFrame - currentFrame) / 1000f;
            lastFrame = currentFrame;

            inputSystem.dispatch();
            sceneManager.update(delta);
            sceneManager.draw(graphicSystem);

            long remainingWaitingTime = System.currentTimeMillis() - lastFrame;

            if(remainingWaitingTime > 0 && remainingWaitingTime < 16){
                try {
                    Thread.sleep(16 - remainingWaitingTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public InputSystem getInputSystem(){
        return inputSystem;
    }
    public CollisionSystem getCollisionSystem(){
        return collisionSystem;
    }
}
