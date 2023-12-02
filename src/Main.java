import components.InterpolationType;
import components.Point;
import components.Pose;
import graphics.GraphVisualizer;
import paths.PathSegment;
import paths.PathType;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        GraphVisualizer visualizer = new GraphVisualizer(500, 3);
        visualizer.setNumPoses(15);

        // Default spline
        PathSegment seg = new PathSegment(new Pose(2.5333, 0.5200, 0.00), new Pose(1.4800, 2.2533, 15.00), PathType.Spline, InterpolationType.Linear, new Point(2.0667, 0.5333), new Point(1.6267, 1.5067));

        visualizer.addPath(seg);

        visualizer.displayGraph();

        visualizer.animatePath(seg, 2000);

    }
}