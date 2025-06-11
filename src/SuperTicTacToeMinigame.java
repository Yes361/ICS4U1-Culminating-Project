import Components.Minigame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SuperTicTacToeMinigame extends Minigame {
    private int[][][][] state;
    private JButton[][][][] cells;
    private int currentTurn;

    public SuperTicTacToeMinigame() {
        setBounds(0, 0, 900, 600);

        createMinigame();
        resetMinigame();
    }

    public void createMinigame() {
        GridLayout gridLayout = new GridLayout(3, 3);
        JPanel gamePanel = new JPanel(gridLayout);
        gamePanel.setLayout(gridLayout);
        gamePanel.setBounds(0, 0, 300, 300);

        state = new int[3][3][3][3];

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

        for (int i = 0;i < 3;i++) {
            for (int j = 0;j < 3;j++) {
                JButton button = new JButton();

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (currentTurn == 1) {
                            button.setText("O");

                        } else {
                            button.setText("X");

                        }
                    }
                });

                cells[x][y][i][j] = button;
                cellPanel.add(button);
            }
        }

        return cellPanel;
    }

    public void nextTurn() {
        currentTurn = currentTurn == 1 ? 2 : 1;
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
