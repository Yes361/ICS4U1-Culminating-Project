package Components;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public class InventoryItem implements Serializable {
    private BufferedImage itemTileSprite;
    private BufferedImage inventoryTileSprite;
    private String itemName;

    public InventoryItem(BufferedImage inventoryTileSprite, String itemName) {
        this.inventoryTileSprite = inventoryTileSprite;
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BufferedImage getInventoryTileSprite() {
        return inventoryTileSprite;
    }
}
