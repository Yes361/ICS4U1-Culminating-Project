/*
* UNUSED
*  */

package Components;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class InventoryItem implements Serializable {
    private BufferedImage itemTileSprite;
    private BufferedImage inventoryTileSprite;
    private String itemName;

    public InventoryItem(String inventoryTilePath, String itemName) {
        try {
            this.inventoryTileSprite = ImageIO.read(new File(inventoryTilePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.itemName = itemName;
    }

    public InventoryItem(BufferedImage inventoryTileSprite, String itemName) {
        this.inventoryTileSprite = inventoryTileSprite;
        this.itemName = itemName;
    }


    public InventoryItem(BufferedImage inventoryTileSprite, BufferedImage itemTileSprite, String itemName) {
        this.inventoryTileSprite = inventoryTileSprite;
        this.itemTileSprite = itemTileSprite;
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
