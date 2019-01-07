package com.mycompany.a3;

public interface ICollider {

    boolean collidesWith(ICollider obj);
    void handleCollision(ICollider obj);

}
