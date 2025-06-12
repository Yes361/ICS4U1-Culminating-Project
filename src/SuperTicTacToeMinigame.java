import Components.Minigame;

import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        gamePanel.setBounds(0, 0, 300, 300);
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
        JPanel cellPanel = new JPanel();
        cellPanel.setLayout(gridLayout);
        cellPanel.setBounds(x * 100, y * 100, 100, 100);

        cellPanel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(10, 10, 10, 10)));
        panels[x][y] = cellPanel;

        for (int i = 0;i < 3;i++) {
            for (int j = 0;j < 3;j++) {
                JButton button = new JButton();
//                button.setMinimumSize(new Dimension(10, 10));

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
                            button.setText("O");
                        } else {
                            button.setText("X");
                        }

                        if (isWin(state[x][y], currentTurn)) {
                            if (currentTurn == 1) {
                                finalStates[x][y] = finalState.P1_WIN;
                            } else {
                                finalStates[x][y] = finalState.P2_WIN;
                            }
                        } else if (isDraw(null, state[x][y])) {
                            finalStates[x][y] = finalState.DRAW;
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

                            setPanelBorderColor(gamePanel, Color.RED);

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

                            setPanelBorderColor(panels[row][col], Color.RED);
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
        component.setBorder(new CompoundBorder(new LineBorder(color), border.getInsideBorder()));
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
            if (matrix[i][i] != current) {
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
