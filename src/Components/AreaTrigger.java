/*
* UNUSED
*  */

package Components;

import Core.GameSystem.JGameObject;
import Utility.EventEmitter;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class AreaTrigger extends JGameObject implements CollisionListener {
    private EventEmitter eventEmitter = new EventEmitter();
    private ArrayList<Object> CollidedObjects = new ArrayList<>();

    public AreaTrigger() {}

    public AreaTrigger(int x, int y, int w, int h) {
        setBounds(x, y, w, h);
    }

    public void update(float delta) {
        repaint();
    }

    @Override
    public void onCollision(CollisionListener other) {

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
