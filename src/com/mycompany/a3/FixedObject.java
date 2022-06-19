package com.mycompany.a3;

public abstract class FixedObject extends GameObject {

    private int Id;

    protected FixedObject(int Id) {
        this.Id = Id;
    }

    @Override
    public String toString() {
        String parentDesc = super.toString();
        String myDesc = Integer.toString(Id); //ID number
        return parentDesc + myDesc;
    }

}
