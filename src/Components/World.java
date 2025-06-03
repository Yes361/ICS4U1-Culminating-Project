package Components;

import Core.GameSystem.JGameObject;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class World extends JGameObject  {
    private TileMap tileMap;

    public World(TileMap tileMap) {
        this.tileMap = tileMap;

        tileMap = new TileMap();
    }

    public void update(float delta) {
        computeCollisions(delta);
    }

    private void computeCollisions(float delta) {
        ArrayList<CollisionListener> collidable = (ArrayList<CollisionListener>) getChildren(CollisionListener.class);
        int nCollidables = collidable.size();

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
