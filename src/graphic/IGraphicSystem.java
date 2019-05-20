package graphic;

import game.object.IGameObject;

import java.util.List;

public interface IGraphicSystem {

    void draw(List<IGameObject> gameObjects);

    Window getWindow();

}
