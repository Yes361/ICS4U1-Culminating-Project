package Components;

import Core.GameSystem.AssetManager;
import Core.GameSystem.JGameObject;
import Core.GameSystem.JGameObjectInterface;
import Core.GameSystem.JLabelExtended;
import Utility.Console;
import Utility.RandomUtilities;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Random;

public class MemoryCardMinigame extends Minigame implements JGameObjectInterface, MouseListener {
    JPanel panel;
    private int firstX = -1;
    private int firstY = -1;
    private int secondX = -1;
    private int secondY = -1;

    private boolean[][] discovered;
    private JLabelExtended[][] cards;
    private float delay = 10;
    private boolean clicked = false;
    private float clickDelay = 1000;

    public MemoryCardMinigame() {
        setBounds(0, 0,900, 600);
        setOpaque(true);
        setBackground(new Color(75, 47, 31));

        createMemoryCards();

        repaint();
    }

    public void createMemoryCards() {
        panel = new JPanel();
        panel.setBounds(100, 100, 700, 400);

        int cardDeckWidth = 4;
        int cardDeckHeight = 5;

        GridLayout gridLayout = new GridLayout(cardDeckWidth, cardDeckHeight);
        gridLayout.setHgap(5);
        gridLayout.setVgap(5);

        panel.setLayout(gridLayout);
        String[] cardItems = {
            "Isomer",
            "Catalyst",
            "Electronegativity",
            "Precipitate",
            "Covalent",
            "Alkene",
            "Titration",
            "Orbital",
            "Stoichiometry",
            "Radical"
        };

        int[][] cardContents = new int[cardDeckWidth][cardDeckHeight];
        discovered = new boolean[cardDeckWidth][cardDeckHeight];
        cards = new JLabelExtended[cardDeckWidth][cardDeckHeight];

        Random rand = new Random();

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


        add(panel);

        for (int i = 0;i < cardDeckWidth;i++) {
            for (int j = 0;j < cardDeckHeight;j++) {
                JLabelExtended label = new JLabelExtended("");
                label.setOpaque(true);
                label.setBorder(new CompoundBorder(BorderFactory.createLineBorder(Color.BLACK), new EmptyBorder(10, 10, 10, 10)));

                int row = i;
                int col = j;

                label.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (firstX == -1 && firstY == -1) {
                            firstX = row;
                            firstY = col;

                            cards[row][col].setText(cardItems[cardContents[firstX][firstY] - 1]);
                        } else if (secondX == -1 && secondY == -1) {
                            secondX = row;
                            secondY = col;

                            cards[row][col].setText(cardItems[cardContents[secondX][secondY] - 1]);

                            if (cardContents[firstX][firstY] == cardContents[secondX][secondY]) {
                                cards[firstX][firstY].setBackground(Color.GREEN);
                                cards[secondX][secondY].setBackground(Color.GREEN);

                                firstX = -1;
                                firstY = -1;
                                secondX = -1;
                                secondY = -1;
                            } else {
                                delay = 0;
                                clicked = true;
                            }
                        }


                    }

                    @Override
                    public void mousePressed(MouseEvent e) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }
                });

//                panel.add(label);
                cards[i][j] = label;
                panel.add(label);
            }
        }
    }

    @Override
    public void update(float delta) {

        delay += delta;

        if (delay > clickDelay && clicked) {
            cards[firstX][firstY].setText("");
            cards[secondX][secondY].setText("");

            firstX = -1;
            firstY = -1;
            secondX = -1;
            secondY = -1;

            clicked = false;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0;i < 4;i++) {
            g.drawLine(0, i * getHeight() / 4, getWidth(), i * getHeight() / 4);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public BufferedImage getMinigameIcon() {
        return null;
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

    @Override
    public void resetMinigame() {

    }
}
