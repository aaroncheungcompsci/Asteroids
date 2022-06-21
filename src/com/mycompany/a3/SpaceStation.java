package com.mycompany.a3;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point2D;

import java.util.Random;
public class SpaceStation extends FixedObject implements IDrawable, ICollider {

    private int blinkRate;
    private boolean visible;
    private int size;

    public SpaceStation(Point2D point, int color, int ID) {
        super(ID);
        Random r = new Random();
        blinkRate = r.nextInt(4) + 1;
        setLocation(point);
        setColor(color);
        size = 50;
        visible = true;
    }

    public int getBlinkRate() {
        return blinkRate;
    }

    public void blink() {
        if (this.visible) {
            this.visible = false;
        } else {
            this.visible = true;
        }
    }

    public boolean getVisible() {
        return visible;
    }

    @Override
    public String toString() {
        String parentDesc = super.toString();
        String myDesc = " rate:" + getBlinkRate();
        return "Space Station: " + parentDesc + myDesc;
    }

    @Override
    public int getSize() {
        return size;
    }

    public void draw (Graphics g, Point2D component) {
        g.setColor(this.getColor());
        double oldXLoc = this.getLocation().getX();
        double oldYLoc = this.getLocation().getY();
        double xLoc = component.getX() + oldXLoc;
        double yLoc = component.getY() + oldYLoc;
        if (isSelected()) {
            g.fillArc((int) xLoc, (int) yLoc, getSize(), getSize(), 0, 360);
        } else {
            if (this.visible) {
                g.fillRoundRect((int) xLoc, (int) yLoc, getSize(), getSize(), 20, 10);
            }
            g.drawRoundRect((int)xLoc, (int)yLoc, getSize(), getSize(), 20, 10);
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

        return distBetweenCenters <= radiiSquare;
    }

    public void handleCollision(ICollider object) {
        if (object instanceof PS) {
            this.setPSFlag(false);
            this.setFlag(false);
            ((PS)object).setMissileCount(10);
        }
    }
}
