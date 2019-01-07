package com.mycompany.a3;

public abstract class Ship extends MoveableObject {

    private int missileCount;

    public void setMissileCount(int count) {
        missileCount = count;
    }

    public int getMissileCount() {
        return missileCount;
    }

}