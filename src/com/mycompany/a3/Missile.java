package com.mycompany.a3;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point2D;

public class Missile extends MoveableObject implements IDrawable, ICollider {

    private int fuelLevel;
    private final int MAX_FUEL = 50;
    private int size;
    private final boolean playerFlag;

    public Missile(int direction, int speed, Point2D location, int color, int size, boolean playerFlag) {
        fuelLevel = MAX_FUEL;
        setDirection(direction);
        setSpeed(speed + 10);
        setLocation(location);
        setColor(color);
        this.size = size;
        this.playerFlag = playerFlag;
    }

    public void useFuel() {
        fuelLevel -= 1;
    }

    public int getFuel() {
        return fuelLevel;
    }

    public void refillFuel() {
        fuelLevel = MAX_FUEL;
    }

    @Override
    public String toString() {
        String parentDesc = super.toString();
        String myDesc = " fuel:" + fuelLevel;
        return "Missile" + parentDesc + myDesc;
    }

    public void draw (Graphics g, Point2D component) {
        g.setColor(this.getColor());
        double oldXLoc = this.getLocation().getX();
        double oldYLoc = this.getLocation().getY();
        double xLoc = component.getX()+ oldXLoc;// shape location relative
        double yLoc = component.getY()+ oldYLoc;
        if(isSelected()) { //selected
            g.fillRect((int) xLoc, (int) yLoc, size, size);
        } else {
            g.drawRect((int) xLoc, (int) yLoc, size, size);
        }
    }

    public boolean contains (Point2D ptr, Point2D component) {
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

    public boolean collidesWith (ICollider object) {
        GameObject gObject = (GameObject) object;
        int centerX = (int) (this.getLocation().getX() + (this.getSize()/2));
        int centerY = (int) (this.getLocation().getY() + (this.getSize()/2));
        int otherCenterX = (int) gObject.getLocation().getX() + gObject.getSize()/2;
        int otherCenterY = (int) gObject.getLocation().getY() + gObject.getSize()/2;
        int dx = centerX - otherCenterX;
        int dy = centerY - otherCenterY;
        int distBetweenCenters = (dx * dx) + (dy * dy);
        int thisRadius = this.getSize()/2;
        int otherRadius = gObject.getSize()/2;
        int radiiSquare = (thisRadius*thisRadius + 2*thisRadius*otherRadius + otherRadius*otherRadius);

        if (distBetweenCenters <= radiiSquare) {
            return true;
        } else {
            return false;
        }
    }

    public void handleCollision(ICollider object) {
        if (object instanceof PS) {
            this.setPSFlag(false);
            this.setFlag(false);
        } else if (object instanceof Asteroid) {
            this.setAsteroidFlag(true);
            this.setFlag(true);
        } else if (object instanceof Missile) {
            this.setMissileFlag(false);
            this.setFlag(false);
        } else if (object instanceof SpaceStation) {
            this.setSSFlag(false);
            this.setFlag(false);
        } else if (object instanceof NPS) {
            this.setNPSFlag(true);
            this.setFlag(true);
        }
    }

    public boolean belongsToPlayer() {
        return playerFlag;
    }
}
