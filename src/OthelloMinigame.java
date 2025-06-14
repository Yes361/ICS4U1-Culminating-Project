import Components.Minigame;
import Core.GameSystem.AssetManager;
import Utility.Console;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class OthelloMinigame extends Minigame {
    // The game board represented by a grid of integer IDs
    // 0 = empty, 1 = black, 2 = red
    private int[][] grid;

    // The array of cell buttons for UI representation
    private JButton[][] cells;

    // The main game panel
    private JPanel gamePanel;

    // Number of rows and columns in the Othello grid
    private int gridRow = 25;
    private int gridCol = 25;

    // Currently active player (1 = black, 2 = red)
    private int currentTurn = 1;

    // Initializes the Othello game
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

        Dimension dimension = new Dimension(500, 500);
        gamePanel.setPreferredSize(dimension);
        gamePanel.setMaximumSize(dimension);
        gamePanel.setMinimumSize(dimension);

        // Loop to create each cell in the grid
        for (int i = 0;i < gridRow;i++) {
            for (int j = 0;j < gridCol;j++) {
                JButton button = new JButton();
                button.setFocusPainted(false);
                button.setBorder(new LineBorder(Color.BLACK));
                button.setBackground(Color.WHITE);

                int row = i;
                int col = j;

                // Action when a cell is clicked by the player
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        boolean successfulMove = false;

                        // Can't move if cell is already occupied
                        if (grid[row][col] != 0) {
                            return;
                        }

                        // Check in all directions for flipped discs
                        for (int dx = -1;dx <= 1;dx++) {
                            for (int dy = -1;dy <= 1;dy++) {
                                if (dx == 0 && dy == 0) {
                                    continue;
                                }

                                if (isConnectedAndMark(row + dx, col + dy, dx, dy, false, true)) {
                                    setCellIcon(row, col, currentTurn);
                                    successfulMove = true;
                                }
                            }
                        }

                        if (successfulMove) {
                            nextTurn();

                            // Highlight the cells where moves are legal
                            revealPlayableSquares();
                        }
                    }
                });

                cells[i][j] = button;
                gamePanel.add(button);
            }
        }

        add(gamePanel);

        JButton resetButton = new JButton("RESET");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetMinigame();
            }
        });

        add(resetButton);

        JButton rulesButton = new JButton("Rules");
        rulesButton.setAlignmentX(CENTER_ALIGNMENT);
        rulesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MinigameRuleFrame.showPopup("""
                Othello
                
                Rules:
                You and the computer take turns placing discs to flank and flip their opponent's pieces. Moves must capture at least one opposing disc in a straight line. If someone can't move, they pass. The game ends when no moves are left, and the player with the most discs wins. Goodliuck!
                """);
            }
        });

        add(rulesButton);
    }

    private void revealPlayableSquares() {
        // iterate over the whole board to check
        for (int i = 0;i < gridRow;i++) {
            for (int j = 0; j < gridCol; j++) {
                cells[i][j].setBorder(new LineBorder(Color.BLACK));

                if (grid[i][j] != 0) {
                    continue;
                }

                // determine if there is a playable move in all directions
                outerLoop: for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        if (dx == 0 && dy == 0) {
                            continue;
                        }

                        if (isConnectedAndMark(i + dx, j + dy, dx, dy, false, false)) {
                            cells[i][j].setBorder(new LineBorder(Color.GREEN));
                            break outerLoop;
                        }
                    }
                }
            }
        }
    }

    // Set the cell icon based on the currenturn
    private void setCellIcon(int x, int y, int currentTurn) {
        cells[x][y].setBackground(currentTurn == 1 ? Color.BLACK : Color.RED);
        grid[x][y] = currentTurn;
    }

    // determines if the is a connecting disc of the same color, and if there is
    // mark it if the isMark flagged is true
    // returns a boolean indicating whether or not this move is possible given the
    // x, y, dx, dy configuration
    private boolean isConnectedAndMark(int x, int y, int dx, int dy, boolean otherEncountered, boolean isMarked) {
        if (x < 0 || x >= gridRow || y < 0 || y >= gridCol) {
            return false;
        }

        if (grid[x][y] == 0) {
            return false;
        }

        if (grid[x][y] == currentTurn) {
            if (otherEncountered) {
                if (isMarked) {
                    setCellIcon(x, y, currentTurn);
                }
                return true;
            }
        } else {
            boolean result = isConnectedAndMark(x + dx, y + dy, dx, dy, true, isMarked);

            if (result && isMarked) {
                setCellIcon(x, y, currentTurn);
            }

            return result;
        }

        return false;
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
        grid = new int[gridRow][gridCol];

        for (int i = 0;i < gridRow;i++) {
            for (int j = 0;j < gridCol;j++) {
                cells[i][j].setBackground(Color.WHITE);
            }
        }

        setCellIcon(gridRow / 2, gridCol / 2 + 1, 1);
        setCellIcon(gridRow / 2 + 1, gridCol / 2, 1);
        setCellIcon(gridRow / 2, gridCol / 2, 2);
        setCellIcon(gridRow / 2 + 1, gridCol / 2 + 1, 2);

        revealPlayableSquares();

    }
}
