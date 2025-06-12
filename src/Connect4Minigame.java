import Components.Minigame;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Connect4Minigame extends Minigame {
    private int currentTurn = 1;
    private JPanel gamePanel;
    private JButton[][] cells;
    private JLabel messageLabel;
    private int[][] state;
    private int width = 10;
    private int height = 10;
    private int required = 4;

    public Connect4Minigame() {
        setBounds(0, 0, 900, 600);
        createMinigame();
    }

    public void createMinigame() {
        createBoard();

        messageLabel = new JLabel();
        add(messageLabel);
    }

    public void createBoard() {
        GridLayout gridLayout = new GridLayout(width, height);
        gridLayout.setVgap(10);
        gridLayout.setHgap(10);

        gamePanel = new JPanel();
        gamePanel.setBounds(200, 200, width * 20, height * 20);
        gamePanel.setLayout(gridLayout);

        if (cells != null) {
//            do stuff
        }

        cells = new JButton[width][height];
        state = new int[width][height];

        for (int i = 0;i < width;i++) {
            for (int j = 0;j < height;j++) {
                JButton button = new JButton();
                button.setSize(10, 10);
                button.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(10, 10, 10, 10)));

                int row = i;
                int col = j;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (state[row][col] != 0) {
                            return;
                        }

                        int idx = row;
                        while (++idx < state.length && state[idx][col] == 0);

                        idx--;

                        state[idx][col] = currentTurn;

                        if (currentTurn == 1) {
                            cells[idx][col].setText("O");
                        } else {
                            cells[idx][col].setText("X");
                        }

                        if (isWin()) {
                            messageLabel.setText(String.format("Player %d wins!", currentTurn));
                        } else if (isDraw()) {
                            messageLabel.setText("Draw!");
                        }

                        nextTurn();
                    }
                });

                gamePanel.add(button);
                cells[i][j] = button;
            }
        }

        add(gamePanel);
    }

    public void nextTurn() {
        currentTurn = currentTurn == 1 ? 2 : 1;
    }



    public boolean isRow(int x, int y) {
        for (int i = 0;i < required;i++) {
            if (state[x + i][y] != currentTurn) {
                return false;
            }
        }
        return true;
    }

    public boolean isCol(int x, int y) {
        for (int i = 0;i < required;i++) {
            if (state[x][y + i] != currentTurn) {
                return false;
            }
        }
        return true;
    }

    public boolean isLeftDiagonal(int x, int y) {
        for (int i = 0;i < required;i++) {
            if (state[x + i][y + i] != currentTurn) {
                return false;
            }
        }
        return true;
    }

    public boolean isRightDiagonal(int x, int y) {
        for (int i = 0;i < required;i++) {
            if (state[x - i][y + i] != currentTurn) {
                return false;
            }
        }
        return true;
    }

    public boolean isWin() {
        for (int i = 0;i <= width - required;i++) {
            for (int j = 0;j < height;j++) {
                if (isRow(i, j)) {
                    return true;
                }
            }
        }

        for (int i = 0;i < width;i++) {
            for (int j = 0;j <= height - required;j++) {
                if (isCol(i, j)) {
                    return true;
                }
            }
        }

        for (int i = 0;i <= width - required;i++) {
            for (int j = 0;j <= height - required;j++) {
                if (isLeftDiagonal(i, j)) {
                    return true;
                }
            }
        }

        for (int i = required - 1;i < width;i++) {
            for (int j = 0;j <= height - required;j++) {
                if (isRightDiagonal(i, j)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isDraw() {
        for (int i = 0;i < width;i++) {
            for (int j = 0;j < height;j++) {
                if (state[i][j] == 0) {
                    return false;
                }
            }
        }

        return true;
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
