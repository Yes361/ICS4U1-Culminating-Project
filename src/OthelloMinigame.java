import Components.Minigame;
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
    private int gridRow = 5;
    private int gridCol = 5;
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
        gamePanel.setBounds(100, 100, 300, 300);

        for (int i = 0;i < gridRow;i++) {
            for (int j = 0;j < gridCol;j++) {
                JButton button = new JButton();
                button.setFocusPainted(false);

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

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
        if (currentTurn == 1) {
            cells[x][y].setBackground(Color.BLACK);
        } else {
            cells[x][y].setBackground(Color.RED);
        }
    }

    private void nextTurn() {
        currentTurn = currentTurn == 1 ? 2 : 1;
    }

    @Override
    public BufferedImage getMinigameIcon() {
        return null;
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
