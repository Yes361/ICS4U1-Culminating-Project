package Components;

import java.awt.geom.Rectangle2D;

public interface CollisionListener {
    public void onCollision(Object other);
    public Rectangle2D getBoundRect();
}
