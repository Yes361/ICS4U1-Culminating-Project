import Components.Player;
import Components.TextDialogDisplay;
import Components.TileLayoutRenderer;
import Components.TileMap;
import Core.GameSystem.AssetManager;
import Core.GameSystem.JGameObject;

import java.io.File;

public class WorldScreen extends JGameObject {
    TileLayoutRenderer tileLayoutRenderer = new TileLayoutRenderer();
    Player player = new Player();
    TileMap tileMap = new TileMap();


    public WorldScreen() {
        setBounds(0, 0, 500, 500);

        tileMap.addTilesFromDirectory(AssetManager.getSpriteResourcePath("TileMap"));
        tileMap.addTilesFromDirectory(AssetManager.getSpriteResourcePath("TileMap\\Table"));
        tileMap.addTilesFromDirectory(AssetManager.getSpriteResourcePath("TileMap\\Board"));
        tileMap.addTilesFromDirectory(AssetManager.getSpriteResourcePath("TileMap\\Bookshelf"));

        tileLayoutRenderer.setBounds(0, 0, getWidth(), getHeight());
        tileLayoutRenderer.setTileMap(tileMap);
        tileLayoutRenderer.createLayoutFromFile(AssetManager.getResourceDirectory("Layouts\\layout.txt"));

        add(tileLayoutRenderer);

        TextDialogDisplay textDialogDisplay = new TextDialogDisplay();
        textDialogDisplay.loadScriptFromFile(new File(AssetManager.getResourceDirectory("Dialogue\\exemplar.txt")));

        add(textDialogDisplay);
    }
}
