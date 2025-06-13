package Components;

import java.awt.geom.Rectangle2D;

public interface CollisionListener {
    public void onCollision(CollisionListener other);
    public Rectangle2D getBoundRect();
}
