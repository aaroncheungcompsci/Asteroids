package com.mycompany.a3;

public abstract class FixedObject extends GameObject {

    private int ID;

    protected FixedObject(int ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        String parentDesc = super.toString();
        String myDesc = Integer.toString(ID); //ID number
        return parentDesc + myDesc;
    }

}
