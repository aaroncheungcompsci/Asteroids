package com.mycompany.a3;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point2D;

import java.util.Random;

public class Asteroid extends MoveableObject implements IDrawable, ICollider {

    private int size;

    public Asteroid(Point2D location, int color) {
        Random r = new Random();
        size = r.nextInt(30) + 30; //range between 30 - 60, adjust values for asteroid size
        setSpeed();
        setDirection();
        setLocation(location);
        setColor(color);
    }

    @Override
    public int getSize() {
        return size;
    }

    public void draw(Graphics g, Point2D component) {
        g.setColor(this.getColor());
        double oldX = this.getLocation().getX();
        double oldY = this.getLocation().getY();
        double newX = component.getX() + oldX;
        double newY = component.getY() + oldY;
        if(isSelected()) {
            g.fillArc((int)newX, (int)newY, getSize(), getSize(), 0, 360);
        } else {
            g.drawArc((int) newX, (int) newY, getSize(), getSize(), 0, 359);
        }
    }

    public boolean contains(Point2D ptr, Point2D component) {
        double pX = ptr.getX();
        double pY = ptr.getY();
        double xLoc = component.getX() + this.getLocation().getX();
        double yLoc = component.getY() + this.getLocation().getY();

        return pX >= xLoc && pX <= xLoc + getSize() && pY >= yLoc && pY <= yLoc + getSize();
    }

    public boolean collidesWith (ICollider object) {
        GameObject gObject = (GameObject) object;
        int centerX = (int) (this.getLocation().getX() + (this.getSize()/2.0));
        int centerY = (int) (this.getLocation().getY() + (this.getSize()/2.0));
        int otherCenterX = (int) gObject.getLocation().getX() + gObject.getSize()/2;
        int otherCenterY = (int) gObject.getLocation().getY() + gObject.getSize()/2;
        int dx = centerX - otherCenterX;
        int dy = centerY - otherCenterY;
        int distBetweenCenters = (dx * dx) + (dy * dy);
        int thisRadius = this.getSize()/2;
        int otherRadius = gObject.getSize()/2;
        int radiiSquare = (thisRadius*thisRadius + 2*thisRadius*otherRadius + otherRadius*otherRadius);

        return distBetweenCenters <= radiiSquare;
    }

    public void handleCollision (ICollider object) {
        if (object instanceof PS) {
            this.setFlag(true);
            this.setPSFlag(true);
        } else if (object instanceof NPS) {
            this.setFlag(true);
            this.setNPSFlag(true);
        } else if (object instanceof Asteroid) {
            this.setFlag(true);
            this.setAsteroidFlag(true);
        } else if (object instanceof Missile) {
            this.setFlag(true);
            this.setMissileFlag(true);
        } else if (object instanceof MissileNPS) {
            this.setFlag(true);
            this.setNPSMissileFlag(true);
        } else if (object instanceof SpaceStation) {
            this.setFlag(false);
            this.setSSFlag(false);
        }
    }

    @Override
    public String toString() {
        String parentDesc = super.toString();
        String myDesc = " size:" + getSize();
        return "Asteroid: " + parentDesc + myDesc;
    }

}
