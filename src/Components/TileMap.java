package Components;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TileMap {
    private HashMap<String, Image> tileMap = new HashMap<>();

    public TileMap(HashMap<String, Image> tileMap) {
        this.tileMap = tileMap;
    }

    public TileMap() {}

    public void addTile(String identifier, Image tile) {
        this.tileMap.put(identifier, tile);
    }

    public Image getTile(String identifier) {
        return tileMap.get(identifier);
    }

    public Image getTile(int index) {
        return getTiles()[index];
    }

    public Image[] getTiles() {
        return tileMap.values().toArray(new Image[0]);
    }


    public HashMap<String, Image> getTileMap() {
        return tileMap;
    }
}
