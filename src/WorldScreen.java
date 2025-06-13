import Components.*;
import Core.GameSystem.AssetManager;
import Core.GameSystem.JGameObject;
import Utility.Console;
import Utility.EventListener;

import java.awt.*;
import java.io.File;
import java.util.Objects;

public class WorldScreen extends JGameObject {
    TileLayoutRenderer tileLayoutRenderer = new TileLayoutRenderer();
    Player player;
    TileMap tileMap = new TileMap();
    JGameObject World;
    Camera2D camera2D;

    public WorldScreen() {
        initScreen();
    }

    public void initScreen() {
//        setBounds(0, 0, getParent().getWidth(), getParent().getHeight());
        setBounds(0, 0, 900, 600);
        setBackground(Color.BLACK);

        tileMap.addTilesFromDirectory(AssetManager.getSpriteResourcePath("TileMap"));
        tileMap.addTilesFromDirectory(AssetManager.getSpriteResourcePath("TileMap\\Table"));
        tileMap.addTilesFromDirectory(AssetManager.getSpriteResourcePath("TileMap\\Board"));
        tileMap.addTilesFromDirectory(AssetManager.getSpriteResourcePath("TileMap\\Bookshelf"));
        tileMap.addTilesFromDirectory(AssetManager.getSpriteResourcePath("TileMap\\Carpet"));
        tileMap.addTilesFromDirectory(AssetManager.getSpriteResourcePath("TileMap\\Door"));
        tileMap.addTilesFromDirectory(AssetManager.getSpriteResourcePath("TileMap\\Window"));

        tileLayoutRenderer.setBounds(0, 0, getWidth(), getHeight());
        tileLayoutRenderer.setTileMap(tileMap);
        tileLayoutRenderer.createLayoutFromFile(AssetManager.getResourceDirectory("Layouts\\dorm.txt"));

        player = new Player();
        player.setFocusable(true);

        camera2D = new Camera2D();
        camera2D.setBounds(0, 0, getWidth(), getHeight());

        AreaTrigger areaTrigger = new AreaTrigger(90, 60, 30, 60) {
            @Override
            public void onCollision(Object other) {
                super.onCollision(other);

                if (!this.isVisible()) {
                    return;
                }

                if (other instanceof Player playerCollidable) {
                    camera2D.startUnFade();
                }
            }
        };

        AreaTrigger areaTriggerHall = new AreaTrigger(90, 60, 30, 60) {
            @Override
            public void onCollision(Object other) {
                super.onCollision(other);

                if (!this.isVisible()) {
                    return;
                }

                if (other instanceof Player playerCollidable) {
                    camera2D.startUnFade();
                }
            }
        };


        camera2D.onEffectFinish(new EventListener() {
            @Override
            public void onEvent(Object... args) {
                String currentEffect = (String) args[0];

                if (currentEffect.equals("UnFade")) {
                    camera2D.startFade();

                    if (areaTrigger.isVisible()) {
                        tileLayoutRenderer.createLayoutFromFile(AssetManager.getResourceDirectory("Layouts\\academy.txt"));
                        areaTrigger.setVisible(false);
                        areaTriggerHall.setVisible(true);
                    }
                    if (areaTriggerHall.isVisible()) {
                        tileLayoutRenderer.createLayoutFromFile(AssetManager.getResourceDirectory("Layouts\\academy_hall.txt"));
                        areaTriggerHall.setVisible(false);
                        areaTrigger.setVisible(true);
                    }
                }
            }
        });


        areaTriggerHall.setVisible(false);

        CollisionEngine collisionEngine = new CollisionEngine();
//        collisionEngine.addChildExcludeRender(areaTrigger);
//        collisionEngine.addChildExcludeRender(areaTriggerHall);
        collisionEngine.addChildExcludeRender(player);

        World = new JGameObject();
        World.setBounds(0, 0, getWidth(), getHeight());

        World.addChild(collisionEngine);

        camera2D.addChild(areaTrigger);
        camera2D.addChild(areaTriggerHall);
        camera2D.addChild(player);
        camera2D.addChild(tileLayoutRenderer);
        camera2D.setOpaque(false);

        player.onMove(new EventListener() {
            @Override
            public void onEvent(Object... args) {
                float x = (float) args[0];
                float y = (float) args[1];

                camera2D.setCenter((int) -x + getWidth() / 2 - player.getWidth() / 2, (int) -y + getHeight() / 2 - player.getHeight() / 2);
            }
        });

        World.addChild(camera2D);

        World.setVisible(false);

        TextDialogDisplay textDialogDisplay = new TextDialogDisplay();
        textDialogDisplay.parseScriptFromTextFile(new File(AssetManager.getResourceDirectory("Dialogue\\Act1-Prologue.txt")));
        textDialogDisplay.play();

        textDialogDisplay.onScriptFinish(new EventListener() {
            @Override
            public void onEvent(Object... args) {
                World.setVisible(true);
                addChild(World);

                remove(textDialogDisplay);

                revalidate();
                repaint();

                player.requestFocusInWindow();
            }
        });

        add(textDialogDisplay);
    }
}
