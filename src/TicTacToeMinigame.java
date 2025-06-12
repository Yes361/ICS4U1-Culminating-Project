import Components.Minigame;
import Core.GameSystem.AssetManager;
import Utility.SwingUtilities;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

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
        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        setLayout(boxLayout);

        setMaximumSize(new Dimension(2 * getWidth() / 3, getHeight()));

        JLabel titleLabel = new JLabel("TicTacToe Minigame!");
        SwingUtilities.resizeFont(titleLabel, 24);

        add(titleLabel);

        add(Box.createVerticalStrut(10));

        createBoard();

        add(Box.createVerticalStrut(10));

        messageLabel = new JLabel();
        add(messageLabel);

        JButton resetButton = new JButton("Reset!");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetMinigame();
            }
        });

        add(Box.createVerticalStrut(10));

        add(resetButton);
    }

    public void createBoard() {
        state = new int[length][length];

//        GridBagLayout gridBagLayout = new GridBagLayout();
        GridLayout gridLayout = new GridLayout(length, length);
        gamePanel = new JPanel();
        gamePanel.setMinimumSize(new Dimension(300, 300));
        gamePanel.setBounds(200, 200, 300, 300);
        gamePanel.setLayout(gridLayout);

        cells = new JButton[length][length];

        int borderThickness = 30;

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                JButton button = new JButton();
                button.setSize(100, 100);
                button.setFocusPainted(false);
                button.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(borderThickness, borderThickness, borderThickness, borderThickness)));

                int row = i;
                int col = j;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (state[row][col] != 0 && !isWin()) {
                            return;
                        }

                        String imagePath;
                        if (currentTurn) {
                            state[row][col] = 2;
                            button.setIcon(new ImageIcon(AssetManager.getBufferedSpriteLocked("Minigame\\TicTacToe\\TicTacToeAnion.png", (int) button.getBounds().getWidth() - 2 * borderThickness)));
                        } else {
                            state[row][col] = 1;
                            button.setIcon(new ImageIcon(AssetManager.getBufferedSpriteLocked("Minigame\\TicTacToe\\TicTacToeCation.png", (int) button.getBounds().getWidth() - 2 * borderThickness)));
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
    public BufferedImage getMinigameIcon() {
//        return new ImageIcon(AssetManager.getBufferedSprite("Minigame\\Thumbnails\\"));
        return null;
    }

    @Override
    public String getMinigameName() {
        return "TicTacToe";
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
