import javax.sound.sampled.Line;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

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

        public CartesianPanel(int planeBounds) {
            this.planeBounds = planeBounds;
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

            int width = this.getWidth();
            int height = this.getHeight();

            g2.draw(new Line2D.Double(width/spacing, height/2.0, width-(width/spacing), height/2.0));
            g2.draw(new Line2D.Double(width/2.0, height/spacing, width/2.0, height-(height/spacing)));
        }
    }
}
