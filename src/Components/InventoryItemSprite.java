/*
* UNUSED
*  */

package Components;

import Core.GameSystem.AssetManager;
import Core.GameSystem.JGameObject;

public class InventoryItemSprite extends AreaTrigger {
    private InventoryItem inventoryItem;

    public InventoryItemSprite() {
       inventoryItem = new InventoryItem(AssetManager.getBufferedSprite("TileMap\\Pot-23.png.png"), AssetManager.getBufferedSprite("TileMap\\Pot-23.png.png"), "Skibidi");

    }

}
