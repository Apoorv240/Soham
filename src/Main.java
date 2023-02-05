public class Main {
    public static void main(String[] args) {
        Pose startPos = new Pose(0, 0, 0);
        Pose endPos = new Pose(4, 4, 15);

        PathSegment segLinear = new PathSegment(startPos, endPos, PathType.Linear, InterpolationType.Linear);
        segLinear.generatePath();

        GraphVisualizer visualizer = new GraphVisualizer(500, 5);

        // must add the points in order for the color shading to work correctly
        for(float i = 0f; i <= 1f; i += 0.1f) {
            visualizer.addPoint(segLinear.calcLocation(i));
        }

        visualizer.displayGraph();

    }
}