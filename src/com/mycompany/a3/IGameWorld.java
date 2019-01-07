package com.mycompany.a3;

public interface IGameWorld {
    //specifications here for all GameWorld Methods

    int getPlayerScore();
    int getPSMissileCount();
    int getLives();
    int getClock();
    boolean getSound();
    boolean isPaused();
    GameCollection getGameCollection();
}
