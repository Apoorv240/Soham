package components;

public class Curve {
    Pose startPose, endPose;
    public Point p1, p2;

    public Curve(Pose startPose, Pose endPose, Point p1, Point p2) {
        this.startPose = startPose;
        this.endPose = endPose;
        this.p1 = p1;
        this.p2 = p2;
    }

    public Curve() {
        this.startPose = new Pose();
        this.endPose = new Pose();
        this.p1 = new Point();
        this.p2 = new Point();
    }

    public Point calculateCurveLocation(double t) {
        // garbage collector WWW
        // most of this will probably be optimized away by the compiler anyways lol
        double x0 = startPose.x;
        double x1 = p1.x;
        double x2 = p2.x;
        double x3 = endPose.x;
        double y0 = startPose.y;
        double y1 = p1.y;
        double y2 = p2.y;
        double y3 = endPose.y;

        // Note that equations are calculated on time and not position intervals
        double x = x0 * (1 - 3*t + 3*t*t - t*t*t) +
                x1 * (3*t - 6*t*t + 3*t*t*t) +
                x2 * (3*t*t - 3*t*t*t) +
                x3 * (t*t*t);
        double y = y0 * (1 - 3*t + 3*t*t - t*t*t) +
                y1 * (3*t - 6*t*t + 3*t*t*t) +
                y2 * (3*t*t - 3*t*t*t) +
                y3 * (t*t*t);

        return new Point(x, y);

    }

    @Override
    public String toString() {
        return "Curve{" +
                "startPoint=" + startPose +
                ", endPoint=" + endPose +
                ", p1=" + p1 +
                ", p2=" + p2 +
                '}';
    }
}
