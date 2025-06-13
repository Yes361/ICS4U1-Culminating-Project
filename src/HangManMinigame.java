import Animation.AnimationEvent;
import Animation.AnimationRenderer;
import Animation.AnimationSprite;
import Animation.AnimationSprites;
import Components.Minigame;
import Core.GameSystem.AssetManager;
import Core.GameSystem.JGameObjectInterface;
import Utility.Console;
import Utility.JSwingUtilities;
import Utility.WordList;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
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
        setLayout(new FlowLayout(FlowLayout.LEFT));

        JPanel panel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(panel,BoxLayout.PAGE_AXIS);
        panel.setLayout(boxLayout);
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setOpaque(false);

        add(panel);

        image = new JLabel(getIcon(1));
        panel.add(image);

        JPanel panelSKIB = new JPanel();
        BoxLayout boxLayout1 = new BoxLayout(panelSKIB, BoxLayout. PAGE_AXIS);
        panelSKIB.setLayout(boxLayout1);

        wrong_guesses = new JLabel();
        Dimension dimension = new Dimension(200, 50);
        wrong_guesses.setMinimumSize(dimension);
        wrong_guesses.setPreferredSize(dimension);
        wrong_guesses.setMaximumSize(dimension);
        wrong_guesses.setBorder(new CompoundBorder(new TitledBorder("Wrong Guesses"), new EmptyBorder(10, 10, 10, 10)));
        panelSKIB.add(wrong_guesses);

        messageLabel = new JLabel();
        panelSKIB.add(messageLabel);

        currentLabel = new JLabel();
        currentLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        currentLabel.setBounds(450, 300, 100, 30);
        panelSKIB.add(currentLabel);

        add(panelSKIB);

        AnimationSprite fireSprites = new AnimationSprite(
            80,
            AssetManager.getSpriteResourcePath("Minigame\\Fire\\Fire-1.png"),
            AssetManager.getSpriteResourcePath("Minigame\\Fire\\Fire-2.png"),
            AssetManager.getSpriteResourcePath("Minigame\\Fire\\Fire-3.png")
        );

        JLabel fireComponent = new JLabel();
        fireComponent.setSize(new Dimension(200, 200));
        panel.add(fireComponent);

        AnimationSprites animationSprites = new AnimationSprites();
        animationSprites.addAnimation("fire", fireSprites);

        fireRenderer = new AnimationRenderer(fireComponent, animationSprites);
        fireRenderer.onAnimationEvent(new AnimationEvent() {
            @Override
            public void onEvent(Object... args) {
                String currentAnimationName = (String) args[0];
                int currentFrame = (int) args[1];

                Image fireImage = fireRenderer.getCurrentSprite().image();
                fireComponent.setIcon(new ImageIcon(JSwingUtilities.resizeImageAspectLocked(fireComponent, fireImage, image.getWidth())));
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
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Image img = JSwingUtilities.resizeImageAspectLockedWithMinDimensions(AssetManager.getBufferedSprite("Minigame\\Backgrounds\\BGHangMolecule.jpeg"), getWidth(), getHeight());
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public BufferedImage getMinigameIcon() {
        return AssetManager.getBufferedSprite("Minigame\\Thumbnails\\Hang Molecule.jfif");
    }

    @Override
    public String getMinigameName() {
        return "HangMan";
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
        setVisible(true);
        setFocusable(true);
        requestFocus();
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
