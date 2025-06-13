import Components.Minigame;
import Core.GameSystem.AssetManager;
import Utility.Console;
import Utility.JSwingUtilities;

import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.Arrays;

public class SuperTicTacToeMinigame extends Minigame {
    private Integer[][][][] state;
    private JButton[][][][] cells;
    private finalState[][] finalStates;
    private JPanel[][] panels;
    private JPanel gamePanel;
    private int currentTurn;
    private int regionSelectorRow;
    private int regionSelectorCol;
    private JLabel messageLabel;

    private enum finalState {
        P1_WIN,
        DRAW,
        P2_WIN,
    }

    public SuperTicTacToeMinigame() {
        setBounds(0, 0, 900, 600);

        createMinigame();
        resetMinigame();
    }

    public void createMinigame() {
        createBoard();

        messageLabel = new JLabel();
        add(messageLabel);
    }

    public void createBoard() {
        GridLayout gridLayout = new GridLayout(3, 3);
        gamePanel = new JPanel(gridLayout);
        gamePanel.setLayout(gridLayout);

        Dimension dimension = new Dimension(500, 500);
        gamePanel.setPreferredSize(dimension);
        gamePanel.setMinimumSize(dimension);
        gamePanel.setMaximumSize(dimension);
        gamePanel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(10, 10, 10, 10)));

        state = new Integer[3][3][3][3];
        cells = new JButton[3][3][3][3];

        finalStates = new finalState[3][3];
        panels = new JPanel[3][3];

        for (int i = 0;i < 3;i++) {
            for (int j = 0;j < 3;j++) {
                gamePanel.add(createCellPanel(i, j));
            }
        }

        add(gamePanel);
    }

    public JPanel createCellPanel(int x, int y) {
        GridLayout gridLayout = new GridLayout(3, 3);
        JPanel cellPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (finalStates[x][y] != null) {
                    Color color = g.getColor();

                    switch (finalStates[x][y]) {
                        case DRAW -> {
                            g.drawLine(0, 0, this.getWidth(), this.getHeight());
                        }
                        case P1_WIN -> {
                            g.setColor(Color.BLUE);
                            g.drawOval(0, 0, this.getWidth(), this.getHeight());
                        }
                        case P2_WIN -> {
                            g.setColor(Color.RED);
                            g.drawLine(0, 0, this.getWidth(), this.getHeight());
                            g.drawLine(this.getWidth(), 0, 0, this.getHeight());
                        }
                    }

                    g.setColor(color);
                }
            }
        };
        cellPanel.setLayout(gridLayout);

        cellPanel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 2), new EmptyBorder(10, 10, 10, 10)));
        panels[x][y] = cellPanel;

        for (int i = 0;i < 3;i++) {
            for (int j = 0;j < 3;j++) {
                JButton button = new JButton();
                JSwingUtilities.resizeFont(button, 12);

                button.setFocusPainted(false);
                button.setBackground(Color.WHITE);

                int row = i;
                int col = j;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (state[x][y][row][col] != null) {
                            return;
                        }

                        if (regionSelectorRow != -1 && !(regionSelectorRow == x && regionSelectorCol == y)) {
                            return;
                        }

                        if (currentTurn == 1) {
                            button.setForeground(Color.BLUE);
                            button.setText("O");
                        } else {
                            button.setForeground(Color.RED);
                            button.setText("X");
                        }

                        state[x][y][row][col] = currentTurn;

                        if (isWin(state[x][y], currentTurn)) {
                            if (currentTurn == 1) {
                                finalStates[x][y] = finalState.P1_WIN;
                            } else {
                                finalStates[x][y] = finalState.P2_WIN;
                            }

                            repaint();
                        } else if (isDraw(null, state[x][y])) {
                            finalStates[x][y] = finalState.DRAW;

                            repaint();
                        }

                        if (isWin(finalStates, finalState.P1_WIN)) {
                            messageLabel.setText("Player 1 Won!");
                        } else if (isWin(finalStates, finalState.P2_WIN)) {
                            messageLabel.setText("Player 2 Won!");
                        } else if (isDraw(null, finalStates)) {
                            messageLabel.setText("Draw!");
                        }

                        if (finalStates[row][col] != null) {
                            regionSelectorRow = -1;
                            regionSelectorCol = -1;

                            if (currentTurn == 1) {
                                setPanelBorderColor(gamePanel, Color.RED);
                            } else {
                                setPanelBorderColor(gamePanel, Color.BLUE);
                            }

                            for (Component component : gamePanel.getComponents()) {
                                if (component instanceof JPanel panel) {
                                    setPanelBorderColor(panel, Color.BLACK);
                                }
                            }

                        } else {
                            regionSelectorRow = row;
                            regionSelectorCol = col;

                            setPanelBorderColor(gamePanel, Color.BLACK);

                            for (Component component : gamePanel.getComponents()) {
                                if (component instanceof JPanel panel) {
                                    setPanelBorderColor(panel, Color.BLACK);
                                }
                            }

                            if (currentTurn == 1) {
                                setPanelBorderColor(panels[row][col], Color.RED);
                            } else {
                                setPanelBorderColor(panels[row][col], Color.BLUE);
                            }
                        }

                        nextTurn();
                    }
                });

                cells[x][y][i][j] = button;
                cellPanel.add(button);
            }
        }

        return cellPanel;
    }

    private void setPanelBorderColor(JComponent component, Color color) {
        CompoundBorder border = (CompoundBorder) component.getBorder();
        component.setBorder(new CompoundBorder(new LineBorder(color, 2), border.getInsideBorder()));
    }

    public void nextTurn() {
        currentTurn = currentTurn == 1 ? 2 : 1;
    }

    private <T> boolean isDraw(T defaultValue, T[][] matrix) {
        for (int i = 0;i < matrix.length;i++) {
            for (int j = 0;j < matrix[i].length;j++) {
                if (matrix[i][j] == defaultValue) {
                    return false;
                }
            }
        }

        return true;
    }

    private <T> boolean isRow(int x, T[][] matrix, T current) {
        for (T[] ts : matrix) {
            if (ts[x] != current) {
                return false;
            }
        }
        return true;
    }

    private <T> boolean isCol(int y, T[][] matrix, T current) {
        for (int i = 0;i < matrix[y].length;i++) {
            if (matrix[y][i] != current) {
                return false;
            }
        }
        return true;
    }

    private <T> boolean isLeftDiagonal(T[][] matrix, T current) {
        for (int i = 0;i < matrix.length;i++) {
            if (matrix[i][i] != current) {
                return false;
            }
        }
        return true;
    }

    private <T> boolean isRightDiagonal(T[][] matrix, T current) {
        for (int i = 0;i < matrix.length;i++) {
            if (matrix[i][matrix.length - i - 1] != current) {
                return false;
            }
        }
        return true;
    }

    private <T> boolean isWin(T[][] matrix, T current) {
        for (int i = 0;i < matrix.length;i++) {
            if (isCol(i, matrix, current)) {
                return true;
            }
        }

        for (int i = 0;i < matrix.length;i++) {
            if (isRow(i, matrix, current)) {
                return true;
            }
        }

        return isLeftDiagonal(matrix, current) || isRightDiagonal(matrix, current);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Image img = JSwingUtilities.resizeImageAspectLockedWithMinDimensions(AssetManager.getBufferedSprite("Minigame\\Backgrounds\\BGSuperTicTacToe.jpeg"), getWidth(), getHeight());
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public BufferedImage getMinigameIcon() {
        return AssetManager.getBufferedSprite("Minigame\\Thumbnails\\SuperTicTacToe.jfif");
    }

    @Override
    public String getMinigameName() {
        return "Super TicTacToe";
    }

    @Override
    public void showMinigame() {

    }

    @Override
    public void hideMinigame() {

    }

    @Override
    public void resetMinigame() {
        currentTurn = 1;

        regionSelectorRow = -1;
        regionSelectorCol = -1;
    }
}
