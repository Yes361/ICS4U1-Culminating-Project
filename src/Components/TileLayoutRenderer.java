package Components;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class TileLayoutRenderer extends JComponent {
    private List<TileLayout> tileLayouts = new ArrayList<>();

    public void addTileLayout(TileLayout tileLayout) {
        tileLayouts.add(tileLayout);
    }

    public TileLayout getTileLayout(int index) {
        return tileLayouts.get(index);
    }

    public void removeTileLayout(TileLayout tileLayout) {
        tileLayouts.remove(tileLayout);
    }

    public void render(Graphics graphics) {
        for (TileLayout tileLayout : tileLayouts) {
            for (int i = 0;i < tileLayout.getTileLayout().size();i++) {
                List<String> tileRow = tileLayout.getTileLayout().get(i);
                for (int j = 0;j < tileRow.size();j++) {
                    if (tileRow.get(j) == null) {
                        continue;
                    }

                    int width = 32;
                    int height = 32;
                    Image img = tileLayout.getTileMap().getTile(tileRow.get(j)).getScaledInstance(width, height, Image.SCALE_SMOOTH);
                    graphics.drawImage(img, i * width, j * height, this);
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        render(g);
    }
}
