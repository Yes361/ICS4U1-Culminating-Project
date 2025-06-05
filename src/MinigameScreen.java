import Components.MemoryCardMinigame;
import Core.GameSystem.JGameObject;
import Core.GameSystem.JGameObjectInterface;
import Utility.Console;

import javax.swing.*;

public class MinigameScreen extends JPanel implements JGameObjectInterface {
    JGameObject root = new JGameObject();

    public MinigameScreen() {
        setBounds(0, 0, 500, 500);
        setVisible(true);
        setLayout(null);

        root.setLayout(null);
        root.setBounds(0, 0, getWidth(), getHeight());
        root.setVisible(true);

        MemoryCardMinigame memoryCardMinigame = new MemoryCardMinigame();
        memoryCardMinigame.setBounds(0, 0, getWidth(), getHeight());

        root.addChild(memoryCardMinigame);

        add(root);
    }

    @Override
    public void update(float delta) {
        root.update(delta);

        Console.println("wa");
    }
}
