import java.text.DecimalFormat;

public class Curve {
    Point startPoint, endPoint;
    public Point p1, p2;

    public Curve(Pose startPose, Pose endPose, Point p1, Point p2) {
        this.startPoint = new Point(startPose.x, startPose.y);
        this.endPoint = new Point(endPose.x, endPose.y);
        this.p1 = p1;
        this.p2 = p2;
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

        double tX0 = x0 + t * (x1 - x0);
        double tX1 = x1 + t * (x2 - x1);
        double tX3 = x2 + t * (x3 - x2);

        double tX2 = tX0 + t * (tX1 - tX0);
        double tX4 = tX1 + t * (tX3 - tX1);

        double tY0 = y0 + t * (y1 - y0);
        double tY1 = y1 + t * (y2 - y1);
        double tY3 = y2 + t * (y3 - y2);

        double tY2 = tY0 + t * (tY1 - tY0);
        double tY4 = tY1 + t * (tY3 - tY1);

        double x = tX2 + t * (tX4 - tX2);
        double y = tY2 + t * (tY4 - tY2);

        return new Point(x, y);
    }

}
