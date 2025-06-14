import Components.Minigame;
import Core.GameSystem.AssetManager;
import Core.GameSystem.JGameObjectInterface;
import Utility.JSwingUtilities;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

public class MemoryCardMinigame extends Minigame implements JGameObjectInterface {
    // Swing component references
    private JPanel panel;
    private JLabel[][] cards;

    // Selector variables
    private int firstX = -1;
    private int firstY = -1;
    private int secondX = -1;
    private int secondY = -1;

    // State Array Variables
    private boolean[][] discovered;
    private int[][] cardContents;

    // State values
    private float delay = 10;
    private boolean clicked = false;
    private float clickDelay = 1000;

    private int cardDeckWidth = 4;
    private int cardDeckHeight = 4;
    private String[] cardItems = {
            "Cinderglow",
            "Cradlevine",
            "Mirror-Moss",
            "Roselight-Claria",
            "Starlace-Fern",
            "Vireleaf-Bloom",
            "Whisperthorn",
            "Lumenbloom",
    };

    public MemoryCardMinigame() {
        // Setting minigame screen properties
        setBounds(0, 0,900, 600);
        setOpaque(true);
        setBackground(new Color(75, 47, 31));

        // Creating and applying a box layout
        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        setLayout(boxLayout);

        // Creating a title label
        JLabel titleLabel = new JLabel("Memory Card");
        JSwingUtilities.resizeFont(titleLabel, 24);
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(titleLabel);

        createMemoryCards();

        // Reset button
        JButton resetButton = new JButton("Reset !");
        resetButton.setAlignmentX(CENTER_ALIGNMENT);
        add(resetButton);

        // When button is pressed, reset the minigame
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetMinigame();
            }
        });

        JButton rulesButton = new JButton("Rules");
        rulesButton.setAlignmentX(CENTER_ALIGNMENT);
        rulesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MinigameRuleFrame.showPopup("""
                    Memory Cards
                    
                    Rules:
                    
                    There are 16 cards, 8 different cards. Each card has a different flower on it, but your goal is to flip two cards every turn to identify whether they are matching or not. If they are not, they will unflip, and in your turn you can flip another two cards. The game has been won once all matching pairs of cards have been flipped!
                    """);
            }
        });

        add(rulesButton);

        repaint();
    }

    public void createMemoryCards() {
        // Create a panel
        panel = new JPanel();
        panel.setBounds(100, 100, 700, 400);
        panel.setOpaque(false);

        // Creating and applying a gridlayout with dimensions
        // cardDeckWidth x cardDeckHeight
        GridLayout gridLayout = new GridLayout(cardDeckWidth, cardDeckHeight);

        // GridLayout properties
        gridLayout.setHgap(5);
        gridLayout.setVgap(5);

        panel.setLayout(gridLayout);

        cards = new JLabel[cardDeckWidth][cardDeckHeight];
        randomize();

        // Setting size
        Dimension dimension = new Dimension(500, 400);
        panel.setMinimumSize(dimension);
        panel.setMaximumSize(dimension);
        panel.setPreferredSize(dimension);

        add(panel);

        // Creating the JLabels
        for (int i = 0;i < cardDeckWidth;i++) {
            for (int j = 0;j < cardDeckHeight;j++) {
                JLabel label = new JLabel();

                label.setOpaque(true);
                label.setBorder(new CompoundBorder(
                        BorderFactory.createLineBorder(Color.BLACK),
                        new EmptyBorder(10, 10, 10, 10)
                ));

                int row = i;
                int col = j;

                // Adding a listener for when the label is pressed
                label.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        Insets insets = label.getInsets();
                        BufferedImage bufferedImage = null;

                        // Ignore if the card is already discovered
                        if (discovered[row][col]) {
                            return;
                        }

                        // Ignore if the card was already selected
                        if (firstX == row && firstY == col) {
                            return;
                        }

                        // What to do when first card hasn't been selected
                        if (firstX == -1 && firstY == -1) {
                            // Set the first card selector variables to row, col
                            firstX = row;
                            firstY = col;

                            // Icon to set to
                            bufferedImage = AssetManager.getBufferedSprite(String.format("Minigame\\MemoryCards\\%s.png", cardItems[cardContents[firstX][firstY] - 1]));
                            updateBorder(row, col, true);

                        } else if (secondX == -1 && secondY == -1) { // What to do when first card is selected but second isn't
                            secondX = row;
                            secondY = col;

                            // Icon to set
                            bufferedImage = AssetManager.getBufferedSprite(String.format("Minigame\\MemoryCards\\%s.png", cardItems[cardContents[secondX][secondY] - 1]));
                            updateBorder(row, col, true);

                            // Code executed when the card contents of the cards match
                            if (cardContents[firstX][firstY] == cardContents[secondX][secondY]) {
                                // Set the backgrounds to green to indicate that the cards matched
                                cards[firstX][firstY].setBackground(Color.GREEN);
                                cards[secondX][secondY].setBackground(Color.GREEN);

                                // Set them to be discovered
                                discovered[firstX][firstY] = true;
                                discovered[secondX][secondY] = true;

                                // Reset the selector variables
                                firstX = -1;
                                firstY = -1;
                                secondX = -1;
                                secondY = -1;
                            } else {
                                // If the card contents do not match set a delay countdown

                                delay = 0;
                                clicked = true;
                            }
                        }

                        // Ignore if bufferedImage is null somehow
                        if (bufferedImage == null) {
                            return;
                        }

                        // Set the card contents to the bufferedImage resized to the size of the card
                        // accounting for margins (insets + arbitrary value)
                        int horizontalMargin = insets.left + insets.right + 40;
                        int verticalMargin = insets.top + insets.bottom + 40;

                        Image resizedImage = JSwingUtilities.resizeImageAspectLockedWithMinDimensions(bufferedImage, label.getWidth() - horizontalMargin, label.getHeight() - verticalMargin);
                        cards[row][col].setIcon(new ImageIcon(resizedImage));
                    }
                });

                cards[i][j] = label;
                panel.add(label);
            }
        }
    }

    // Randomize the memory cards
    private void randomize() {
        Random rand = new Random();

        // Reset the discovered, and cardContents
        cardContents = new int[cardDeckWidth][cardDeckHeight];
        discovered = new boolean[cardDeckWidth][cardDeckHeight];

        // Iterating through each possible item and creating
        // two random locations containing that item
        for (int i = 0;i < cardItems.length;i++) {
            int x, y;

            do {
                x = rand.nextInt(0, cardDeckWidth);
                y = rand.nextInt(0, cardDeckHeight);
            } while (cardContents[x][y] != 0); // Repeat till a unique location is found

            cardContents[x][y] = i + 1;

            do {
                x = rand.nextInt(0, cardDeckWidth);
                y = rand.nextInt(0, cardDeckHeight);
            } while (cardContents[x][y] != 0); // Repeat till a unique location is found

            cardContents[x][y] = i + 1;
        }
    }

    @Override
    public void update(float delta) {
        // Increment delay
        delay += delta;

        // If delay surpasses the threshold and the cards
        // were clicked, hide the mismatched cards
        if (delay > clickDelay && clicked) {
            hideCards();
        }
    }

    /**
     * getCardName returns the name of contents of the card at [row, col]
     *
     * @param row
     * @param col
     *
     * @return Card name
     */
    private String getCardName(int row, int col) {
        return cardItems[cardContents[row][col] - 1];
    }

    /**
     * updateBorder updates the border the cards depending on their state of being shown or hidden
     *
     * @param row
     * @param col
     * @param show is a boolean indicating visibility
     */
    private void updateBorder(int row, int col, boolean show) {
        if (show) {
            // Set the card border to show the name of the card through
            // a titled border
            cards[row][col].setBorder(new CompoundBorder(
                    new TitledBorder(
                            new LineBorder(Color.BLACK, 3),
                            getCardName(row, col),
                            TitledBorder.DEFAULT_JUSTIFICATION,
                            TitledBorder.DEFAULT_POSITION,
                            new Font("Arial", Font.PLAIN, 16)
                    ),
                    new EmptyBorder(10, 10, 10, 10)
            ));
        } else {
            // Regular border
            cards[row][col].setBorder(new CompoundBorder(
                    BorderFactory.createLineBorder(Color.BLACK),
                    new EmptyBorder(10, 10, 10, 10)
            ));
        }
    }

    /**
     * Hides the cards
     */
    public void hideCards() {
        if (firstX == -1 && secondX == -1) {
            return;
        }

        cards[firstX][firstY].setIcon(null);
        cards[secondX][secondY].setIcon(null);

        updateBorder(firstX, firstY, false);
        updateBorder(secondX, secondY, false);

        firstX = -1;
        firstY = -1;
        secondX = -1;
        secondY = -1;

        clicked = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Rendering the background
        Image img = JSwingUtilities.resizeImageAspectLockedWithMinDimensions(AssetManager.getBufferedSprite("Minigame\\Backgrounds\\BGConnectFour.png"), getWidth(), getHeight());
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public BufferedImage getMinigameIcon() {
        return AssetManager.getBufferedSprite("Minigame\\Thumbnails\\Memory Cards.jfif");
    }

    @Override
    public String getMinigameName() {
        return "Memory Cards!";
    }

    @Override
    public void showMinigame() {}

    @Override
    public void hideMinigame() {}


    @Override
    public void resetMinigame() {
        randomize();

        hideCards();

        for (int i = 0;i < cards.length;i++) {
            for (int j = 0;j < cards[i].length;j++) {
                cards[i][j].setBackground(Color.WHITE);
                cards[i][j].setIcon(null);
                updateBorder(i, j, false);
            }
        }
    }
}