package Components;

import Core.GameSystem.JGameObject;
import Core.Input.Input;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Palette extends JGameObject {
    private List<List<Point>> lines = new ArrayList<>();

    private Input input = new Input() {
        @Override
        public void mouseReleased(MouseEvent e) {
            super.mouseReleased(e);

            lines.add(new ArrayList<>());
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            super.mouseDragged(e);

            lines.getLast().add(e.getPoint());
            repaint();
        }
    };

    public Palette() {
        addKeyListener(input);
        addMouseListener(input);
        addMouseMotionListener(input);

        lines.add(new ArrayList<>());
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (List<Point> line : lines) {
            int nPoints = line.size();
            int[] xPoints = new int[nPoints];
            int[] yPoints = new int[nPoints];

            for (int i = 0;i < nPoints;i++) {
                xPoints[i] = line.get(i).x;
                yPoints[i] = line.get(i).y;
            }

            g.drawPolyline(xPoints, yPoints, nPoints);
        }
    }
}
