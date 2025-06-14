/*
* CollisionEngine handles the collision between objects registered to it
*
*  */

package Components;

import Core.GameSystem.JGameObject;

import java.awt.*;
import java.util.ArrayList;

public class CollisionEngine extends JGameObject  {
    public void update(float delta) {
        computeCollisions(delta);
    }

    private void computeCollisions(float delta) {
        // Get the children that are implementations of CollisionListeners,
        // indicating they will respond to collision events in some capacity
        ArrayList<CollisionListener> collidable = (ArrayList<CollisionListener>) getChildren(CollisionListener.class);
        int nCollidables = collidable.size();

        // For each pair of entities that have collidied,
        // if they intersect, call their onCollision method,
        // passing in the entity that collided with them
        for (int i = 0;i < nCollidables;i++) {
            CollisionListener a = collidable.get(i);
            for (int j = i + 1;j < nCollidables;j++) {
                CollisionListener b = collidable.get(j);

                if (a.getBoundRect().intersects(b.getBoundRect())) {
                    a.onCollision(b);
                    b.onCollision(a);
                }

            }
        }
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
    }
}
