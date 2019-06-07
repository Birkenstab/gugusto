package scene.scenes.level;

import game.Camera;
import game.level.Level;

public class Action {

    private Level level;
    private Camera camera;

    public Action(Level level){
        this.level = level;
    }

    public void resetLevel(){
        level.getPlayer().getBoundingBox().getPosition().set(level.getStartPosition());
        camera.set(level.getCameraStartPosition(camera.getScaling()));
        level.getPlayer().reset();
    }

    public void setCamera(Camera camera){
        this.camera = camera;
    }

}
