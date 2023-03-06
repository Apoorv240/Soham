import java.util.ArrayList;
import java.util.List;

public class PathSegment {
    Pose startPose;
    Pose endPose;
    InterpolationType headingInterpType;
    PathType pathType;

    // Linear
    double[] movementVector; // [x, y, heading]

    // Spline
    List<Pose> waypoints;
    List<Curve> curves;

    public PathSegment(Pose startPose, Pose endPose, PathType pathType, InterpolationType headingInterpType) {
        this.startPose = startPose;
        this.endPose = endPose;
        this.headingInterpType = headingInterpType;
        this.pathType = pathType;

        movementVector = new double[3];
    }

    public PathSegment(Pose startPose, Pose endPose, PathType pathType, InterpolationType headingInterpType, List<Pose> waypoints) {
        this.startPose = startPose;
        this.endPose = endPose;
        this.headingInterpType = headingInterpType;
        this.pathType = pathType;
        this.waypoints = waypoints;

        movementVector = new double[3];
        curves = new ArrayList<Curve>();
    }

    void createCurves() {
        curves.add(new Curve());
        curves.get(0).startPose = startPose;

        for (int i = 0; i < waypoints.size(); i++) {
            curves.get(i).endPose = waypoints.get(i);

            curves.add(new Curve());
            curves.get(i+1).startPose = waypoints.get(i);
        }

        curves.get(curves.size() - 1).endPose = endPose;
        System.out.println(curves);
    }

    void smoothCurves() {
        for (int n = 0; n < curves.size(); n++) {

        }
    }

    public void generatePath() {
        if (pathType == PathType.Linear) {
            movementVector[0] = endPose.x - startPose.x;
            movementVector[1] = endPose.y - startPose.y;
        } else if (pathType == PathType.Spline) {
            // Kinodynamic Motion Planning for Mobile Robots Using Splines (Lau, Sprunk, Burgard)
            createCurves();
            smoothCurves();
        }

        if (headingInterpType == InterpolationType.Linear) {
            movementVector[2] = endPose.headingRadians - startPose.headingRadians;
        }
    }

    public Pose calcLocation(double t) {
        return new Pose(startPose.x + (movementVector[0] * t), startPose.y + (movementVector[1] * t), startPose.headingRadians + (movementVector[2] * t));
    }
}
