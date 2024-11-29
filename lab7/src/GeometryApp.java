import javax.swing.*;
import java.awt.*;

public class GeometryApp extends JFrame {

    private final int[] triangleX = {200, 300, 200};
    private final int[] triangleY = {300, 300, 100};

    private final Point p1 = new Point(300, 350);
    private final Point p2 = new Point(250, 50);

    public GeometryApp() {
        setTitle("Геометрична програма");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        drawAxes(g2d);

        g2d.setColor(Color.BLUE);
        g2d.drawPolygon(triangleX, triangleY, 3);

        g2d.setColor(Color.RED);
        g2d.drawLine(p1.x, p1.y, p2.x, p2.y);

        g2d.setColor(Color.GREEN);
        for (int i = 0; i < 3; i++) {
            int next = (i + 1) % 3;
            Point intersection = getIntersection(
                    p1, p2,
                    new Point(triangleX[i], triangleY[i]),
                    new Point(triangleX[next], triangleY[next])
            );

            if (intersection != null) {
                g2d.fillOval(intersection.x - 5, intersection.y - 5, 10, 10);
                g2d.drawString("Перетин: (" + intersection.x + ", " + intersection.y + ")", intersection.x + 10, intersection.y - 10);
            }
        }

        g2d.setColor(Color.BLACK);
        g2d.drawString("Знайти точки перетину відрізка зі сторонами трикутника.", 20, 450);
    }

    private void drawAxes(Graphics2D g2d) {
        g2d.setColor(Color.GRAY);
        g2d.drawLine(50, 250, 450, 250); // Вісь X
        g2d.drawLine(250, 50, 250, 450); // Вісь Y
    }

    private Point getIntersection(Point p1, Point p2, Point p3, Point p4) {
        double a1 = p2.y - p1.y;
        double b1 = p1.x - p2.x;
        double c1 = a1 * p1.x + b1 * p1.y;

        double a2 = p4.y - p3.y;
        double b2 = p3.x - p4.x;
        double c2 = a2 * p3.x + b2 * p3.y;

        double determinant = a1 * b2 - a2 * b1;

        if (determinant == 0) {
            return null;
        }

        int x = (int) ((b2 * c1 - b1 * c2) / determinant);
        int y = (int) ((a1 * c2 - a2 * c1) / determinant);

        if (isBetween(p1, p2, new Point(x, y)) && isBetween(p3, p4, new Point(x, y))) {
            return new Point(x, y);
        }

        return null;
    }

    private boolean isBetween(Point a, Point b, Point c) {
        return c.x >= Math.min(a.x, b.x) && c.x <= Math.max(a.x, b.x)
                && c.y >= Math.min(a.y, b.y) && c.y <= Math.max(a.y, b.y);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GeometryApp app = new GeometryApp();
            app.setVisible(true);
        });
    }
}
