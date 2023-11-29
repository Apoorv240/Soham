package components;

public class Pose {
    public double x;
    public double y;
    public double headingRadians;

    public Pose(double x, double y, double headingRadians) {
        this.x = x;
        this.y = y;
        this.headingRadians = headingRadians;
    }

    public Pose() {}

    @Override
    public String toString() {
        return "res.Pose{" +
                "x=" + x +
                ", y=" + y +
                ", headingRadians=" + headingRadians +
                '}';
    }
}
