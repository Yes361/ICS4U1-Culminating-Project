import Components.Minigame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeMinigame extends Minigame {
    private JPanel gamePanel;
    private JButton[][] cells;
    private JLabel messageLabel;
    private int[][] state;
    private boolean currentTurn;
    private int length = 3;

    public TicTacToeMinigame() {
        setBounds(0, 0, 900, 600);

        createMinigame();
        resetMinigame();
    }

    public void createMinigame() {
        createBoard();

        messageLabel = new JLabel();
        add(messageLabel);

        JButton resetButton = new JButton("Reset!");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetMinigame();
            }
        });

        add(resetButton);
    }

    public void createBoard() {
        state = new int[length][length];

        GridLayout gridLayout = new GridLayout(length, length);
        gamePanel = new JPanel();
        gamePanel.setMinimumSize(new Dimension(300, 300));
        gamePanel.setBounds(200, 200, 300, 300);
        gamePanel.setLayout(gridLayout);

        cells = new JButton[length][length];

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                JButton button = new JButton();

                int row = i;
                int col = j;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (state[row][col] != 0 && !isWin()) {
                            return;
                        }

                        if (currentTurn) {
                            button.setText("O");
                            state[row][col] = 2;
//                          button.setIcon();
                        } else {
                            button.setText("X");
                            state[row][col] = 1;
//                          button.setIcon();
                        }

                        if (isWin()) {
                            messageLabel.setText(String.format("Player %d Wins!", getCurrentTurn()));
                        } else if (isDraw()) {
                            messageLabel.setText("Draw !");
                        } else {
                            currentTurn = !currentTurn;
                        }

                    }
                });

                gamePanel.add(button);

                cells[i][j] = button;
            }
        }

        add(gamePanel);
    }

    private int getCurrentTurn() {
        return currentTurn ? 2 : 1;
    }

    private boolean rowWin(int x) {
        for (int i = 0; i < length; i++) {
            if (state[x][i] != getCurrentTurn()) {
                return false;
            }
        }

        return true;
    }

    private boolean colWin(int y) {
        for (int i = 0; i < length; i++) {
            if (state[i][y] != getCurrentTurn()) {
                return false;
            }
        }

        return true;
    }

    private boolean leftDiagonalWin() {
        for (int i = 0; i < length; i++) {
            if (state[i][i] != getCurrentTurn()) {
                return false;
            }
        }

        return true;
    }

    private boolean rightDiagonalWin() {
        for (int i = 0; i < length; i++) {
            if (state[i][length - i - 1] != getCurrentTurn()) {
                return false;
            }
        }

        return true;
    }

    private boolean isWin() {
        for (int i = 0; i < length; i++) {
            if (rowWin(i)) {
                return true;
            }
        }

        for (int i = 0; i < length; i++) {
            if (colWin(i)) {
                return true;
            }
        }

        return leftDiagonalWin() || rightDiagonalWin();
    }

    private boolean isDraw() {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
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
        state = new int[length][length];
        currentTurn = false;

        for (int i = 0;i < length;i++) {
            for (int j = 0;j < length;j++) {
                cells[i][j].setText("");
            }
        }

        messageLabel.setText("");
    }
}
