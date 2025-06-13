import Components.Minigame;
import Core.GameSystem.AssetManager;
import Core.GameSystem.JGameObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class OthelloMinigame extends Minigame {
    private int[][] grid;
    private JButton[][] cells;
    private JPanel gamePanel;
    private int gridRow = 25;
    private int gridCol = 25;
    private int currentTurn = 1;

    public OthelloMinigame() {
        setBounds(0, 0, 900, 600);

        createMinigame();
    }

    public void createMinigame() {
        grid = new int[gridRow][gridCol];
        cells = new JButton[gridRow][gridCol];

        GridLayout gridLayout = new GridLayout(gridRow, gridCol);
        gamePanel = new JPanel();
        gamePanel.setLayout(gridLayout);
        Dimension dimension = new Dimension(300, 300);
        gamePanel.setPreferredSize(dimension);
        gamePanel.setMaximumSize(dimension);
        gamePanel.setMinimumSize(dimension);

        for (int i = 0;i < gridRow;i++) {
            for (int j = 0;j < gridCol;j++) {
                JButton button = new JButton();
                button.setFocusPainted(false);

                int row = i;
                int col = j;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        for (int dx = -1;dx <= 1;dx++) {
                            for (int dy = -1;dy <= 1;dy++) {
                                if (dx == 0 && dy == 0) {
                                    continue;
                                }

                                if (isSigma(row, col, dx, dy, true)) {
                                    nextTurn();
                                }
                            }
                        }
                    }
                });

                cells[i][j] = button;
                gamePanel.add(button);
            }
        }

        setCellIcon(2, 2, 1);
        setCellIcon(3, 3, 1);
        setCellIcon(2, 3, 2);
        setCellIcon(3, 2, 2);

        add(gamePanel);
    }

    private void setCellIcon(int x, int y, int currentTurn) {
        cells[x][y].setBackground(currentTurn == 1 ? Color.BLACK : Color.RED);
        grid[x][y] = currentTurn;
    }

    private boolean isSigma(int x, int y, int dx, int dy, boolean start) {
        if (x < 0 || x >= gridRow || y < 0 || y >= gridCol) {
            return false;
        }

        if (grid[x][y] == 0 && !start) {
            return false;
        }

        if (grid[x][y] == currentTurn && !start) {
            setCellIcon(x, y, currentTurn);
            return true;
        }

        boolean result = isSigma(x + dx, y + dy, dx, dy, false);

        if (result) {
            setCellIcon(x, y, currentTurn);
        }

        return result;
    }

    private void nextTurn() {
        currentTurn = currentTurn == 1 ? 2 : 1;
    }

    @Override
    public BufferedImage getMinigameIcon() {
        return AssetManager.getBufferedSprite("Minigame\\Thumbnails\\Othello.jfif");
    }

    @Override
    public String getMinigameName() {
        return "Othello";
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
    }
}
