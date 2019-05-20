package graphic;

import game.object.GameObject;

import java.util.List;

public interface IGraphicSystem {

    void draw(List<GameObject> gameObjects);

    Window getWindow();

}
