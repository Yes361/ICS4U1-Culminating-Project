package Core.GameSystem;

import Utility.GraphicUtilies;

import javax.swing.*;
import java.awt.*;

public class JLabelExtended extends JLabel {
    float rotation = 0;

    public JLabelExtended(String label) {
        super(label);
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        GraphicUtilies.rotateGraphics(g, rotation);
        super.paintComponent(g);
    }
}
