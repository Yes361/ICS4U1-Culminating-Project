package Components;

import Core.GameSystem.JGameObject;
import Utility.Console;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class RigidCollision2D extends JGameObject implements CollisionListener {
    public RigidCollision2D(int x, int y, int w, int h) {
        setBounds(x, y, w, h);
    }

    @Override
    public void onCollision(CollisionListener other) {
        Rectangle rectA = getBoundRect().getBounds();
        Rectangle rectB = other.getBoundRect().getBounds();

        Rectangle intersection = rectA.intersection(rectB);
        if (intersection.isEmpty()) {
            return;
        }

        if (other instanceof Player gameObject) {
            if (rectA.intersects(rectB)) {

                int newX = 0;
                int newY = 0;

                int buffer = 1;

                if (intersection.width < intersection.height) {
                    // Resolve along X axis
                    if (rectA.x < rectB.x) {
                        newX += intersection.width + buffer;
                    } else {
                        newX -= intersection.width + buffer;
                    }
                } else {
                    // Resolve along Y axis
                    if (rectA.y < rectB.y) {
                        newY += intersection.height + buffer;
                    } else {
                        newY -= intersection.height + buffer;
                    }
                }

                gameObject.movePlayer(newX, newY);
            }
        }
    }

    @Override
    public Rectangle2D getBoundRect() {
        return this.getBounds().getBounds2D();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawRect(getX(), getY(), getWidth(), getHeight());
    }

}
