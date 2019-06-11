package game.object.player;

import game.object.Direction;

class PlayerState {

    private Player.State state = Player.State.IDLE;
    private Direction direction = Direction.NONE;

    PlayerState(){

    }

    void setState(Player.State state){
        this.state = state;
    }

    Player.State getState(){
        return state;
    }

    void setDirection(Direction direction){
        this.direction = direction;
    }

    Direction getDirection(){
        return direction;
    }

    @Override
    public String toString(){
        String stateName = "Idle";
        String directionName = "None";

        if(state == Player.State.WALK) stateName = "walk";
        else if(state == Player.State.JUMP) stateName = "jump";
        else if(state == Player.State.FALL) stateName = "fall";

        if(direction == Direction.LEFT) directionName = "Left";
        else if(direction == Direction.RIGHT) directionName = "Right";

        return stateName + " " + directionName;
    }

}
