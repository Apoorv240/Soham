public class Main {
    public static void main(String[] args) {
        Pose startPos = new Pose(4, -1, 0);
        Pose endPos = new Pose(1, 1, 15);

        PathSegment segLinear = new PathSegment(startPos, endPos, PathType.Linear, InterpolationType.Linear);
        segLinear.generatePath();

        GraphVisualizer visualizer = new GraphVisualizer(500, 5);

        // must add the points in order for the color shading to work correctly
        visualizer.addPoint(segLinear.calcLocation(0));
        visualizer.addPoint(segLinear.calcLocation(0.25));
        visualizer.addPoint(segLinear.calcLocation(0.5));
        visualizer.addPoint(segLinear.calcLocation(0.75));
        visualizer.addPoint(segLinear.calcLocation(1));

        visualizer.displayGraph();

    }
}