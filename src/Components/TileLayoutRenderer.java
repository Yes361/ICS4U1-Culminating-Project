package Components;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class TileLayoutRenderer extends JComponent {
    public TileMap tileMap = new TileMap();
    private ArrayList<ArrayList<String>> tileLayout = new ArrayList<>();

    public ArrayList<ArrayList<String>> getTileLayout() {
        return tileLayout;
    }

    public void setTileLayout(ArrayList<ArrayList<String>> tileLayout) {
        this.tileLayout = tileLayout;
    }

    public void setTileLayout(String[][] tileLayout) {
        this.tileLayout.clear();
        for (String[] row : tileLayout) {
            this.tileLayout.add(new ArrayList<>());
            for (String col : row) {
                this.tileLayout.getLast().add(col);
            }
        }
    }

    public TileMap getTileMap() {
        return tileMap;
    }

    public void setTileMap(TileMap tileMap) {
        this.tileMap = tileMap;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0;i < tileLayout.size();i++) {
            List<String> tileRow = tileLayout.get(i);
            for (int j = 0;j < tileRow.size();j++) {
                if (tileRow.get(j) == null) {
                    continue;
                }

                int width = 32;
                int height = 32;
                Image img = tileMap.getTile(tileRow.get(j)).getScaledInstance(width, height, Image.SCALE_SMOOTH);
                g.drawImage(img, i * width, j * height, this);
            }
        }
    }
}
