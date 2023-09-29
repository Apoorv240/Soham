import graphics.GraphVisualizer;
import components.InterpolationType;
import components.Point;
import components.Pose;

public class Main {
    public static void main(String[] args) {
        Pose startPos = new Pose(0, 0, 0);
        Pose endPos = new Pose(5, -0.5, 15);
        // Control points
        Point p1 = new Point(2, -2);
        Point p2 = new Point(4, 2);

        PathSegment segLinear = new PathSegment(startPos, endPos, PathType.Spline, InterpolationType.Linear, p1, p2);
        segLinear.generatePath();

        GraphVisualizer visualizer = new GraphVisualizer(500, 5);

        // Must add the points in order for the color shading to work correctly
        for(int i = 0; i <= 10; i += 1) {
            visualizer.addPoint(segLinear.calcLocation(i / 10.0));
            System.out.println(segLinear.calcLocation(i / 10.0) + " " + i / 10.0);
        }

        visualizer.addPoint(p1);
        visualizer.addPoint(p2);

        visualizer.displayGraph();

    }
}