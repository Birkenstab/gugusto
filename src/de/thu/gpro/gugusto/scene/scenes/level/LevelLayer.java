package de.thu.gpro.gugusto.scene.scenes.level;

import de.thu.gpro.gugusto.game.Camera;
import de.thu.gpro.gugusto.game.level.io.LevelLoader;
import de.thu.gpro.gugusto.game.level.io.LevelUtil;
import de.thu.gpro.gugusto.input.event.InputEventType;
import de.thu.gpro.gugusto.input.event.KeyEvent;
import de.thu.gpro.gugusto.scene.Layer;
import de.thu.gpro.gugusto.game.level.Level;

import java.util.List;
import java.awt.*;
import java.nio.file.Path;


public class LevelLayer extends Layer {

    private Path currentLevelPath;
    private LevelLogic logic;
    private LevelAction levelAction;
    private boolean aiMode;

    public LevelLayer(Path level, LevelAction levelAction) {
        this(level, levelAction, false);
    }

    public LevelLayer(Path level, LevelAction levelAction, boolean aiMode) {
        currentLevelPath = level;
        this.levelAction = levelAction;
        this.aiMode = aiMode;

        loadLevel();
        bindListeners();
    }

    public void restartLevel() {
        loadLevel();
        levelAction.getCoinLabel().setCoins(0);
    }

    public void pause() {
        logic.pause();
    }

    public void resume() {
        logic.resume();
    }

    private void loadLevel() {
        Level level;
        if (aiMode) {
            List<Path> levels = LevelUtil.getPlayableLevels();
            level = LevelLoader.load(levels.get((int)(Math.random() * levels.size())));
        } else {
            level = LevelLoader.load(currentLevelPath);
        }
        this.logic = new LevelLogic(level, levelAction, aiMode);
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
    public Camera getCamera() {
        return logic.getCamera();
    }

    public LevelLogic getLogic() {
        return logic;
    }
}
