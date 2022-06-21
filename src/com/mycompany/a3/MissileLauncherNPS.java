package com.mycompany.a3;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point2D;

public class MissileLauncherNPS extends MoveableObject implements IDrawable{

    public MissileLauncherNPS(Point2D location, int speed, int direction, int color) {
        setLocation(location);
        setSpeed(speed);
        setDirection(direction);
        setColor(color);
    }

    public void fire(Missile missile) {
        //missile is fired!
    }

    public void draw(Graphics g, Point2D component) {

    }

    public boolean contains(Point2D ptr, Point2D component) {
        double pX = ptr.getX();
        double pY = ptr.getY();
        double xLoc = component.getX() + this.getLocation().getX();
        double yLoc = component.getY() + this.getLocation().getY();

        return pX >= xLoc && pX <= xLoc + getSize() && pY >= yLoc && pY <= yLoc + getSize();
    }
}
