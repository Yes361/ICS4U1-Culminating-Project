import Components.*;
import Core.GameSystem.AssetManager;
import Core.GameSystem.JGameObject;
import Core.GameSystem.JGameObjectInterface;
import Utility.Console;
import Utility.EventListener;

import java.awt.*;
import java.io.File;
import java.util.Objects;

// WorldScreen is a game screen that shows the game world with a player, camera, collision, and dialogue.
public class WorldScreen extends Screen implements JGameObjectInterface {
    // Responsible for rendering tile maps
    TileLayoutRenderer tileLayoutRenderer = new TileLayoutRenderer();

    // The main character or actor in the game
    Player player;

    // Stores game map made up of tile sprites
    TileMap tileMap = new TileMap();

    // The root game object for the world
    JGameObject World;

    // 2D camera for following the player
    Camera2D camera2D;

    // Text box or dialogue renderer
    TextDialogDisplay textDialogDisplay;

    // Background image displayed before game starts
    private Image backgroundImage;

    public WorldScreen() {
        initScreen();
    }

    public void initScreen() {
//        setBounds(0, 0, getParent().getWidth(), getParent().getHeight());
        setBounds(0, 0, 900, 600);
        setLayout(null);
        setBackground(Color.BLACK);

        // Loading tile sets
        tileMap.addTilesFromDirectory(AssetManager.getSpriteResourcePath("TileMap"));
        tileMap.addTilesFromDirectory(AssetManager.getSpriteResourcePath("TileMap\\Table"));
        tileMap.addTilesFromDirectory(AssetManager.getSpriteResourcePath("TileMap\\Board"));
        tileMap.addTilesFromDirectory(AssetManager.getSpriteResourcePath("TileMap\\Bookshelf"));
        tileMap.addTilesFromDirectory(AssetManager.getSpriteResourcePath("TileMap\\Carpet"));
        tileMap.addTilesFromDirectory(AssetManager.getSpriteResourcePath("TileMap\\Door"));
        tileMap.addTilesFromDirectory(AssetManager.getSpriteResourcePath("TileMap\\Window"));

        backgroundImage = AssetManager.getBufferedSprite("Background\\DreamWorld.png");

        // Set up renderer for tile map
        tileLayoutRenderer.setBounds(0, 0, getWidth(), getHeight());
        tileLayoutRenderer.setTileMap(tileMap);
        tileLayoutRenderer.createLayoutFromFile(AssetManager.getResourceDirectory("Layouts\\dorm.txt"));

        // Create player character
        player = new Player();
        player.setFocusable(true);

        // Set up camera view following player
        camera2D = new Camera2D();
        camera2D.setBounds(0, 0, getWidth(), getHeight());

        // Set up collision engine with borders
        CollisionEngine collisionEngine = new CollisionEngine();
        collisionEngine.addChildExcludeRender(player);

        // Border collision
        RigidCollision2D top = new RigidCollision2D(0, 0, 900, 150);
        RigidCollision2D bottom = new RigidCollision2D(0, 600, 900, 150);
        RigidCollision2D left = new RigidCollision2D(0, 0, 10, 600);
        RigidCollision2D right = new RigidCollision2D(900, 0, 10, 600);

        // Initial player placement
        player.movePlayer(200, 200);

        // add borders to collision engine
        collisionEngine.addChildExcludeRender(top);
        collisionEngine.addChildExcludeRender(bottom);
        collisionEngine.addChildExcludeRender(left);
        collisionEngine.addChildExcludeRender(right);

        World = new JGameObject();
        World.setBounds(0, 0, getWidth(), getHeight());

        World.addChild(collisionEngine);

        camera2D.addChild(player);
        camera2D.addChild(tileLayoutRenderer);
        camera2D.setOpaque(false);

        // Update camera's center to follow player's movement
        player.onMove(new EventListener() {
            @Override
            public void onEvent(Object... args) {
                float x = (float) args[0];
                float y = (float) args[1];

                camera2D.setCenter(-player.getX() + (float) getWidth() / 2 - (float) player.getWidth() / 2,
                        -player.getY() + (float) getHeight() / 2 - (float) player.getHeight() / 2);
            }
        });

        World.addChild(camera2D);
        World.setVisible(false);

        add(World);

        // Display dialogue before the game starts
        textDialogDisplay = new TextDialogDisplay();
        textDialogDisplay.parseScriptFromTextFile(new File(AssetManager.getResourceDirectory("Dialogue\\Act1-Prologue.txt")));
        textDialogDisplay.play();

        textDialogDisplay.onScriptFinish(new EventListener() {
            @Override
            public void onEvent(Object... args) {
                World.setVisible(true);
                World.revalidate();
                World.repaint();

                remove(textDialogDisplay);

                revalidate();
                repaint();

                player.requestFocusInWindow();

                backgroundImage = null;
            }
        });

        add(textDialogDisplay);
    }

    @Override
    public void update(float delta) {
        if (World.isVisible()) {
            World.UpdateHandler(delta);
        }

        if (textDialogDisplay.isVisible()) {
            textDialogDisplay.update(delta);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    @Override
    public void showScreen() {

    }

    @Override
    public void hideScreen() {

    }
}
