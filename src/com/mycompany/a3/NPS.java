package com.mycompany.a3;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point2D;

import java.util.Random;

public class NPS extends Ship implements IDrawable, ICollider {

    private MissileLauncherNPS launcher;
    private int size;

    public NPS(Point2D location, int direction, int color) {
        Random r = new Random();
        setMissileCount(2);
        setSpeed();
        launcher = new MissileLauncherNPS(location, this.getSpeed(), direction, color);
        size = randomVal(10, 20);
        setDirection();
        setLocation(location);
        setColor(color);
    }

    public void fire(Missile missile) {
        if (getMissileCount() == 0) {
            return;
        } else {
            setMissileCount(getMissileCount() - 1);
            launcher.fire(missile);
        }
    }

    public int getSize() {
        return size;
    }

    public void draw(Graphics g, Point2D component) {
        int currentX = (int) this.getLocation().getX();
        int currentY = (int) this.getLocation().getY();

        g.setColor(this.getColor());
        //creating a triangle
        Point2D top = new Point2D(component.getX() + currentX, component.getY() + currentY + getSize()/2); //half of height
        Point2D bottomLeft = new Point2D(component.getX() + currentX - getSize()/2, component.getY() + currentY - getSize()/2);
        Point2D bottomRight = new Point2D(component.getX() + currentX + getSize()/2,
                component.getY() + currentY - getSize()/2);

        if (isSelected()) {
            g.fillArc((int) top.getX(), (int) bottomRight.getY(),
                    getSize(), getSize(), 0, 360);
        } else {
            g.fillTriangle((int) top.getX()+20, (int) top.getY()+20, (int) bottomLeft.getX()+20, (int) bottomLeft.getY()+20,
                    (int) bottomRight.getX()+20, (int) bottomRight.getY()+20);
        }
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
            this.setPSFlag(true);
            this.setFlag(true);
        } else if (object instanceof Asteroid) {
            this.setAsteroidFlag(true);
            this.setFlag(true);
        } else if (object instanceof Missile) {
            this.setMissileFlag(true);
            this.setFlag(true);
        } else if (object instanceof SpaceStation) {
            //do nothing!
        } else if (object instanceof NPS) { //becareful with this one
            this.setNPSFlag(true);
            this.setFlag(true);
        } else if (object instanceof MissileNPS) {
            this.setNPSMissileFlag(true);
            this.setFlag(true);
        }
    }

    public String toString() {
        String parentDesc = super.toString();
        String myDesc = " missiles:" + getMissileCount();
        return parentDesc + myDesc;
    }

    private int randomVal(int min, int max) {
        Random r = new Random();
        return min + (max - min);
    }
}
