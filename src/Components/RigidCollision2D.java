package Components;

import Core.GameSystem.JGameObject;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class RigidCollision2D extends JGameObject implements CollisionListener {

    @Override
    public void onCollision(CollisionListener other) {
        if (other instanceof JGameObject) {
            JGameObject gameObject = (JGameObject) other;

            Rectangle rectA = getBoundRect().getBounds();
            Rectangle rectB = other.getBoundRect().getBounds();

            Rectangle intersection = rectA.intersection(rectB);

            if (intersection.width < intersection.height) {
                if (rectA.x < rectB.x) {
                    rectA.x -= intersection.width;
                } else {
                    rectA.x += intersection.width;
                }
            } else {
                if (rectA.y < rectB.y) {
                    rectA.y -= intersection.height;
                } else {
                    rectA.y += intersection.height;
                }
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
