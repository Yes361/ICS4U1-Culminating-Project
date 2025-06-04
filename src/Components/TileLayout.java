package Components;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class TileLayout implements Serializable {
    private int zIndex = -1;
    private ArrayList<ArrayList<String>> tileLayout = new ArrayList<>();

    public TileLayout() {
        this(0, new ArrayList<>());
    }

    public TileLayout(String[][] tileLayout) {
        zIndex = 0;
        setTileLayout(tileLayout);
    }

    public TileLayout(ArrayList<ArrayList<String>> tileLayout) {
        this(0, tileLayout);
    }

    public TileLayout(int zIndex) {
        this(zIndex, new ArrayList<>());
    }

    public TileLayout(int zIndex, ArrayList<ArrayList<String>> tileLayout) {
        this.zIndex = zIndex;
        this.tileLayout = tileLayout;
    }

    public TileLayout clone() {
        return new TileLayout(zIndex, (ArrayList<ArrayList<String>>) tileLayout.clone());
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

    public int getzIndex() {
        return zIndex;
    }
}
