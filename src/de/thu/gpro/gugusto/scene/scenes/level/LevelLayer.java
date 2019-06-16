package de.thu.gpro.gugusto.scene.scenes.level;

import de.thu.gpro.gugusto.game.Camera;
import de.thu.gpro.gugusto.game.level.LevelLoader;
import de.thu.gpro.gugusto.input.event.InputEventType;
import de.thu.gpro.gugusto.input.event.KeyEvent;
import de.thu.gpro.gugusto.scene.Layer;
import de.thu.gpro.gugusto.game.level.Level;

import java.awt.*;
import java.nio.file.Path;


public class LevelLayer extends Layer {

    private Path currentLevelPath;
    private LevelLogic logic;
    private LevelAction levelAction;

    public LevelLayer(Path level, LevelAction levelAction) {
        currentLevelPath = level;
        this.levelAction = levelAction;

        loadLevel();
        bindListeners();
    }

    public void restartLevel() {
        loadLevel();
    }

    public void pause() {
        logic.pause();
    }

    public void resume() {
        logic.resume();
    }

    private void loadLevel() {
        Level level = LevelLoader.load(currentLevelPath);
        this.logic = new LevelLogic(level, levelAction);
    }

    private void bindListeners() {
        addListener(InputEventType.KEY_DOWN, (KeyEvent event) -> logic.onKeyDown(event));
        addListener(InputEventType.KEY_UP, (KeyEvent event) -> logic.onKeyUp(event));
    }

    @Override
    public void update(double delta) {
        logic.update(delta);
    }

    @Override
    public void draw(Graphics2D g2d) {
        logic.draw(g2d);
    }


    @Override
    protected Camera getCamera() {
        return logic.getCamera();
    }

}
