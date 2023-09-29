package components;

public class Point {
    public double x;
    public double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(Pose pose) {
        this.x = pose.x;
        this.y = pose.y;
    }

    public Point() {}
}
