import Animation.AnimationEvent;
import Animation.AnimationRenderer;
import Animation.AnimationSprite;
import Animation.AnimationSprites;
import Components.Minigame;
import Core.GameSystem.AssetManager;
import Core.GameSystem.JGameObjectInterface;
import Utility.Console;
import Utility.WordList;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

public class HangManMinigame extends Minigame implements KeyListener, JGameObjectInterface {
    private String currentWord;
    private boolean[] discovered;
    private JLabel currentLabel;
    private int attemptIndex = 0;
    private JLabel image;
    private JLabel wrong_guesses;
    private JLabel messageLabel;
    private AnimationRenderer fireRenderer;

    public HangManMinigame() {
        setBounds(0, 0, 900, 600);

        image = new JLabel(getIcon(1));
        add(image);

        wrong_guesses = new JLabel();
        wrong_guesses.setMinimumSize(new Dimension(100, 50));
        wrong_guesses.setBorder(new CompoundBorder(new TitledBorder("Wrong Guesses"), new EmptyBorder(10, 10, 10, 10)));
        add(wrong_guesses);

        messageLabel = new JLabel();
        add(messageLabel);

        currentLabel = new JLabel();
        currentLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        currentLabel.setBounds(450, 300, 100, 30);

        add(currentLabel);

        AnimationSprite fireSprites = new AnimationSprite(
            80,
            AssetManager.getSpriteResourcePath("Minigame\\Fire\\Fire-1.png"),
            AssetManager.getSpriteResourcePath("Minigame\\Fire\\Fire-2.png"),
            AssetManager.getSpriteResourcePath("Minigame\\Fire\\Fire-3.png")
        );

        JLabel fireComponent = new JLabel();
        fireComponent.setSize(new Dimension(200, 200));
        add(fireComponent);

        AnimationSprites animationSprites = new AnimationSprites();
        animationSprites.addAnimation("fire", fireSprites);

        fireRenderer = new AnimationRenderer(fireComponent, animationSprites);
        fireRenderer.onAnimationEvent(new AnimationEvent() {
            @Override
            public void onEvent(Object... args) {
                String currentAnimationName = (String) args[0];
                int currentFrame = (int) args[1];

                fireComponent.setIcon(new ImageIcon(fireRenderer.getCurrentSprite().image()));
            }
        });
        fireRenderer.setCurrentAnimation("fire");

        resetMinigame();

        addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (isWin()) {
            return;
        }

//        Console.println("Debug:", currentWord);

        String skib = String.valueOf(e.getKeyChar());
        StringBuilder currentText = new StringBuilder(currentLabel.getText());

        if (!Character.isLetter(e.getKeyChar())) {
            messageLabel.setText("Not a character!");
            return;
        } else if (wrong_guesses.getText().contains(skib)) {
            messageLabel.setText("Already Guessed!");
            return;
        }

        messageLabel.setText("");

        if (currentWord.contains(skib)) {
            for (int i = 0;i < currentWord.length();i++) {
                if (currentWord.charAt(i) == e.getKeyChar()) {
                    currentText.setCharAt(2 * i, e.getKeyChar());
                    discovered[i] = true;
//                    Console.println(Arrays.toString(discovered));
                }
            }
        } else {
            attemptIndex++;

            if (attemptIndex > 5) {
                messageLabel.setText("You Lost!");
                return;
            }

            image.setIcon(getIcon(attemptIndex + 1));
            wrong_guesses.setText(skib + ", " + wrong_guesses.getText());
        }

        currentLabel.setText(currentText.toString());

        if (isWin()) {
            messageLabel.setText("You Won!");
        }
    }

    public boolean isWin() {
        for (int i = 0;i < discovered.length;i++) {
            if (!discovered[i]) {
                return false;
            }
        }
        return true;
    }

    public ImageIcon getIcon(int index) {
        return new ImageIcon(
            AssetManager.getBufferedSprite(
                String.format("Minigame\\Hangman\\Hangman-%d.png", index),
                getWidth() / 2,
                getHeight() / 2
            )
        );
    }

    @Override
    public void update(float delta) {
        fireRenderer.update(delta);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void showMinigame() {
        requestFocus();
        setVisible(true);
    }

    @Override
    public void hideMinigame() {

    }

    @Override
    public void resetMinigame() {
        currentWord = WordList.getRandomWord();
        discovered = new boolean[currentWord.length()];

        currentLabel.setText(new StringBuilder().repeat("_ ", currentWord.length()).toString());
    }
};
