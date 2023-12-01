package components;

public class Curve {
    Pose startPose, endPose;
    public Point p1, p2;

    private int N = 5; // 5th degree spline (quintic)

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
        double x0 = startPose.x;
        double x1 = p1.x;
        double x2 = p2.x;
        double x3 = endPose.x;
        double y0 = startPose.y;
        double y1 = p1.y;
        double y2 = p2.y;
        double y3 = endPose.y;

        double x = curveComponent(t, 0, x0) +
                curveComponent(t, 1, x0) +
                curveComponent(t, 2, x1) +
                curveComponent(t, 3, x2) +
                curveComponent(t, 4, x3) +
                curveComponent(t, 5, x3);

        double y = curveComponent(t, 0, y0) +
                curveComponent(t, 1, y0) +
                curveComponent(t, 2, y1) +
                curveComponent(t, 3, y2) +
                curveComponent(t, 4, y3) +
                curveComponent(t, 5, y3);

        return new Point(x, y);
    }

    public int binom(int k) {
        int factor = 1;

        for (int i = N; i > k; i--) {
            factor *= i;
        }

        for (int i = 1; i <= (N-k); i++) {
            factor /= i;
        }

        return factor;
    }

    public double curveComponent(double t, int K, double v) {
        return binom(K) * Math.pow(1-t, N-K) * Math.pow(t, K) * v;
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
