package de.thu.gpro.gugusto.game;

import de.thu.gpro.gugusto.game.level.Level;
import de.thu.gpro.gugusto.graphic.GraphicSystem;
import de.thu.gpro.gugusto.graphic.Window;
import de.thu.gpro.gugusto.input.InputSystem;
import de.thu.gpro.gugusto.scene.SceneManager;
import de.thu.gpro.gugusto.scene.scenes.level.LevelScene;
import de.thu.gpro.gugusto.scene.scenes.startmenu.StartMenuScene;

public class Game {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static final int TARGET_FRAME_TIME = 1000 / 60;

    public static Window WINDOW = new Window(Game.WIDTH, Game.HEIGHT);

    public static int INNER_WIDTH = (int)(WIDTH - WINDOW.getTotalInset().getX());
    public static int INNER_HEIGHT = (int)(HEIGHT - WINDOW.getTotalInset().getY());

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
        graphicSystem = new GraphicSystem(WINDOW);
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
            int count = 8;
            for (int i = 0; i < count; i++)
                sceneManager.update(delta / count);
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

    public Level getCurrentLevel() {
        return sceneManager.getScene().getLevelLayer().getLogic().getLevel();
    }

    public Camera getCamera() {
        return sceneManager.getScene().getLevelLayer().getCamera();
    }
}
