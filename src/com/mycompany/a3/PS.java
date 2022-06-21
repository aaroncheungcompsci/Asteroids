package com.mycompany.a3;
import com.codename1.ui.geom.Point2D;
import com.codename1.ui.Display;
import com.codename1.ui.Graphics;

public class PS extends Ship implements ISteerable, IDrawable, ICollider {

    private MissileLauncher launcher;

    private int size;
    //private static MapView gameScreen = new MapView();

    public PS(int speed, int direction, int color, Point2D location) {
        setMissileCount(10);
        setLocation(location);
        setSpeed(speed);
        setDirection(direction);
        setColor(color);
        size = 35;
        this.launcher = new MissileLauncher(location, speed, direction, color);
    }

    public void fire(Missile missile) {
        switch (getMissileCount()) {
            case 0:
                break;
            default:
                setMissileCount(getMissileCount() - 1);
                launcher.fire(missile);
                break;
        }
    }

    public void steerLeft() {
        setDirection((getDirection() + 22 % 360));
    }

    public void steerRight() {
        setDirection((getDirection() - 22 % 360));
    }

    public void steerLauncher() {
        launcher.steerLeft();
    }

    public int getSize() {
        return size;
    }

    public void draw (Graphics g, Point2D component) {
        //this.move();
        //int mapOriginX = (int) component.getX();
        //int mapOriginY = (int) component.getY();
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
        int centerX = (int) (this.getLocation().getX() + (getSize()/2));
        int centerY = (int) (this.getLocation().getY() + (getSize()/2));
        int otherCenterX = (int) gObject.getLocation().getX() + gObject.getSize()/2;
        int otherCenterY = (int) gObject.getLocation().getY() + gObject.getSize()/2;
        int dx = centerX - otherCenterX;
        int dy = centerY - otherCenterY;
        int distBetweenCenters = (dx * dx) + (dy * dy);
        int thisRadius = getSize()/2;
        int otherRadius = gObject.getSize()/2;
        int radiiSquare = (thisRadius*thisRadius + 2*thisRadius*otherRadius + otherRadius*otherRadius);

        if (distBetweenCenters <= radiiSquare) {
            return true;
        } else {
            return false;
        }
    }

    public void handleCollision(ICollider object) {
        if (object instanceof Asteroid) {
            this.setFlag(true);
            this.setAsteroidFlag(true);
        } else if (object instanceof MissileNPS) {
            this.setFlag(true);
            this.setNPSMissileFlag(true);
        } else if (object instanceof SpaceStation) {
            this.setFlag(false);
            this.setSSFlag(false);
            setMissileCount(10);
        } else if (object instanceof NPS) {
            this.setFlag(true);
            this.setNPSFlag(true);
        }
    }

    public String toString() {
        String parentDesc = super.toString();
        String myDesc = " missiles:" + getMissileCount() +
                " MissileLauncher dir:" + launcher.getDirection();
        return "Player Ship: " + parentDesc + myDesc;
    }
}
