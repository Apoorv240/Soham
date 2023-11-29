package graphics;

import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

public class GraphListener extends MouseInputAdapter
{
    java.awt.Point location;
    MouseEvent pressed;

    public void mousePressed(MouseEvent me)
    {
        pressed = me;
    }

    public void mouseDragged(MouseEvent me)
    {
        Component pt = pressed.getComponent();
        GraphVisualizer.DraggablePoint point = (GraphVisualizer.DraggablePoint) pt;
        location = point.getLocation(location);
        int x = location.x - pressed.getX() + me.getX();
        int y = location.y - pressed.getY() + me.getY();
        point.setLocation(x, y);
        GraphVisualizer.CartesianPanel panel = (GraphVisualizer.CartesianPanel) pt.getParent();
        GraphVisualizer visualizer = panel.parent.parent;
        // TODO: add heading later
        if (point.isPosition) {
            visualizer.movePositionPoint(point.segment,
                    new components.Pose(point.planeBounds * (x + 6 - (point.getOverallWidth() / 2)) / point.planeWidth, (point.planeBounds * (y + 6 - (point.getOverallHeight() / 2))) / -point.planeHeight, 0),
                    point.isFirst);
        } else {
            visualizer.moveControlPoint(point.segment,
                    new components.Point(point.planeBounds * (x + 6 - (point.getOverallWidth() / 2)) / point.planeWidth, (point.planeBounds * (y + 6 - (point.getOverallHeight() / 2))) / -point.planeHeight),
                    point.isFirst ? 1 : 2);
        }
    }
}