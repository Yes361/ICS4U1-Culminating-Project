import Components.Camera2D;
import Components.Particles;
import Components.Player;
import Components.TypewriterLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

public class Game extends JFrame {
    JPanel Exploration;
    public Game() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);

        setLayout(null);

        Player player = new Player();
        player.setLocation(0, 0);

        /* Exploration Screen */
        Camera2D camera2D = new Camera2D(0, 0);
        Exploration = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                camera2D.render(g);
            }

            public void update(float delta) {
                camera2D.setLocation(player.getX(), player.getY());
                System.out.println("skibi");
//                repaint();
            }
        };
        Exploration.setLayout(null);
        Exploration.setBounds(0, 0, getWidth(), getHeight());
        Exploration.add(player);

        JLabel label = new JLabel("skib JUNIOR!!!");
        label.setBounds(250, 20, 200, 50);
        Exploration.add(label);
        Exploration.setVisible(false);

        /* Main Menu */
        JPanel Menu = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

//                particles.render(g);
            }

            public void update(float delta) {

            }
        };
        Menu.setBounds(0, 0, getWidth(), getHeight());
        Menu.add(new JLabel("Chemistry RPG"));

        JButton startButton = new JButton("skib junior?");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Menu.setVisible(false);
                Exploration.setVisible(true);
                player.grabFocus();
            }
        });
        Menu.add(startButton);

        Menu.setVisible(true);

        particles = new Particles(Menu);
        particles.kaboom(new Point2D.Float(250, 250));

        add(Exploration);
        add(Menu);
    }

    Particles particles;

    public void update(JComponent parent, float delta) {
        for (Component child : parent.getComponents()) {
            if (child instanceof Player) {
                ((Player) child).update(delta);
            } else if (child instanceof TypewriterLabel) {
                ((TypewriterLabel) child).update(delta);
            } else if (child instanceof JComponent) {
                update((JComponent) child, delta);
            }
        }
//        particles.update(delta);
    }

    public void UpdateHandler() {
        double pastTimeMillis = System.nanoTime() / 1e6;
        while (true) {
            double currentTimeMillis = System.nanoTime() / 1e6;
            float delta = (float) (currentTimeMillis - pastTimeMillis);

            for (Component child : this.getComponents()) {
                if (child instanceof Player) {
                    ((Player) child).update(delta);
                } else if (child instanceof TypewriterLabel) {
                    ((TypewriterLabel) child).update(delta);
                } else if (child instanceof JComponent) {
                    update((JComponent) child, delta);
                }
            }

            pastTimeMillis = currentTimeMillis;
        }
    }
}
