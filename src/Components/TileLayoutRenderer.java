package Components;

import Core.GameSystem.AssetManager;
import Core.GameSystem.JGameObject;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class TileLayoutRenderer extends JGameObject implements Serializable {
    private List<TileLayout> tileLayouts = new ArrayList<>();
    private TileMap tileMap = new TileMap();
    private List<TileLayoutSprite> tileLayoutSpriteList = new ArrayList<>();

    public void setTileMap(TileMap tileMap) {
        this.tileMap = tileMap;
    }

    public TileMap getTileMap() {
        return tileMap;
    }

    public void addTileSprite(TileLayoutSprite tileLayoutSprite) {
        tileLayoutSpriteList.add(tileLayoutSprite);
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

//    Render according to z-index
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

        for (TileLayoutSprite tileLayoutSprite : tileLayoutSpriteList) {
            for (int i = 0;i < tileLayoutSprite.getTileLayout().getTileLayout().size();i++) {
                List<String> tileRow = tileLayoutSprite.getTileLayout().getTileLayout().get(i);
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
