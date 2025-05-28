package Components;

import java.awt.*;
import java.awt.geom.Point2D;

public class Camera2D extends Point2D.Float {
    public Camera2D(float x, float y) {
        super(x, y);
    }

    public void render(Graphics graphics) {
        graphics.translate((int) -getX(), (int) -getY());
    }
}
