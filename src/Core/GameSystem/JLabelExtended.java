package Core.GameSystem;

import Utility.GraphicUtilies;

import javax.swing.*;
import java.awt.*;

public class JLabelExtended extends JLabel {
    float rotation = 0;
    float rotationY = 0;

    public JLabelExtended(String label) {
        super(label);
    }

    public float getRotation() {
        return rotation;
    }

    public float getRotationY() {
        return rotationY;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
        repaint();
    }

    public void setRotationY(float rotationY) {
        this.rotationY = rotationY;
    }

    @Override
    protected void paintComponent(Graphics g) {
        GraphicUtilies.rotateGraphics(g, getWidth() / 2, getHeight() / 2, rotation);
        GraphicUtilies.rotateYGraphics(g, getWidth() / 2, getHeight() / 2, rotationY);
        super.paintComponent(g);
    }
}
