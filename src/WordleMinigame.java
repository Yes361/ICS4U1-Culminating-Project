import Components.Minigame;
import Core.GameSystem.AssetManager;
import Utility.Console;
import Utility.JSwingUtilities;
import Utility.WordList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class WordleMinigame extends Minigame {
    private String currentWord;
    private String[] guesses;
    private int currentGuessIndex = 0;
    private JTextPane textPane;
    private JTextField textField;
    private int limit = 5;

    public WordleMinigame() {
        setBounds(0, 0, 900, 600);

        WordList.parseWordsFromFile(AssetManager.getResourceDirectory("Miscellaneous\\valid-wordle-words.txt"));

        createMinigame();
    }


    private void createMinigame() {
        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        setLayout(boxLayout);

        currentWord = WordList.getRandomWordLength(5);
        guesses = new String[6];

        JLabel titleLabel = new JLabel("Wordle !!!");
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        JSwingUtilities.resizeFont(titleLabel, 24);

        add(titleLabel);
        add(Box.createVerticalStrut(10));

        createTextField();
        add(textField);

        add(Box.createVerticalStrut(10));

        createTextPane();
        add(textPane);
    }

    private void createTextField() {
        textField = new JTextField();
        textField.setAlignmentX(CENTER_ALIGNMENT);

        Dimension textFieldDimension = new Dimension(100, 50);
        textField.setPreferredSize(textFieldDimension);
        textField.setMaximumSize(textFieldDimension);
        textField.setMinimumSize(textFieldDimension);

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
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
        });
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


    private JTextPane createTextPane() {
        textPane = new JTextPane();
        textPane.setAlignmentX(CENTER_ALIGNMENT);

        textPane.setEditable(false);
        textPane.setContentType("text/html");
        textPane.setBounds(450, 200, 100, 500);

        Dimension dimension = new Dimension(100, 500);
        textPane.setPreferredSize(dimension);
        textPane.setMinimumSize(dimension);
        textPane.setMaximumSize(dimension);

        return textPane;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Image img = JSwingUtilities.resizeImageAspectLockedWithMinDimensions(AssetManager.getBufferedSprite("Minigame\\Backgrounds\\BGWordle.jpeg"), getWidth(), getHeight());
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public BufferedImage getMinigameIcon() {
        return AssetManager.getBufferedSprite("Minigame\\Thumbnails\\Wordle.jpeg");
    }

    @Override
    public String getMinigameName() {
        return "Wordle";
    }

    @Override
    public void showMinigame() {

    }

    @Override
    public void hideMinigame() {

    }

    @Override
    public void resetMinigame() {
        currentWord = WordList.getRandomWordLength(5);
        guesses = new String[6];

        textField.setText("");
        textPane.setText("");
    }
}
