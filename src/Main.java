public class Main {
    public static void main(String[] args) {
        Pose startPos = new Pose(0, 0, 0);
        Pose endPos = new Pose(4, 4, 15);

        PathSegment segLinear = new PathSegment(startPos, endPos, PathType.Linear, InterpolationType.Linear);
        segLinear.generatePath();

        GraphVisualizer visualizer = new GraphVisualizer(500, 5);

        // must add the points in order for the color shading to work correctly
        for(int i = 0; i <= 10; i += 1) {
            visualizer.addPoint(segLinear.calcLocation(i / 10.0));
            System.out.println(segLinear.calcLocation(i / 10.0) + " " + i / 10.0);
        }

        visualizer.displayGraph();

    }
}