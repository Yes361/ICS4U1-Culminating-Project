import Components.MemoryCardMinigame;
import Core.GameSystem.JGameObject;
import Core.GameSystem.JGameObjectInterface;
import Utility.Console;

import javax.swing.*;
import java.awt.*;

public class MinigameScreen extends JPanel implements JGameObjectInterface {
    JGameObject root;
    MemoryCardMinigame memoryCardMinigame;
    HangManMinigame hangManMinigame;
    WordleMinigame wordleMinigame;
    TicTacToeMinigame ticTacToeMinigame;

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

        hangManMinigame = new HangManMinigame();
        hangManMinigame.setFocusable(true);
        hangManMinigame.setVisible(false);
        root.add(hangManMinigame);

        ticTacToeMinigame = new TicTacToeMinigame();
        ticTacToeMinigame.setFocusable(true);
        ticTacToeMinigame.setVisible(true);
        root.add(ticTacToeMinigame);

        wordleMinigame = new WordleMinigame();
        wordleMinigame.setFocusable(true);
        wordleMinigame.setVisible(false);
        root.add(wordleMinigame);


        wordleMinigame = new WordleMinigame();
        wordleMinigame.setFocusable(true);
        wordleMinigame.setVisible(false);
        root.add(wordleMinigame);

        root.setVisible(true);
        add(root);

        repaint();
    }

    public void SwitchGame() {

    }


    public TicTacToeMinigame getTicTacToeMinigame() {
        return ticTacToeMinigame;
    }

    public HangManMinigame getHangManMinigame() {
        return hangManMinigame;
    }

    public WordleMinigame getWordleMinigame() {
        return wordleMinigame;
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
