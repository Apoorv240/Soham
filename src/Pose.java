public class Pose {
    public double x;
    public double y;
    public double headingRadians;

    public Pose(double x, double y, double headingRadians) {
        this.x = x;
        this.y = y;
        this.headingRadians = headingRadians;
    }

    @Override
    public String toString() {
        return "Pose{" +
                "x=" + x +
                ", y=" + y +
                ", headingRadians=" + headingRadians +
                '}';
    }
}
