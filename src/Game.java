import Components.*;
import Core.GameSystem.JGameObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

public class Game extends JFrame {
    JGameObject root;

    public Game() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);

        root = new JGameObject();
        root.setBounds(0, 0, getWidth(), getHeight());

        Player player = new Player();
        player.setFocusable(true);
        player.grabFocus();
        root.addChild(player);

        Palette palette = new Palette();
        palette.setBounds(0, 0, getWidth(), getHeight());
        root.addChild(palette);

        add(root);
    }

    public void UpdateHandler() {
        double pastTimeMillis = System.nanoTime() / 1e6;
        while (true) {
            double currentTimeMillis = System.nanoTime() / 1e6;
            float delta = (float) (currentTimeMillis - pastTimeMillis);
            root.UpdateHandler(delta);
            pastTimeMillis = currentTimeMillis;
        }
    }
}
