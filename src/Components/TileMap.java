package Components;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;

public class TileMap {
    private HashMap<String, BufferedImage> tileMap = new HashMap<>();

    public TileMap(HashMap<String, BufferedImage> tileMap) {
        this.tileMap = tileMap;
    }

    public TileMap() {}

    public void addTile(String identifier, Image tile) {
        this.tileMap.put(identifier, (BufferedImage) tile);
    }

    public void addTiles() {

    }

    public void addTilesFromDirectory(String filePath) {
        File directory = new File(filePath);
        FilenameFilter fileNameExtensionFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return true;
            }
        };

        File[] directoryFiles = directory.listFiles(fileNameExtensionFilter);

        assert directoryFiles != null;
        for (File file : directoryFiles) {
            addTile(file.getName(), file.getAbsolutePath());
        }
    }

    public void addTile(String identifier, String filePath) {
        // Toolkit.getDefaultToolkit().getImage(filePath)
        try {
            this.tileMap.put(identifier, ImageIO.read(new File(filePath)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Image getTile(String identifier) {
        return tileMap.get(identifier);
    }

    public BufferedImage getTile(int index) {
        return getTiles()[index];
    }

    public BufferedImage[] getTiles() {
        return tileMap.values().toArray(new BufferedImage[0]);
    }

    public int getTileCount() {
        return tileMap.size();
    }

    public String[] getIdentifiers() {
        return tileMap.keySet().toArray(new String[0]);
    }

    public String getIdentifier(int index) {
        return getIdentifiers()[index];
    }


    public HashMap<String, BufferedImage> getTileMap() {
        return tileMap;
    }
}
