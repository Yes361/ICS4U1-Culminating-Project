/* UNUSED */

package Components;

import Utility.Console;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// TODO: Expanded inventory
public class Inventory extends JPanel {
    private ArrayList<InventoryItem> inventoryItems = new ArrayList<>();
    private Dimension inventorySlotDimensions = new Dimension(32, 32);

    public Inventory() {
        setLayout(null);
    }

    public ArrayList<InventoryItem> getInventoryItems() {
        return inventoryItems;
    }

    public InventoryItem getInventoryItem(int index) {
        return inventoryItems.get(index);
    }

    public void setInventoryItem(int index, InventoryItem inventoryItem) {
        inventoryItems.set(index, inventoryItem);
    }

    public void addInventoryItem(InventoryItem inventoryItem) {
        inventoryItems.add(inventoryItem);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = inventorySlotDimensions.width;
        int height = inventorySlotDimensions.height;

        for (int i = 0;i < inventoryItems.size();i++) {
//            Console.println(250, getY(), width, height);
            g.drawRect(0, 0, width, height);
            Image inventoryImage = inventoryItems.get(i).getInventoryTileSprite().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            g.drawImage(inventoryImage, 0, 0, this);
        }
    }
}
