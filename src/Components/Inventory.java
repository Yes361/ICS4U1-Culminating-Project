package Components;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// TODO: Expanded inventory
public class Inventory extends JComponent {
    private ArrayList<InventoryItem> inventoryItems = new ArrayList<>();
    private Dimension inventorySlotDimensions = new Dimension(50, 50);

    public ArrayList<InventoryItem> getInventoryItems() {
        return inventoryItems;
    }

    public InventoryItem getInventoryItem(int index) {
        return inventoryItems.get(index);
    }

    public void setInventoryItem(int index, InventoryItem inventoryItem) {
        inventoryItems.set(index, inventoryItem);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = inventorySlotDimensions.width;
        int height = inventorySlotDimensions.height;
        for (int i = 0;i < inventoryItems.size();i++) {
            g.drawRect(getX(), getY(), width, height);
            Image inventoryImage = inventoryItems.get(i).getInventoryTileSprite().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            g.drawImage(inventoryImage, getX(), getY(), this);
        }
    }
}
