package com.mycompany.a3;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point2D;

public class MissileLauncher extends MoveableObject implements ISteerable, IDrawable {

    public MissileLauncher(Point2D location, int speed, int direction, int color) {
        setLocation(location);
        setSpeed(speed);
        setDirection(direction);
        setColor(color);
    }

    public void steerLeft() {
        setDirection((getDirection() + 15) % 360);
    }

    public void steerRight() {
        //do nothing
    }

    public void setLocation(Point2D location) {
        super.setLocation(location);
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
        if (pX >= xLoc && pX <= xLoc + getSize() && pY >= yLoc && pY <= yLoc + getSize()) {
            return true;
        } else {
            return false;
        }
    }

}
