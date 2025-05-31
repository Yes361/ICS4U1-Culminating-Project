package Components;

import java.util.ArrayList;

public class TileLayout {
    private int zIndex = -1;
    public TileMap tileMap = new TileMap();
    private ArrayList<ArrayList<String>> tileLayout = new ArrayList<>();

    public TileLayout() {}

    public TileLayout(TileMap tileMap) {
        this(tileMap, 0);
    }

    public TileLayout(TileMap tileMap, int zIndex) {
        this.tileMap = tileMap;
        this.zIndex = zIndex;
    }

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

    public int getzIndex() {
        return zIndex;
    }
}
