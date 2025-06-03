package Components;

import Core.GameSystem.AssetManager;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class TileLayoutRenderer extends JComponent implements Serializable {
    private List<TileLayout> tileLayouts = new ArrayList<>();
    private TileMap tileMap = new TileMap();

    public void setTileMap(TileMap tileMap) {
        this.tileMap = tileMap;
    }

    public TileMap getTileMap() {
        return tileMap;
    }

    public void addTileLayout(TileLayout tileLayout) {
        tileLayouts.add(tileLayout);
    }

    public void setTileLayouts(List<TileLayout> tileLayouts) {
        this.tileLayouts = tileLayouts;
    }

    public TileLayout getTileLayout(int index) {
        return tileLayouts.get(index);
    }

    public void removeTileLayout(TileLayout tileLayout) {
        tileLayouts.remove(tileLayout);
    }

    public int getTileLayoutCount() {
        return tileLayouts.size();
    }

    public void saveLayouts(String filePath) {
        saveLayouts(new File((filePath)));
    }

    public void saveLayouts(File file) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
                objectOutputStream.writeObject(tileLayouts);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createLayoutFromFile(String filePath) {
        createLayoutFromFile(new File(filePath));
    }

    public void createLayoutFromFile(File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            try (ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
                setTileLayouts((List<TileLayout>) objectInputStream.readObject());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
                    Image img = tileMap.getTile(tileRow.get(j)).getScaledInstance(width, height, Image.SCALE_SMOOTH);
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
