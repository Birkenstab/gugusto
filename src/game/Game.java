package game;

import game.object.blocks.*;
import graphic.GraphicSystem;
import input.InputSystem;
import scene.SceneManager;
import util.Vector;

public class Game {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int TARGET_FRAME_TIME = 1000 / 60;

    private static volatile Game INSTANCE;

    private boolean running = true;
    private GraphicSystem graphicSystem;
    private InputSystem inputSystem;
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
        sceneManager = new SceneManager();
    }

    public void run(){
        long lastFrame = System.currentTimeMillis();

        while(running){
            long currentFrame = System.currentTimeMillis();
            double delta = (currentFrame - lastFrame) / 1000f;
            lastFrame = currentFrame;

            inputSystem.dispatch();
            sceneManager.update(delta);
            sceneManager.draw(graphicSystem);

            long elapsedTime = System.currentTimeMillis() - lastFrame;

            if(elapsedTime < TARGET_FRAME_TIME){
                try {
                    Thread.sleep(TARGET_FRAME_TIME - elapsedTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public SceneManager getSceneManager(){
        return sceneManager;
    }
}
