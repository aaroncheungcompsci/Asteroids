package com.mycompany.a3;
import java.util.Random;

public abstract class FixedObject extends GameObject {

    private int ID;

    public FixedObject(int ID) {
        this.ID = ID;
    }

    public String toString() {
        String parentDesc = super.toString();
        String myDesc = ""; //ID number
        return parentDesc + myDesc;
    }

}
