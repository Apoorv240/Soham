public class Curve {
    Point startPoint, endPoint;
    public Point p1, p2;

    public Curve(Pose startPose, Pose endPose, Point p1, Point p2) {
        this.startPoint = new Point(startPose.x, startPose.y);
        this.endPoint = new Point(endPose.x, endPose.y);
        this.p1 = p1;
        this.p2 = p2;
    }

    public Curve() {
        this.startPoint = new Point();
        this.endPoint = new Point();
        this.p1 = new Point();
        this.p2 = new Point();
    }

    public Point calculateCurveLocation(double t) {
        // garbage collector WWW
        double x0 = startPoint.x;
        double x1 = p1.x;
        double x2 = p2.x;
        double x3 = endPoint.x;
        double y0 = startPoint.y;
        double y1 = p1.y;
        double y2 = p2.y;
        double y3 = endPoint.y;

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

}
