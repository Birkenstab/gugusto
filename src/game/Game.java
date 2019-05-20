package game;

import collision.CollisionSystem;
import game.object.Circle;
import game.object.IGameObject;
import game.object.Player;
import graphic.GraphicSystem;
import input.InputSystem;

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
    private List<IGameObject> gameObjects;

    public static Game getInstance(){
        if(INSTANCE == null){
            synchronized (Game.class){
                if(INSTANCE == null){
                    INSTANCE = new Game();
                }
            }
        }
        return INSTANCE;
    }

    private Game(){
        INSTANCE = this;
        graphicSystem = new GraphicSystem();
        inputSystem = new InputSystem(graphicSystem.getWindow());
        collisionSystem = new CollisionSystem(this);
        gameObjects = new ArrayList<>();

        int minSize = 10;
        int maxSize = 60;

        int minSpeed = 200;
        int maxSpeed = 300;

        gameObjects.add(new Player());

        for(int i = 0; i < 50; i++){
            int size = (int)(Math.random() * (maxSize - minSize) + minSize);
            int speed = (int)(Math.random() * (maxSpeed - minSpeed) + minSpeed);
            gameObjects.add(new Circle(size, speed));
        }

        loop();
    }

    private void loop(){
        long lastFrame = System.currentTimeMillis();

        while(running){
            long currentFrame = System.currentTimeMillis();
            double delta = (lastFrame - currentFrame) / 1000f;
            lastFrame = currentFrame;

            inputSystem.dispatch();
            graphicSystem.draw(gameObjects);

            for(Iterator<IGameObject> iter = gameObjects.iterator(); iter.hasNext();){
                IGameObject object = iter.next();
                if(object.isActive()) object.update(delta);
                else iter.remove();
            }

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
    public List<IGameObject> getGameObjects(){
        return gameObjects;
    }
}
