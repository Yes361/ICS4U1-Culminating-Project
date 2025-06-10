import Components.MemoryCardMinigame;
import Core.GameSystem.JGameObject;
import Core.GameSystem.JGameObjectInterface;
import Utility.Console;

import javax.swing.*;
import java.awt.*;

public class MinigameScreen extends JPanel implements JGameObjectInterface {
    JGameObject root;
    MemoryCardMinigame memoryCardMinigame;

    public MinigameScreen() {
        setBounds(0, 0, 900, 600);
        setLayout(null);
        setOpaque(true);

        root = new JGameObject();
        root.setOpaque(true);
        root.setLayout(null);
        root.setBounds(0, 0, getWidth(), getHeight());

//        memoryCardMinigame = new MemoryCardMinigame();
//        root.addChild(memoryCardMinigame);

        HangManMinigame hangManMinigame = new HangManMinigame();
        root.add(hangManMinigame);

        root.setVisible(true);
        add(root);

        repaint();
    }

    @Override
    public void update(float delta) {
        root.UpdateHandler(delta);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
