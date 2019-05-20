package game.object;

abstract class BaseGameObject implements IGameObject {

    private boolean shouldRemove = false;

    public void remove(){
        shouldRemove = true;
    }

    @Override
    public boolean shouldBeRemoved(){
        return shouldRemove;
    }

}
