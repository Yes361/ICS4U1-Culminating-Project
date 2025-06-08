import Components.*;
import Core.GameSystem.AssetManager;
import Core.GameSystem.JGameObject;
import Utility.Console;
import Utility.EventListener;

import javax.swing.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;

public class Game extends JFrame {
    JGameObject root;

    public Game() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLayout(null);

        initializeAssets();

        root = new JGameObject();

        root.setBounds(0, 0, getWidth(), getHeight());

        TileMap tileMap = new TileMap();
        tileMap.addTile("Pot-23", AssetManager.getBufferedSprite("TileMap\\Pot-23.png"));
        tileMap.addTile("Wall-3", AssetManager.getBufferedSprite("TileMap\\Wall-3.png"));
        tileMap.addTile("Wall-4", AssetManager.getBufferedSprite("TileMap\\Wall-4.png"));
        tileMap.addTile("Wall-5", AssetManager.getBufferedSprite("TileMap\\Wall-5.png"));
        tileMap.addTile("Wall-7", AssetManager.getBufferedSprite("TileMap\\Wall-7.png"));
        tileMap.addTile("Wall-8", AssetManager.getBufferedSprite("TileMap\\Wall-8.png"));
        tileMap.addTile("Wall-9", AssetManager.getBufferedSprite("TileMap\\Wall-9.png"));
        tileMap.addTile("WallTop-13", AssetManager.getBufferedSprite("TileMap\\WallTop-13.png"));
        tileMap.addTile("WallTop-14", AssetManager.getBufferedSprite("TileMap\\WallTop-14.png"));
        tileMap.addTile("WallTop-15", AssetManager.getBufferedSprite("TileMap\\WallTop-15.png"));
        tileMap.addTile("WallTop-19", AssetManager.getBufferedSprite("TileMap\\WallTop-19.png"));
        tileMap.addTile("WallTop-20", AssetManager.getBufferedSprite("TileMap\\WallTop-20.png"));
        tileMap.addTile("WallTop-21", AssetManager.getBufferedSprite("TileMap\\WallTop-21.png"));
        tileMap.addTile("WallTop-22", AssetManager.getBufferedSprite("TileMap\\WallTop-22.png"));

        tileMap.addTilesFromDirectory(AssetManager.getSpriteResourcePath("TileMap\\Table"));

        tileMap.addTile("Floor1", AssetManager.getBufferedSprite("TileMap\\Floor1.png"));
        tileMap.addTile("Floor2", AssetManager.getBufferedSprite("TileMap\\Floor2.png"));

        tileMap.addTilesFromDirectory(AssetManager.getSpriteResourcePath("TileMap\\Bookshelf"));
        tileMap.addTilesFromDirectory(AssetManager.getSpriteResourcePath("TileMap\\Board"));

        Camera2D camera2D = new Camera2D();
        camera2D.setBounds(0, 0, getWidth(), getHeight());

        String[][] tileLayout = new String[][]{
            {"Wall-3", null},
            {"Wall-8", "Wall-9"},
        };

        TileLayout tileLayout1 = new TileLayout();
        tileLayout1.setTileLayout(tileLayout);

        TileLayoutRenderer tileLayoutRenderer = new TileLayoutRenderer();
        tileLayoutRenderer.setTileMap(tileMap);
        tileLayoutRenderer.createLayoutFromFile(AssetManager.getResourceDirectory("Layouts\\layout.txt"));

        Player player = new Player();
        player.setFocusable(true);
        player.grabFocus();

        player.onMove(new EventListener() {
            @Override
            public void onEvent(Object... args) {
                float x = (float) args[0];
                float y = (float) args[1];

                camera2D.setCenter((int) -x + getWidth() / 2 - player.getWidth() / 2, (int) -y + getHeight() / 2 - player.getHeight() / 2);
            }
        });

        camera2D.add(player);

        tileLayoutRenderer.setBounds(0, 0, getWidth(), getHeight());
        tileLayoutRenderer.repaint();
        camera2D.add(tileLayoutRenderer);

        Palette palette = new Palette();
        palette.setBounds(0, 0, getWidth(), getHeight());
//        root.addChild(palette);

        TileLayoutPalette tileLayoutPalette = new TileLayoutPalette(tileMap, 32, 32);
        tileLayoutPalette.setBounds(0, 0, getWidth(), getHeight());
        addKeyListener(tileLayoutPalette);

//        root.addChild(tileLayoutPalette);

//      TODO: Make Camera2D anchorable to a JComponent
        camera2D.setCenter(50, 50);

        AreaTrigger areaTrigger1 = new AreaTrigger(0, 0, 50, 50);
        camera2D.add(areaTrigger1);


        World world = new World(tileMap);
        world.addChildExcludeRender(player);
        world.addChildExcludeRender(areaTrigger1);


        tileLayoutRenderer.addTileSprite(new TileLayoutSprite(0,0,new TileLayout(new String[][]{{"Wall-3"}})));

//        FlowerWateringMinigame flowerWateringMinigame = new FlowerWateringMinigame();
//        flowerWateringMinigame.setBounds(0, 0, getWidth(), getHeight());
//        root.addChild(flowerWateringMinigame);

        Inventory inventory = new Inventory();
        inventory.setBounds(0, 400, 500, 100);

        InventoryItem inventoryItem = new InventoryItem(AssetManager.getBufferedSprite("TileMap\\Floor1.png"), AssetManager.getBufferedSprite("TileMap\\Floor1.png"), "FloorItem");
        inventory.addInventoryItem(inventoryItem);

//        root.setLayout(null);
//        root.addChild(camera2D);
//        root.addChildExcludeRender(world);
//        root.add(inventory);
//        root.setComponentZOrder(inventory, 0);
//        root.add(textDialogDisplay);
//        root.setComponentZOrder(textDialogDisplay, 0);

//        add(new MenuScreen());



//        root.add(new MinigameScreen());

        add(root);
    }

    public void initializeAssets() {
        AssetManager.loadFont("LibreBaskerville", new File(AssetManager.getResourceDirectory("Fonts\\LibreBaskerville-Regular.ttf")));
//        AssetManager.loadFont("Pokemon", new File(AssetManager.getResourceDirectory("Fonts\\LibreBaskerville-Regular.ttf")));
    }

    public void initializePanels() {

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
