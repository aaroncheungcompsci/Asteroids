package com.mycompany.a3;
import com.codename1.location.Location;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point2D;
import com.codename1.charts.util.ColorUtil;
import java.util.Random;

public abstract class GameObject implements ISelectable {

    private Point2D location;
    private int color;

    private boolean isSelected;
    //private GameCollection collection;

    //flags for collisions
    private boolean flag;
    private boolean PSFlag;
    private boolean NPSFlag;
    private boolean AsteroidFlag;
    private boolean MissileFlag;
    private boolean NPSMissileFlag;
    private boolean SSFlag;
    private int size;
    private final Random r = new Random();

    protected GameObject() {
        location = new Point2D(randomCoord(0.0, 1024.0), randomCoord(0.0, 768.0));
        isSelected = false;
        flag = false;
    }

    public void setFlag (boolean bool) {
        flag = bool;
    }

    public void setPSFlag (boolean bool) {
        PSFlag = bool;
    }

    public void setNPSFlag (boolean bool) {
        NPSFlag = bool;
    }

    public void setAsteroidFlag (boolean bool) {
        AsteroidFlag = bool;
    }

    public void setMissileFlag (boolean bool) {
        MissileFlag = bool;
    }

    public void setNPSMissileFlag (boolean bool) {
        NPSMissileFlag = bool;
    }

    public void setSSFlag (boolean bool) {
        SSFlag = bool;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setSelected (boolean bool) {
        isSelected = bool;
    }

    public boolean isSelected () {
        return isSelected;
    }

    public int getSize() {
        return size;
    }

    public double getX() {
        return location.getX();
    }

    public double getY() {
        return location.getY();
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void setLocation(Point2D point) {
        location = point;
    }

    public Point2D getLocation() {
        return location;
    }

    public double randomCoord(double min, double max) {
        return min + (max - min) * r.nextDouble();
    }

    public String toString() {
        double x = Math.round(getLocation().getX());
        double y = Math.round(getLocation().getY());
        String locDesc = "loc:[" + x + ", " + y + "]";
        String colorDesc = " color:" + "[" +
                ColorUtil.red(getColor()) + "," +
                ColorUtil.green(getColor()) + "," +
                ColorUtil.blue(getColor()) + "]";

        return locDesc + colorDesc;
    }

    public boolean getFlag() {
        return this.flag;
    }

    public boolean getPSFlag() {
        return this.PSFlag;
    }

    public boolean getNPSFlag() {
        return this.NPSFlag;
    }

    public boolean getSpaceStationFlag() {
        return this.SSFlag;
    }

    public boolean getAsteroidFlag() {
        return this.AsteroidFlag;
    }

    public boolean getMissileFlag() {
        return this.MissileFlag;
    }

    public boolean getNPSMissleFlag() {
        return this.NPSMissileFlag;
    }
}
