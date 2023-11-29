package graphics;

import components.Point;
import components.Pose;
import paths.PathSegment;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphVisualizer {
    CartesianFrame frame;
    // Default number of poses generated
    int numPoses = 10;

    public GraphVisualizer(int frameSize, int planeBounds) {
        frame = new CartesianFrame(frameSize, planeBounds);
    }

    public void displayGraph() {
        frame.showUI();
    }

    public void addPath(PathSegment segment) {
        if (!frame.panel.points.containsKey(segment)) {
            frame.panel.positionPoints.put(segment, new ArrayList<>());
            frame.panel.points.put(segment, new ArrayList<>());
            frame.panel.outsidePoints.put(segment, new ArrayList<>());
        }
        this.addPositionPoint(segment, segment.getStartPoint());
        this.addPositionPoint(segment, segment.getEndPoint());
        for(int i = 1; i < numPoses; i += 1) {
            this.addPoint(segment, segment.calcLocation(i / (double) numPoses));
            // TODO: Print this somewhere on the GUI
            System.out.println(segment.calcLocation(i / (double) numPoses) + " " + i / (double) numPoses);
        }
        this.addPoint(segment, segment.p1);
        this.addPoint(segment, segment.p2);
    }

    public void addPositionPoint(PathSegment segment, Pose point) {
        // use solid black color to paint bezier end points
        frame.panel.positionPoints.get(segment).add(point);
        frame.repaint();
    }

    public void addPoint(PathSegment segment, Pose point) {
        // paint with normal gradient
        frame.panel.points.get(segment).add(point);
        frame.repaint();
    }

    public void addPoint(PathSegment segment, Point point) {
        // use solid red color to paint bezier control points
        frame.panel.outsidePoints.get(segment).add(point);
        frame.repaint();
    }

    public void movePositionPoint(PathSegment segment, Pose point, boolean start) {
        if (start) {
            // Start point
            segment.setStartPoint(point);
            segment.generatePath();
        } else {
            // End point
            segment.setEndPoint(point);
            segment.generatePath();
        }

        frame.panel.positionPoints.get(segment).clear();
        frame.panel.outsidePoints.get(segment).clear();
        frame.panel.points.get(segment).clear();
        this.addPath(segment);
        frame.repaint();
    }

    public void moveControlPoint(PathSegment segment, Point point, int id) {
        if (id == 1){
            // First control point
            segment.p1 = point;
            segment.generatePath();
        } else {
            // Second control point (might add more in the future)
            segment.p2 = point;
            segment.generatePath();
        }

        frame.panel.positionPoints.get(segment).clear();
        frame.panel.outsidePoints.get(segment).clear();
        frame.panel.points.get(segment).clear();
        this.addPath(segment);
        frame.repaint();
    }

    static class CartesianFrame extends JFrame {
        CartesianPanel panel;

        public CartesianFrame(int frameSize, int planeBounds) {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setTitle("Graph Visualizer");
            setSize(frameSize, frameSize);

            panel = new CartesianPanel(planeBounds);
            add(panel);
        }

        public void showUI() {
            setVisible(true);
        }
    }

    static class CartesianPanel extends JPanel {
        int width;
        int height;
        double planeWidth;
        double planeHeight;
        int planeBounds;
        final double spacing = 20.0;
        // End points
        Map<PathSegment, List<Pose>> positionPoints = new HashMap<>();
        // Points within the spline
        Map<PathSegment, List<Pose>> points = new HashMap<>();
        // Control points outside the spline
        Map<PathSegment, List<Point>> outsidePoints = new HashMap<>();

        public CartesianPanel(int planeBounds) {
            this.planeBounds = planeBounds;
            setOpaque(false);
        }

        public CartesianPanel(int planeBounds, Map<PathSegment, List<Pose>> points) {
            this.planeBounds = planeBounds;
            this.points = points;
            setOpaque(false);
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            width = this.getWidth();
            height = this.getHeight();

            // only the width and height of one side
            planeWidth = (width - ((2 * width) / spacing)) / 2;
            planeHeight = (height - ((2 * height) / spacing)) / 2;

            drawPlane(g2, width, height, planeWidth, planeHeight);
            drawDynamic(g2, width, height, planeWidth, planeHeight);
            drawUI(g2, width, height, planeWidth, planeHeight);

            for (List<Pose> list:
                    positionPoints.values()) {
                for (Pose point : list) {
                    GraphListener listener = new GraphListener();
                    DraggablePoint p = new DraggablePoint(point.x, point.y, width, height, planeWidth, planeHeight, planeBounds);
                    p.setPreferredSize(new Dimension(6, 6));
                    p.setSize(p.getPreferredSize());
                    p.addMouseListener(listener);
                    p.addMouseMotionListener(listener);
                    add(p);
                    revalidate();
                    repaint();
                }
            }
        }

        void drawUI(Graphics2D g2, int width, int height, double planeWidth, double planeHeight) {
            // Draw statistic in corner
            g2.setColor(Color.BLACK);
            g2.drawString("Width: " + width, (float) (width - 100), (float) (height - 50));
            g2.drawString("Height: " + height, (float) (width - 100), (float) (height - 40));
        }

        void drawDynamic(Graphics2D g2, int width, int height, double planeWidth, double planeHeight) {
            g2.setStroke(new BasicStroke(3));

            for (List<Pose> list :
                    positionPoints.values()) {
                for (Pose point : list) {
                    GraphListener listener = new GraphListener();
                    DraggablePoint p = new DraggablePoint(point.x, point.y, width, height, planeWidth, planeHeight, planeBounds);
                    p.setSize(p.getPreferredSize());
                    p.addMouseListener(listener);
                    p.addMouseMotionListener(listener);
                    this.add(p);
                }
            }

            for (List<Pose> list :
                    points.values()) {
                for (int i = 0; i < list.size(); i++) {
                    Pose point = list.get(i);
                    g2.setColor(Color.getHSBColor((float) (0.8 - (i * (0.3 / list.size()))), 1f, 0.7f));

                    g2.drawOval((int) ((width / 2.0) + (point.x * (planeWidth / planeBounds))) - 3, (int) ((height / 2.0) - (point.y * (planeHeight / planeBounds))) - 3, 6, 6);
                }
            }

            g2.setColor(Color.RED);
            for (List<Point> list :
                    outsidePoints.values()) {
                for (components.Point point : list) {
                    g2.drawOval((int) ((width / 2.0) + (point.x * (planeWidth / planeBounds))) - 3, (int) ((height / 2.0) - (point.y * (planeHeight / planeBounds))) - 3, 6, 6);
                }
            }
        }

        void drawPlane(Graphics2D g2, int width, int height, double planeWidth, double planeHeight) {
            // Draw the base plane
            g2.draw(new Line2D.Double(width / spacing, height / 2.0, width - (width / spacing), height / 2.0));
            g2.draw(new Line2D.Double(width / 2.0, height / spacing, width / 2.0, height - (height / spacing)));

            // Draw the plane ticks
            for(int i = 1; i <= planeBounds; i++) {
                // x-axis
                g2.draw(new Line2D.Double((width / 2.0) - (i * (planeWidth / planeBounds)), (height / 2.0) - 5, (width / 2.0) - (i * (planeWidth / planeBounds)), (height / 2.0) + 5));
                g2.draw(new Line2D.Double((width / 2.0) + (i * (planeWidth / planeBounds)), (height / 2.0) - 5, (width / 2.0) + (i * (planeWidth / planeBounds)), (height / 2.0) + 5));
                // y-axis
                g2.draw(new Line2D.Double((width / 2.0) - 5, (height / 2.0) - (i * (planeHeight / planeBounds)), (width / 2.0) + 5, (height / 2.0) - (i * (planeHeight / planeBounds))));
                g2.draw(new Line2D.Double((width / 2.0) - 5, (height / 2.0) + (i * (planeHeight / planeBounds)), (width / 2.0) + 5, (height / 2.0) + (i * (planeHeight / planeBounds))));
            }
        }
    }

    static class DraggablePoint extends JComponent {
        double x;
        double y;
        double width;
        double height;
        double planeWidth;
        double planeHeight;
        double planeBounds;

        public DraggablePoint(double x, double y, double width, double height, double planeWidth, double planeHeight, double planeBounds) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.planeWidth = planeWidth;
            this.planeHeight = planeHeight;
            this.planeBounds = planeBounds;
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            g2.setStroke(new BasicStroke(3));
            g2.setColor(Color.BLACK);

            g2.drawOval((int) ((width / 2.0) + (x * (planeWidth / planeBounds))) - 3, (int) ((height / 2.0) - (y * (planeHeight / planeBounds))) - 3, 6, 6);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(6, 6);
        }
    }

    public void setNumPoses(int numPoses) {
        this.numPoses = numPoses;
    }
}
