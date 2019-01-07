package com.mycompany.a3;

import com.codename1.location.Location;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;

public interface ISelectable {

    public void setSelected(boolean det);

    public boolean isSelected();

    public boolean contains(Point2D ptr, Point2D component);

    public void draw(Graphics g, Point2D component);

}
