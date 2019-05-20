package game.object;

abstract class BaseGameObject implements IGameObject {

    private boolean active = true;

    public void setActive(boolean active){
        this.active = active;
    }
    public boolean isActive(){
        return active;
    }

}
