package Components;

import Core.GameSystem.AssetManager;
import Core.GameSystem.JGameObject;
import Utility.Console;
import Utility.graphicUtilities;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FlowerWateringMinigame extends JGameObject implements MouseListener {
    private float[] pH = new float[3];

    public FlowerWateringMinigame() {
        addMouseListener(this);

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(AssetManager.getBufferedSprite("PlantPot-1.png", 64, 128), 0, 0, this);
        g.drawImage(AssetManager.getBufferedSprite("PlantPot-1.png", 64, 128), 100, 0, this);
        g.drawImage(AssetManager.getBufferedSprite("PlantPot-1.png", 64, 128), 200, 0, this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (graphicUtilities.containsPoint(e.getPoint(), 0, 0, 64, 128)) {
            Console.println(1);
        }

        if (graphicUtilities.containsPoint(e.getPoint(), 100, 0, 64, 128)) {
            Console.println(2);
        }

        if (graphicUtilities.containsPoint(e.getPoint(), 200, 0, 64, 128)) {
            Console.println(3);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
