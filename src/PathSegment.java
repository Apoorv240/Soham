public class PathSegment {
    Pose startPose;
    Pose endPose;
    InterpolationType headingInterpType;
    PathType pathType;

    // Linear
    double[] movementVector; // [x, y, heading]

    public PathSegment(Pose startPose, Pose endPose, PathType pathType, InterpolationType headingInterpType) {
        this.startPose = startPose;
        this.endPose = endPose;
        this.headingInterpType = headingInterpType;
        this.pathType = pathType;

        movementVector = new double[3];
    }

    public void generatePath() {
        if (pathType == PathType.Linear) {
            movementVector[0] = endPose.x - startPose.x;
            movementVector[1] = endPose.y - startPose.y;
        } else if (pathType == PathType.Spline) {
            // Kinodynamic Motion Planning for Mobile Robots Using Splines (Lau, Sprunk, Burgard)

        }

        if (headingInterpType == InterpolationType.Linear) {
            movementVector[2] = endPose.headingRadians - startPose.headingRadians;
        }
    }

    public Pose calcLocation(double t) {
        return new Pose(startPose.x + movementVector[0]*t, startPose.y + movementVector[1]*t, startPose.headingRadians + movementVector[2]*t);
    }
}
