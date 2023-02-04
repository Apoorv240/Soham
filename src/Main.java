public class Main {
    public static void main(String[] args) {
        Pose startPos = new Pose(4, -1, 0);
        Pose endPos = new Pose(1, 1, 15);

        PathSegment segLinear = new PathSegment(startPos, endPos, PathType.Linear, InterpolationType.Linear);
        segLinear.generatePath();

        System.out.println(segLinear.calcLocation(0));
        System.out.println(segLinear.calcLocation(0.5));
        System.out.println(segLinear.calcLocation(1));

        GraphVisualizer visualizer = new GraphVisualizer(500, 5);

        visualizer.addPoint(segLinear.calcLocation(0));
        visualizer.addPoint(segLinear.calcLocation(0.5));
        visualizer.addPoint(segLinear.calcLocation(1));

        visualizer.displayGraph();

    }
}