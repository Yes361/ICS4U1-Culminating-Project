package Components;

import Core.GameSystem.JGameObject;

import java.awt.*;

// TODO: do this cuh
public class TileLayoutSprite extends JGameObject {
    private int x = 0;
    private int y = 0;
    private TileLayout tileLayout;

    public TileLayoutSprite(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public TileLayoutSprite(int x, int y, TileLayout tileLayout) {
        this.x = x;
        this.y = y;
        this.tileLayout = tileLayout;
    }

    public int getTileX() {
        return x;
    }

    public int getTileY() {
        return y;
    }

    public void render(Graphics graphics) {

    }

    public void setTileLayout(TileLayout tileLayout) {
        this.tileLayout = tileLayout;
    }

    public TileLayout getTileLayout() {
        return tileLayout;
    }
}
