package Components;

import javax.swing.*;
import java.awt.geom.Point2D;

public class Entity extends JComponent {
    private Point2D position = new Point2D.Float();

    public Point2D getPosition() {
        return position;
    }
}
