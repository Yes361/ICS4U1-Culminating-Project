package Components;

import Core.GameSystem.JGameObject;
import Core.GameSystem.JLabelExtended;
import Utility.Console;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MemoryCardMinigame extends JGameObject implements MouseListener {
    JPanel panel = new JPanel();

    public MemoryCardMinigame() {
        createMemoryCards();
//        add(panel);
    }

    public void createMemoryCards() {
        for (int i = 0;i < 5;i++) {
            for (int j = 0;j < 5;j++) {
                JLabelExtended label = new JLabelExtended("What");
                label.setRotation(45);
//                JLabel label = new JLabel("What");
                int x = i;
                int y = j;
//                label.setBorder(new CompoundBorder(BorderFactory.createLineBorder(Color.BLACK), new EmptyBorder(10, 10, 10, 10)));
                label.setBounds(i * 50, j * 50, 45, 45);

                label.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        Console.println(x, y);
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
                });

//                panel.add(label);
                add(label);
            }
        }
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        Console.println("what");

        for (Component component : getComponents()) {
            if (component instanceof JLabelExtended) {
                JLabelExtended labelExtended = (JLabelExtended) component;
                labelExtended.setRotation((float) (labelExtended.getRotation() + 0.2));
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

    }

    @Override
    public void mouseClicked(MouseEvent e) {

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
