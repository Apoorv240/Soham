package graphics;

import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

public class GraphListener extends MouseInputAdapter
{
    Point location;
    MouseEvent pressed;

    public void mousePressed(MouseEvent me)
    {
        pressed = me;
        System.out.println(pressed.getX() + ", " + pressed.getY());
    }

    public void mouseDragged(MouseEvent me)
    {
        Component point = pressed.getComponent();
        location = point.getLocation(location);
        int x = location.x - pressed.getX() + me.getX();
        int y = location.y - pressed.getY() + me.getY();
        point.setLocation(x, y);
    }
}