package de.thu.gpro.gugusto.game.level;

public class Profile {

    private int lastUnlockedLevelId;

    public Profile(){
        lastUnlockedLevelId = 0;
    }

    public Profile(int lastUnlockedLevelId){
        this.lastUnlockedLevelId = lastUnlockedLevelId;
    }

    public int getLastUnlockedLevelId(){
        return lastUnlockedLevelId;
    }

    public void unlockNextLevel(){
        lastUnlockedLevelId++;
    }

}
