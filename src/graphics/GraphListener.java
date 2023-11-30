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

        double newX = point.planeBounds * (x + 6 - (point.getOverallWidth() / 2)) / point.planeWidth;
        double newY = point.planeBounds * (y + 6 - (point.getOverallHeight() / 2)) / -point.planeHeight;

        if (point.isPosition) {
            visualizer.movePositionPoint(point.segment,
                    new components.Pose(newX, newY, point.isFirst ? point.segment.getStartPoint().headingRadians : point.segment.getEndPoint().headingRadians),
                    point.isFirst);
        } else {
            visualizer.moveControlPoint(point.segment,
                    new components.Point(newX, newY),
                    point.isFirst ? 1 : 2);
        }
    }

    public void mouseReleased(MouseEvent me) {
        Component pt = pressed.getComponent();
        GraphVisualizer.DraggablePoint point = (GraphVisualizer.DraggablePoint) pt;
        location = point.getLocation(location);
        int x = location.x - pressed.getX() + me.getX();
        int y = location.y - pressed.getY() + me.getY();

        double newX = point.planeBounds * (x + 6 - (point.getOverallWidth() / 2)) / point.planeWidth;
        double newY = point.planeBounds * (y + 6 - (point.getOverallHeight() / 2)) / -point.planeHeight;

        // Print location of release to SYSOUT
        if (point.isPosition) {
            if (point.isFirst)
                System.out.printf("PathSegment(new Pose(%.4f, %.4f, %.2f), new Pose(%.4f, %.4f, %.2f), PathType.Spline, InterpolationType.Linear, new Point(%.4f, %.4f), new Point(%.4f, %.4f))\n",
                        newX, newY,
                        point.segment.getStartPoint().headingRadians,
                        point.segment.getEndPoint().x, point.segment.getEndPoint().y, point.segment.getEndPoint().headingRadians,
                        point.segment.p1.x, point.segment.p1.y,
                        point.segment.p2.x, point.segment.p2.y);
            else
                System.out.printf("PathSegment(new Pose(%.4f, %.4f, %.2f), new Pose(%.4f, %.4f, %.2f), PathType.Spline, InterpolationType.Linear, new Point(%.4f, %.4f), new Point(%.4f, %.4f))\n",
                        point.segment.getStartPoint().x, point.segment.getStartPoint().y, point.segment.getStartPoint().headingRadians,
                        newX, newY,
                        point.segment.getEndPoint().headingRadians,
                        point.segment.p1.x, point.segment.p1.y,
                        point.segment.p2.x, point.segment.p2.y);
        } else {
            if (point.isFirst)
                System.out.printf("PathSegment(new Pose(%.4f, %.4f, %.2f), new Pose(%.4f, %.4f, %.2f), PathType.Spline, InterpolationType.Linear, new Point(%.4f, %.4f), new Point(%.4f, %.4f))\n",
                        point.segment.getStartPoint().x, point.segment.getStartPoint().y, point.segment.getStartPoint().headingRadians,
                        point.segment.getEndPoint().x, point.segment.getEndPoint().y, point.segment.getEndPoint().headingRadians,
                        newX, newY,
                        point.segment.p2.x, point.segment.p2.y);
            else
                System.out.printf("PathSegment(new Pose(%.4f, %.4f, %.2f), new Pose(%.4f, %.4f, %.2f), PathType.Spline, InterpolationType.Linear, new Point(%.4f, %.4f), new Point(%.4f, %.4f))\n",
                        point.segment.getStartPoint().x, point.segment.getStartPoint().y, point.segment.getStartPoint().headingRadians,
                        point.segment.getEndPoint().x, point.segment.getEndPoint().y, point.segment.getEndPoint().headingRadians,
                        point.segment.p1.x, point.segment.p1.y,
                        newX, newY);
        }
    }

}