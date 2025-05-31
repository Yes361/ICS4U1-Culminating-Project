import Components.*;
import Core.GameSystem.AssetManager;
import Core.GameSystem.JGameObject;

import javax.swing.*;

public class Game extends JFrame {
    JGameObject root;

    public Game() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);

        root = new JGameObject();
        root.setBounds(0, 0, getWidth(), getHeight());

        TileMap tileMap = new TileMap();
        tileMap.addTile("Pot-23", AssetManager.getBufferedSprite("TileMap\\Pot-23.png.png"));
        tileMap.addTile("Wall-3", AssetManager.getBufferedSprite("TileMap\\Wall-3.png.png"));
        tileMap.addTile("Wall-4", AssetManager.getBufferedSprite("TileMap\\Wall-4.png.png"));
        tileMap.addTile("Wall-5", AssetManager.getBufferedSprite("TileMap\\Wall-5.png.png"));
        tileMap.addTile("Wall-7", AssetManager.getBufferedSprite("TileMap\\Wall-7.png.png"));
        tileMap.addTile("Wall-8", AssetManager.getBufferedSprite("TileMap\\Wall-8.png.png"));
        tileMap.addTile("Wall-9", AssetManager.getBufferedSprite("TileMap\\Wall-9.png.png"));
        tileMap.addTile("WallTop-13", AssetManager.getBufferedSprite("TileMap\\WallTop-13.png.png"));
        tileMap.addTile("WallTop-14", AssetManager.getBufferedSprite("TileMap\\WallTop-14.png.png"));
        tileMap.addTile("WallTop-15", AssetManager.getBufferedSprite("TileMap\\WallTop-15.png.png"));
        tileMap.addTile("WallTop-19", AssetManager.getBufferedSprite("TileMap\\WallTop-19.png.png"));
        tileMap.addTile("WallTop-20", AssetManager.getBufferedSprite("TileMap\\WallTop-20.png.png"));
        tileMap.addTile("WallTop-21", AssetManager.getBufferedSprite("TileMap\\WallTop-21.png.png"));
        tileMap.addTile("WallTop-22", AssetManager.getBufferedSprite("TileMap\\WallTop-22.png.png"));

        tileMap.addTilesFromDirectory(AssetManager.getSpriteResourcePath("TileMap\\Table"));

        tileMap.addTile("Floor1", AssetManager.getBufferedSprite("TileMap\\Floor1.png"));
        tileMap.addTile("Floor2", AssetManager.getBufferedSprite("TileMap\\Floor2.png"));

        tileMap.addTilesFromDirectory(AssetManager.getSpriteResourcePath("TileMap\\Bookshelf"));
        tileMap.addTilesFromDirectory(AssetManager.getSpriteResourcePath("TileMap\\Board"));

        String[][] tileLayout = new String[][]{
            {"Wall-3", null},
            {"Wall-8", "Wall-9"},
        };

        TileLayout tileLayout1 = new TileLayout();
        tileLayout1.setTileMap(tileMap);
        tileLayout1.setTileLayout(tileLayout);

        TileLayoutRenderer tileLayoutRenderer = new TileLayoutRenderer();
        tileLayoutRenderer.addTileLayout(tileLayout1);

//        System.out.println(tileMap.getTile("Wall-3"));

        Player player = new Player();
        player.setFocusable(true);
        player.grabFocus();
        root.addChild(player);

        tileLayoutRenderer.setBounds(0, 0, getWidth(), getHeight());
        tileLayoutRenderer.repaint();
//        root.add(tileLayoutRenderer);

        Palette palette = new Palette();
        palette.setBounds(0, 0, getWidth(), getHeight());
//        root.addChild(palette);

        TileLayoutPalette tileLayoutPalette = new TileLayoutPalette(tileMap, 32, 32);
        tileLayoutPalette.setFocusable(true);
        tileLayoutPalette.grabFocus();
        tileLayoutPalette.setBounds(0, 0, getWidth(), getHeight());
        root.addChild(tileLayoutPalette);

        add(root);
    }

    public void UpdateHandler() {
        double pastTimeMillis = System.nanoTime() / 1e6;
        while (true) {
            double currentTimeMillis = System.nanoTime() / 1e6;
            float delta = (float) (currentTimeMillis - pastTimeMillis);
            root.UpdateHandler(delta);
            pastTimeMillis = currentTimeMillis;
        }
    }
}
