import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Pose startPos = new Pose(0, 0, 0);
        Pose endPos = new Pose(4, 4, 15);

        Pose p1 = new Pose(0, 2, 14);
        Pose p2 = new Pose(4, 2, 13);
        List<Pose> poses = new ArrayList<>();
        poses.add(p1);
        poses.add(p2);

        PathSegment segLinear = new PathSegment(startPos, endPos, PathType.Spline, InterpolationType.Linear, poses);
        segLinear.generatePath();

        GraphVisualizer visualizer = new GraphVisualizer(500, 5);
/*
        // must add the points in order for the color shading to work correctly
        for(int i = 0; i <= 10; i += 1) {
            visualizer.addPoint(segLinear.calcLocation(i / 10.0));
            System.out.println(segLinear.calcLocation(i / 10.0) + " " + i / 10.0);
        }

        visualizer.addPoint(p1);
        visualizer.addPoint(p2);

        visualizer.displayGraph();*/

    }
}