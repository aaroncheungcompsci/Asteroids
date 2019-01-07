package com.mycompany.a3;
import java.util.Random;
import com.codename1.ui.geom.Point2D;

public abstract class MoveableObject extends GameObject implements IMoveable {

    private int speed;
    private int direction;
    private Random r;

    public MoveableObject() {
        r = new Random();
    }

    protected int getSpeed() {
        return speed;
    }

    protected int getDirection() {
        return direction;
    }

    protected void setSpeed() {
        this.speed = r.nextInt(2) + 1;
    }

    protected void setDirection() {
        this.direction = r.nextInt(360);
    }

    //---------method overloading to set specific values if need be

    protected void setSpeed(int speed) {
        this.speed = speed;
    }

    protected void setDirection(int direction) {
        this.direction = direction;
    }

    public void move() {
        int heading = 90 - getDirection();
        Point2D point = new Point2D(getX() + Math.cos(heading * Math.PI/180) * getSpeed(),
                getY() + Math.sin(heading * Math.PI/180) * getSpeed());
        setLocation(point);

    }

    public String toString() {
        String parentDesc = super.toString();
        String myDesc = " speed:" + speed + " dir:" + direction;
        return parentDesc + myDesc;
    }
}
