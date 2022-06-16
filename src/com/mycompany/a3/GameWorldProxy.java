package com.mycompany.a3;

import java.util.Observable;

public class GameWorldProxy extends Observable implements IGameWorld {

    private final GameWorld gw;

    public GameWorldProxy (GameWorld gw) {
        this.gw = gw;
    }

    public int getPlayerScore() {
        return gw.getPlayerScore();
    }

    public int getPSMissileCount() {
        return gw.getPSMissileCount();
    }

    public int getLives() {
        return gw.getLives();
    }

    public int getClock() {
        return gw.getClock()/100;
    }

    public boolean getSound() {
        return gw.getSound();
    }

    public boolean isPaused() {
        return gw.isPaused();
    }

    public GameCollection getGameCollection() {
        return gw.getGameCollection();
    }

}
