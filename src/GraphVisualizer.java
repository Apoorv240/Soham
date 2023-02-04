import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class GraphVisualizer {
    int frameSize;
    int planeBounds;
    CartesianFrame frame;

    public GraphVisualizer(int frameSize, int planeBounds) {
        frame = new CartesianFrame(frameSize, planeBounds);
    }

    public void displayGraph() {
        frame.showUI();
    }

    public void addPoint(Pose point) {
        frame.panel.points.add(point);
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
        int planeBounds;
        final double spacing = 20.0;
        ArrayList<Pose> points = new ArrayList<>();

        public CartesianPanel(int planeBounds) {
            this.planeBounds = planeBounds;
        }

        public CartesianPanel(int planeBounds, ArrayList<Pose> points) {
            this.planeBounds = planeBounds;
            this.points = points;
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

            int width = this.getWidth();
            int height = this.getHeight();

            // only the width and height of one side
            double planeWidth = (width - ((2 * width) / spacing)) / 2;
            double planeHeight = (height - ((2 * height) / spacing)) / 2;

            drawPlane(g2, width, height, planeWidth, planeHeight);
            drawDynamic(g2, width, height, planeWidth, planeHeight);
        }

        void drawDynamic(Graphics2D g2, int width, int height, double planeWidth, double planeHeight) {
            g2.setStroke(new BasicStroke(3));
            for (int i = 0; i < points.size(); i++) {
                Pose point = points.get(i);
                g2.setColor(Color.getHSBColor((float) (0.5 + (i * (0.3 / points.size()))), 1f, 0.7f));

                g2.drawOval((int) ((width / 2.0) + (point.x * (planeWidth / planeBounds))), (int) ((height / 2.0) + (point.y * (planeHeight / planeBounds))), 5, 5);
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
}
