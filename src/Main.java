public class Main {
    public static void main(String[] args) {
        Pose startPos = new Pose(4, -1, 0);
        Pose endPos = new Pose(1, 1, 15);

        PathSegment seg = new PathSegment(startPos, endPos, PathType.Linear, InterpolationType.Linear);
        seg.generatePath();

        System.out.println(seg.calcLocation(0));
        System.out.println(seg.calcLocation(0.5));
        System.out.println(seg.calcLocation(1));
    }
}