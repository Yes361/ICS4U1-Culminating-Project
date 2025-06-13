package Components;

import Core.GameSystem.AssetManager;
import Core.GameSystem.JGameObject;
import Core.GameSystem.JGameObjectInterface;
import Core.GameSystem.JLabelExtended;
import Utility.Console;
import Utility.JSwingUtilities;
import Utility.RandomUtilities;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class MemoryCardMinigame extends Minigame implements JGameObjectInterface {
    JPanel panel;
    private int firstX = -1;
    private int firstY = -1;
    private int secondX = -1;
    private int secondY = -1;

    private boolean[][] discovered;
    private JLabel[][] cards;
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
    private int[][] cardContents;
    private float delay = 10;
    private boolean clicked = false;
    private float clickDelay = 1000;

    private int cardDeckWidth = 4;
    private int cardDeckHeight = 4;

    public MemoryCardMinigame() {
        setBounds(0, 0,900, 600);
        setOpaque(true);
        setBackground(new Color(75, 47, 31));

        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        setLayout(boxLayout);

        JLabel titleLabel = new JLabel("Memory Card");
        JSwingUtilities.resizeFont(titleLabel, 24);
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(titleLabel);

        createMemoryCards();

        JButton resetButton = new JButton("Reset !");
        resetButton.setAlignmentX(CENTER_ALIGNMENT);
        add(resetButton);

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetMinigame();
            }
        });

        repaint();
    }

    public void createMemoryCards() {
        panel = new JPanel();
        panel.setBounds(100, 100, 700, 400);

        GridLayout gridLayout = new GridLayout(cardDeckWidth, cardDeckHeight);
        gridLayout.setHgap(5);
        gridLayout.setVgap(5);

        panel.setLayout(gridLayout);

        cards = new JLabel[cardDeckWidth][cardDeckHeight];

        randomize();

        Dimension dimension = new Dimension(500, 400);
        panel.setOpaque(false);
        panel.setMinimumSize(dimension);
        panel.setMaximumSize(dimension);
        panel.setPreferredSize(dimension);

        add(panel);

        for (int i = 0;i < cardDeckWidth;i++) {
            for (int j = 0;j < cardDeckHeight;j++) {
                JLabel label = new JLabel();

                label.setOpaque(true);
                label.setBorder(new CompoundBorder(BorderFactory.createLineBorder(Color.BLACK), new EmptyBorder(10, 10, 10, 10)));


                int row = i;
                int col = j;

                label.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        Insets insets = label.getInsets();
                        BufferedImage bufferedImage = null;

                        if (discovered[row][col]) {
                            return;
                        }

                        if (firstX == row && firstY == col) {
                            return;
                        }

                        if (firstX == -1 && firstY == -1) {
                            firstX = row;
                            firstY = col;

                            bufferedImage = AssetManager.getBufferedSprite(String.format("Minigame\\MemoryCards\\%s.png", cardItems[cardContents[firstX][firstY] - 1]));
                            updateBorder(row, col, true);

                        } else if (secondX == -1 && secondY == -1) {
                            secondX = row;
                            secondY = col;

                            bufferedImage = AssetManager.getBufferedSprite(String.format("Minigame\\MemoryCards\\%s.png", cardItems[cardContents[secondX][secondY] - 1]));
                            updateBorder(row, col, true);

                            if (cardContents[firstX][firstY] == cardContents[secondX][secondY]) {
                                cards[firstX][firstY].setBackground(Color.GREEN);
                                cards[secondX][secondY].setBackground(Color.GREEN);

                                discovered[firstX][firstY] = true;
                                discovered[secondX][secondY] = true;

                                firstX = -1;
                                firstY = -1;
                                secondX = -1;
                                secondY = -1;
                            } else {
                                delay = 0;
                                clicked = true;
                            }
                        }

                        if (bufferedImage == null) {
                            return;
                        }

                        int horizontalMargin = insets.left + insets.right + 40;
                        int verticalMargin = insets.top + insets.bottom + 40;
                        Image resizedImage = JSwingUtilities.resizeImageAspectLockedWithMinDimensions(bufferedImage, label.getWidth() - horizontalMargin, label.getHeight() - verticalMargin);
                        cards[row][col].setIcon(new ImageIcon(resizedImage));
                    }
                });

//                panel.add(label);
                cards[i][j] = label;
                panel.add(label);
            }
        }
    }

    private void randomize() {
        Random rand = new Random();
        cardContents = new int[cardDeckWidth][cardDeckHeight];
        discovered = new boolean[cardDeckWidth][cardDeckHeight];

        for (int i = 0;i < cardItems.length;i++) {
            int x, y;

            do {
                x = rand.nextInt(0, cardDeckWidth);
                y = rand.nextInt(0, cardDeckHeight);
            } while (cardContents[x][y] != 0);

            cardContents[x][y] = i + 1;

            do {
                x = rand.nextInt(0, cardDeckWidth);
                y = rand.nextInt(0, cardDeckHeight);
            } while (cardContents[x][y] != 0);

            cardContents[x][y] = i + 1;
        }
    }

    @Override
    public void update(float delta) {

        delay += delta;

        if (delay > clickDelay && clicked) {
            help();
        }
    }

    private String getCardName(int row, int col) {
        return cardItems[cardContents[row][col] - 1];
    }

    private void updateBorder(int row, int col, boolean show) {
        if (show) {
            cards[row][col].setBorder(new CompoundBorder(new TitledBorder(new LineBorder(Color.BLACK, 3), getCardName(row, col), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.PLAIN, 16)), new EmptyBorder(10, 10, 10, 10)));
        } else {
            cards[row][col].setBorder(new CompoundBorder(BorderFactory.createLineBorder(Color.BLACK), new EmptyBorder(10, 10, 10, 10)));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0;i < 4;i++) {
            g.drawLine(0, i * getHeight() / 4, getWidth(), i * getHeight() / 4);
        }

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
    public void showMinigame() {

    }

    @Override
    public void hideMinigame() {

    }

    public void help() {
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
    public void resetMinigame() {
        randomize();

        help();

        for (int i = 0;i < cards.length;i++) {
            for (int j = 0;j < cards[i].length;j++) {
                cards[i][j].setBackground(Color.WHITE);
                cards[i][j].setIcon(null);
                updateBorder(i, j, false);
            }
        }
    }
}
