import Utility.Console;
import Utility.WordList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class HangManMinigame extends JPanel implements KeyListener {
    private String currentWord;
    private boolean[] discovered;
    private JLabel currentLabel;

    public HangManMinigame() {
        setBounds(0, 0, 900, 600);
        currentWord = WordList.getRandomWord();
        discovered = new boolean[currentWord.length()];

        currentLabel = new JLabel();
        currentLabel.setText(new StringBuilder().repeat("_", currentWord.length()).toString());
        currentLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        currentLabel.setBounds(450, 300, 100, 30);

        add(currentLabel);

//        InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);

        addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        Console.println(currentWord);

        String skib = String.valueOf(e.getKeyChar());
        StringBuilder currentText = new StringBuilder(currentLabel.getText());

        if (currentWord.contains(skib)) {
            for (int i = 0;i < currentWord.length();i++) {
                if (currentWord.charAt(i) == e.getKeyChar()) {
                    currentText.setCharAt(i, e.getKeyChar());
                }
            }
        }

        currentLabel.setText(currentText.toString());
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
};
