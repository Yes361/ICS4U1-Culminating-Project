import Core.GameSystem.AssetManager;
import Utility.Console;
import Utility.WordList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;

public class WordleMinigame extends JPanel implements KeyListener {
    private String currentWord;
    private String[] guesses;
    private int currentGuessIndex = 0;

    public WordleMinigame() {
        WordList.parseWordsFromFile(AssetManager.getResourceDirectory("Miscellaneous\\valid-wordle-words.txt"));

        setBounds(0, 0, 900, 600);
        setLayout(null);

        currentWord = WordList.getRandomWordLength(5);

        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setContentType("text/html");
        textPane.setBounds(450, 200, 100, 500);

        add(textPane);

        guesses = new String[6];

        int limit = 5;

        JTextField textField = new JTextField();
        textField.setBounds(450, 100, 100, 50);
        textField.setMinimumSize(new Dimension(100, 50));
        textField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getComponent() instanceof JTextField textField) {
                    String currentGuess = textField.getText();

                    if (currentGuess.length() >= limit) {
                        e.consume();
                    }

                    if (currentGuess.length() == limit && e.getKeyChar() == KeyEvent.VK_ENTER) {

                        if (WordList.isWord(currentWord)) {
                            Console.println("Not a word!");
                        }

                        guesses[currentGuessIndex] = currentGuess;
                        currentGuessIndex++;

                        textPane.setText(formatAllGuesses());
                    }
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        add(textField);

        addKeyListener(this);
    }

    private String[] getColorForWords(String word) {
        String[] colors = new String[word.length()];

        for (int i = 0;i < currentWord.length();i++) {
            if (currentWord.charAt(i) == word.charAt(i)) {
                colors[i] = "#00FF00";
            } else if (currentWord.indexOf(word.charAt(i)) != -1) {
                colors[i] = "#888800";
            } else {
                colors[i] = "#000000";
            }
        }

        return colors;
    }

    private String formatColorForWords(String word, String[] colors) {
        StringBuilder builder = new StringBuilder();

        builder.append("<p>");

        for (int i = 0;i < word.length();i++) {
            builder.append(String.format("<font color=%s>", colors[i]));
            builder.append(word.charAt(i));
            builder.append("</font>");
        }

        builder.append("</p>");

        return builder.toString();
    }

    private String formatAllGuesses() {
        StringBuilder builder = new StringBuilder();

        builder.append("<html>");

        for (int i = 0;i < currentGuessIndex;i++) {
            builder.append(formatColorForWords(guesses[i], getColorForWords(guesses[i])));
        }

        builder.append("</html>");

        return builder.toString();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
