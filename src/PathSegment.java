import java.util.ArrayList;

public class PathSegment {
    Pose startPose;
    Pose endPose;
    InterpolationType headingInterpType;
    PathType pathType;
    Point p1, p2;

    // Linear
    double[] movementVector; // [x, y, heading]
    Curve curve;

    public PathSegment(Pose startPose, Pose endPose, PathType pathType, InterpolationType headingInterpType) {
        this.startPose = startPose;
        this.endPose = endPose;
        this.headingInterpType = headingInterpType;
        this.pathType = pathType;

        movementVector = new double[3];
    }

    public PathSegment(Pose startPose, Pose endPose, PathType pathType, InterpolationType headingInterpType, Point p1, Point p2) {
        this.startPose = startPose;
        this.endPose = endPose;
        this.headingInterpType = headingInterpType;
        this.pathType = pathType;

        movementVector = new double[3];

        this.p1 = p1;
        this.p2 = p2;
    }

    public void generatePath() {
        if (pathType == PathType.Linear) {
            movementVector[0] = endPose.x - startPose.x;
            movementVector[1] = endPose.y - startPose.y;
        } else if (pathType == PathType.Spline) {
            // Kinodynamic Motion Planning for Mobile Robots Using Splines (Lau, Sprunk, Burgard)
            // Cubic spline prototype
            curve = new Curve(startPose, endPose, p1, p2);
        }

        if (headingInterpType == InterpolationType.Linear) {
            movementVector[2] = endPose.headingRadians - startPose.headingRadians;
        }
    }

    public Pose calcLocation(double t) {
        if (this.pathType == PathType.Linear) {
            Point point = new Point(startPose.x + (movementVector[0] * t), startPose.y + (movementVector[1] * t));
            return new Pose(point.x, point.y, startPose.headingRadians + (movementVector[2] * t));
        } else if (this.pathType == PathType.Spline) {
            Point point = curve.calculateCurveLocation(t);
            return new Pose(point.x, point.y, startPose.headingRadians);
        } else {
            // unrecognized path type
            return new Pose(0, 0, 0);
        }
    }
}
