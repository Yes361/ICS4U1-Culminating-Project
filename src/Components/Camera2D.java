package Components;

import Core.GameSystem.JGameObject;

import java.awt.*;
import java.awt.geom.Point2D;

public class Camera2D extends JGameObject {
    private Point center = new Point(0, 0);

    public Camera2D() {

    }

    public Camera2D(int x, int y) {
        this(new Point(x, y));
    }

    public Camera2D(Point center) {
        this.center = center;
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        repaint();
    }

    public void setCenter(int x, int y) {
        this.center = new Point(x, y);
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public Point getCenter() {
        return center;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.translate(getCenter().x, getCenter().y);
    }
}
