package Components;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class World extends JComponent  {
    private TileMap tileMap;
    private List<Entity> entityList = new ArrayList<>();

    public World(TileMap tileMap) {
        this.tileMap = tileMap;

        tileMap = new TileMap();
    }

    public void update(float delta) {

    }

    private void computeCollisions(float delta) {
        Graphics2D entityGraphics2D = (Graphics2D) entityList.get(0).getGraphics();
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);


    }
}
