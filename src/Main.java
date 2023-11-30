import components.InterpolationType;
import components.Point;
import components.Pose;
import graphics.GraphVisualizer;
import paths.PathSegment;
import paths.PathType;

public class Main {
    public static void main(String[] args) {
        GraphVisualizer visualizer = new GraphVisualizer(500, 3);
        visualizer.setNumPoses(20);

        // Start and end position
        Pose startPos = new Pose(0, 0, 0);
        Pose endPos = new Pose(3, 2.5, 15);
        // Control points
        Point p1 = new Point(2, -2);
        Point p2 = new Point(1.2, 2);

        // Default spline
        PathSegment seg = new PathSegment(startPos, endPos, PathType.Spline, InterpolationType.Linear, p1, p2);
        // Generated by GUI
        PathSegment segCustom = new PathSegment(new Pose(-2.0027, 0.0072, 0.00), new Pose(-0.0137, 2.0230, 15.00), PathType.Spline, InterpolationType.Linear, new Point(-1.1660, -0.3528), new Point(0.2195, 1.2887));

        visualizer.addPath(seg);
        visualizer.addPath(segCustom);

        visualizer.displayGraph();

        visualizer.animatePath(seg, 2000);

    }
}