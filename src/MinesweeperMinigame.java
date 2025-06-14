import Components.Minigame;
import Core.GameSystem.AssetManager;
import Utility.Console;
import Utility.JSwingUtilities;
import Utility.MathUtilities;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class MinesweeperMinigame extends Minigame {
    private int width = 15;
    private int height = 15;

    // matrix variables
    private boolean[][] bombs;
    private int[][] neighbors;
    private boolean[][] discovered;
    private boolean[][] flagged;

    // UI variables
    private JPanel gamePanel;
    private JButton[][] cells;
    private JLabel messageLabel;

    // state variables
    private boolean lost = false;
    private boolean won = false;
    private boolean flagSelectorMode = false;
    private int bombCount = 10;

    public MinesweeperMinigame() {
        setBounds(0, 0, 900, 600);
        createMinigame();
    }

    public void createMinigame() {
        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        setLayout(boxLayout);

        JLabel titleLabel = new JLabel("Minesweeper");
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        JSwingUtilities.resizeFont(titleLabel, 24);
        titleLabel.setForeground(Color.WHITE);

        add(titleLabel);

        createBoard();

        JPanel report = new JPanel();
        report.setOpaque(false);

        messageLabel = new JLabel();
        report.add(messageLabel);

        JPanel container = new JPanel();
        container.setOpaque(false);
        container.setLayout(new FlowLayout());

        int maxBombs = 30;
        JSlider slider = new JSlider(10, maxBombs);
        JTextField textField = new JTextField();
        JLabel currentValue = new JLabel();
        textField.setMinimumSize(new Dimension(100, 50));

        container.add(currentValue);

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                bombCount = slider.getValue();
                resetMinigame();

                currentValue.setText(String.valueOf(bombCount));
                textField.setText(String.valueOf(bombCount));
            }
        });

        container.add(slider);

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);

                String currentText = textField.getText() + e.getKeyChar();

                if (!Character.isDigit(e.getKeyChar())) {
                    e.consume();
                } else {
                    int value = Integer.parseInt(currentText);

                    if (value > maxBombs || value < 0) {
                        value = MathUtilities.constrain(0, maxBombs, value);

                        textField.setText(String.valueOf(value));
                    }

                    slider.setValue(value);
                    currentValue.setText(currentText);
                }
            }
        });

        container.add(textField);

        JButton button = new JButton("Reset");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetMinigame();
            }
        });


        // Displays the rules
        JButton rulesButton = new JButton("Rules");
        rulesButton.setAlignmentX(CENTER_ALIGNMENT);
        rulesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MinigameRuleFrame.showPopup("""
                Minesweeper
                
                Rules:
                Click on a square, and itll clear out an area. Squares that are cleared with numbers on them indicate how many bomb squares are surrounding them in a 3x3 area, your goal is to clear out all the squares and avoid all the bombs. Goodluck!
                
                """);
            }
        });
        report.add(rulesButton);
        report.add(container);
        report.add(button);
        add(report);
    }

    private void createBoard() {
        GridLayout gridLayout = new GridLayout(width, height);
        gridLayout.setHgap(1);
        gridLayout.setVgap(1);

        gamePanel = new JPanel();
        gamePanel.setLayout(gridLayout);

        Dimension dimension = new Dimension(width * 30, height * 30);
        gamePanel.setMaximumSize(dimension);
        gamePanel.setMinimumSize(dimension);
        gamePanel.setPreferredSize(dimension);

        cells = new JButton[width][height];

        for (int i = 0;i < width;i++) {
            for (int j = 0;j < height;j++) {
                JButton button = createButton(i, j);

                cells[i][j] = button;
                gamePanel.add(button);
            }
        }

        resetMinigame();

        add(gamePanel);
    }

    private JButton createButton(int row, int col) {
        JButton button = new JButton();
        button.setFocusPainted(false);
        button.setForeground(Color.BLACK);
        button.setBackground(Color.DARK_GRAY);
        button.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(10, 10, 10, 10)));
        button.setFocusPainted(false);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (flagSelectorMode) {
                    markFlag(row, col);
                } else {
                    if (flagged[row][col]) {
                        return;
                    }

                    if (lost || won) {
                        return;
                    }

                    if (bombs[row][col]) {
                        lost = true;
                        messageLabel.setText("You Lost!");

                        for (int i = 0;i < width;i++) {
                            for (int j = 0;j < height;j++) {
                                if (bombs[i][j]) {
                                    cells[i][j].setIcon(new ImageIcon(AssetManager.getBufferedSprite("minigame\\Minesweeper\\TNT.png")));
                                    discovered[row][col] = true;
                                }
                            }
                        }
                    } else {
                        floodfill(row, col);
                    }
                }
            }
        });

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                if (SwingUtilities.isRightMouseButton(e) && !discovered[row][col]) {
                    markFlag(row, col);
                }
            }
        });

        return button;
    }

    private boolean isBombsFlagged() {
        for (int i = 0;i < width;i++) {
            for (int j = 0;j < height;j++) {
                if (bombs[i][j] && !flagged[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isWin() {
        if (isBombsFlagged()) {
            return true;
        }

        return false;
    }

    public void markFlag(int row, int col) {
        flagged[row][col] = !flagged[row][col];

        if (flagged[row][col]) {
            cells[row][col].setIcon(new ImageIcon(AssetManager.getBufferedSprite("minigame\\Minesweeper\\Flag.png")));
        } else {
            cells[row][col].setIcon(null);
        }

        if (isWin()) {
            won = true;
            messageLabel.setText("You Won!");
        }
    }

    public void floodfill(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return;
        }

        if (discovered[x][y]) {
            return;
        }

        discovered[x][y] = true;
        cells[x][y].setBackground(Color.LIGHT_GRAY);

        if (neighbors[x][y] == 0) {
            floodfill(x - 1, y);
            floodfill(x + 1, y);
            floodfill(x, y - 1);
            floodfill(x, y + 1);
        } else {
            cells[x][y].setText(String.valueOf(neighbors[x][y]));
        }
    }

    public void randomize() {
        bombs = new boolean[width][height];

        Random random = new Random();

        for (int i = 0;i < bombCount;i++) {
            int x;
            int y;

            do {
                x = random.nextInt(0, width - 1);
                y = random.nextInt(0, height - 1);
            } while (bombs[x][y]);

            bombs[x][y] = true;
        }

        neighbors = new int[width][height];

        for (int i = 0;i < width;i++) {
            for (int j = 0;j < height;j++) {
                if (j > 0) {
                    neighbors[i][j] += bombs[i][j - 1] ? 1 : 0;
                }
                if (j < height - 1) {
                    neighbors[i][j] += bombs[i][j + 1] ? 1 : 0;
                }
                if (i > 0) {
                    neighbors[i][j] += bombs[i - 1][j] ? 1 : 0;

                    if (j > 0) {
                        neighbors[i][j] += bombs[i - 1][j - 1] ? 1 : 0;
                    }
                    if (j < height - 1) {
                        neighbors[i][j] += bombs[i - 1][j + 1] ? 1 : 0;
                    }
                }
                if (i < width - 1) {
                    neighbors[i][j] += bombs[i + 1][j] ? 1 : 0;

                    if (j > 0) {
                        neighbors[i][j] += bombs[i + 1][j - 1] ? 1 : 0;
                    }
                    if (j < height - 1) {
                        neighbors[i][j] += bombs[i + 1][j + 1] ? 1 : 0;
                    }
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Image img = JSwingUtilities.resizeImageAspectLockedWithMinDimensions(AssetManager.getBufferedSprite("Minigame\\Backgrounds\\BGMinesweeper.png"), getWidth(), getHeight());
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public BufferedImage getMinigameIcon() {
        return AssetManager.getBufferedSprite("Minigame\\Thumbnails\\Minesweeper.jfif");
    }

    @Override
    public String getMinigameName() {
        return "Minesweeper";
    }

    @Override
    public void showMinigame() {

    }

    @Override
    public void hideMinigame() {

    }

    @Override
    public void resetMinigame() {
        for (int i = 0;i < width;i++) {
            for (int j = 0;j < height;j++) {
                cells[i][j].setBackground(Color.DARK_GRAY);
                cells[i][j].setIcon(null);
                cells[i][j].setText("");
            }
        }

        lost = false;
        won = false;

        flagSelectorMode = false;

        discovered = new boolean[width][height];
        flagged = new boolean[width][height];
        randomize();
    }
}
