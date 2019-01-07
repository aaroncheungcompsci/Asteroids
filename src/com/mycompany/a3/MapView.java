package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Point2D;
import com.codename1.ui.plaf.Border;

import java.util.Observable;
import java.util.Observer;

public class MapView extends Container implements Observer {
    private GameWorld gw;
    private GameCollection collection;

    public MapView(GameWorld gw) {
        this.getAllStyles().setBorder(Border.createLineBorder(5,
                ColorUtil.BLACK));
        this.getAllStyles().setBgTransparency(255);
        this.gw = gw;
    }

    @Override
    public void update(Observable observable, Object object) {
        GameCollection garbage = new GameCollection();
        IIterator colIterator = garbage.getIterator();
        while(colIterator.hasNext()) {
            ICollider curObj = (ICollider)colIterator.getNext();
            // get a collidable object
            // check if this object collides with any OTHER object
            IIterator iter2 = garbage.getIterator();
            while (iter2.hasNext()) {
                ICollider otherObj = (ICollider)iter2.getNext();
                // get a collidable object
                // check for collision
                if (otherObj!=curObj) {
                    // make sure it's not the SAME object
                    if (curObj.collidesWith(otherObj)) {
                        curObj.handleCollision(otherObj);
                    }
                }
            }
        }
        this.repaint();
    }

    /*
    public void pointerPressed(int x, int y) {
        int counter = 0;
        if(gw.isPaused())
        {
            collection = gw.getGameCollection();
            IIterator iterator = collection.getIterator();
            x = x - getParent().getAbsoluteX();
            y = y - getParent().getAbsoluteY();
            Point2D pPtrRelPrnt = new Point2D(x, y);
            Point2D pCmpRelPrnt = new Point2D(getX(), getY());
            Object current;
            while (iterator.hasNext()) {
                current = iterator.getNext();
                if (((GameObject) current).contains(pPtrRelPrnt, pCmpRelPrnt)) {
                    ((GameObject) current).setSelected(true);
                    //highlighted += 1; highlight?
                } else {
                    counter += 1;
                }
                this.repaint();
            }

            if(counter == iterator.getSize()) {
                this.deselectAll();
            }

        } else {
            return;
        }
    }


    public void deselectAll() {
        collection = gw.getGameCollection();
        IIterator iterator = collection.getIterator();
        Object current;
        while(iterator.hasNext()) {
            current = iterator.getNext();
            if (((GameObject) current).isSelected() == true) {
                ((GameObject) current).setSelected(false);
            }
            this.repaint();
        }
    }
    */

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        collection = gw.getGameCollection();
        IIterator iterator = collection.getIterator();

        Object current;

        while (iterator.hasNext()) {
            Point2D origin = new Point2D(this.getX(), this.getY());
            current = iterator.getNext();

            if (current instanceof IMoveable) {
                int x = (int) ((GameObject)current).getLocation().getX();
                int y = (int) ((GameObject)current).getLocation().getY();
                int leftWall = this.getX();
                int rightWall = this.getWidth();
                int topWall = this.getY();
                int bottomWall = this.getHeight() - 25;

                if (y <= topWall || y >= bottomWall) {
                    if (y <= 0) {
                        ((GameObject) current).setLocation(new Point2D(x, bottomWall));
                    }
                    if ((y + this.getY()) >= bottomWall) {
                        ((GameObject) current).setLocation(new Point2D(x, 0));
                    }
                }
                if (x <= leftWall || x >= rightWall) {
                    if (x <= 20) {
                        ((GameObject) current).setLocation(new Point2D(rightWall, y));
                    }
                    if (x >= rightWall - 20) {
                        ((GameObject) current).setLocation(new Point2D(0, y));
                    }
                }
            }
            if (current instanceof IDrawable) {
                ((IDrawable)current).draw(g, origin);
            }
        }
    }
}

