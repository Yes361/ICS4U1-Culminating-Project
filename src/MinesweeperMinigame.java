import Components.Minigame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class MinesweeperMinigame extends Minigame {
    private int width = 10;
    private int height = 10;
    private JPanel gamePanel;
    private JButton[][] cells;
    private boolean[][] bombs;
    private int[][] neighbors;
    private boolean[][] discovered;

    public MinesweeperMinigame() {
        setBounds(0, 0, 900, 600);
        createMinigame();
    }

    public void createMinigame() {
        GridLayout gridLayout = new GridLayout(width, height);
        gridLayout.setHgap(1);
        gridLayout.setVgap(1);

        gamePanel = new JPanel();
        gamePanel.setBounds(100, 100, 400, 400);
        gamePanel.setLayout(gridLayout);

        cells = new JButton[width][height];
        discovered = new boolean[width][height];
        randomize();

        for (int i = 0;i < width;i++) {
            for (int j = 0;j < height;j++) {
                JButton button = new JButton();

                int row = i;
                int col = j;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (bombs[row][col]) {
                            button.setText("BOMB!");
                            discovered[row][col] = true;
                        } else {
                            floodfill(row, col);
                        }

                    }
                });

                cells[i][j] = button;
                gamePanel.add(button);
            }
        }

        add(gamePanel);
    }

    public void floodfill(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return;
        }

        if (discovered[x][y]) {
            return;
        }

        discovered[x][y] = true;

        if (neighbors[x][y] == 0) {
            cells[x][y].setBackground(Color.RED);
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
        int bombCount = 10;

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
    public void showMinigame() {

    }

    @Override
    public void hideMinigame() {

    }

    @Override
    public void resetMinigame() {


    }
}
