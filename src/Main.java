import components.Curve;
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
        Pose startPos = new Pose(-2, 2, 0);
        Pose endPos = new Pose(3, 2.5, 15);
        // Control points
        Point p1 = new Point(2, -2);
        Point p2 = new Point(1.2, 2);

        // Default spline
        PathSegment seg = new PathSegment(startPos, endPos, PathType.Spline, InterpolationType.Linear, p1, p2);

        visualizer.addPath(seg);

        visualizer.displayGraph();

        visualizer.animatePath(seg, 2000);

    }
}