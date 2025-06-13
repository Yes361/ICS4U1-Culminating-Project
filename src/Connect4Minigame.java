import Components.Minigame;
import Core.GameSystem.AssetManager;
import Utility.JSwingUtilities;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class Connect4Minigame extends Minigame {
    private int currentTurn = 1;
    private JPanel gamePanel;
    private JButton[][] cells;
    private JLabel messageLabel;
    private int[][] state;
    private int width = 10;
    private int height = 10;
    private int required = 4;
    private final Color DARK_BLUE = new Color(0, 0, 255);
    private final Color LIGHT_BLUE = new Color(0, 0, 230);

    public Connect4Minigame() {
        setBounds(0, 0, 900, 600);
        createMinigame();
    }

    public void createMinigame() {
        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        setLayout(boxLayout);

        JLabel titleLabel = new JLabel("Connect 4 Minigame!");
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        JSwingUtilities.resizeFont(titleLabel, 24);
        add(titleLabel);

        add(Box.createVerticalStrut(10));

        createBoard();
        add(Box.createVerticalStrut(10));

        Container container = new Container();
        container.setLayout(new FlowLayout());

        messageLabel = new JLabel();
        container.add(messageLabel);

        JButton resetButton = new JButton("Reset !");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetMinigame();
            }
        });

        container.add(resetButton);

        add(container);
    }

    public void createBoard() {
        GridLayout gridLayout = new GridLayout(width, height);
        gridLayout.setVgap(1);
        gridLayout.setHgap(1);

        gamePanel = new JPanel();
        gamePanel.setLayout(gridLayout);

        Dimension dimension = new Dimension(width * 50, height * 50);
        gamePanel.setPreferredSize(dimension);
        gamePanel.setMinimumSize(dimension);
        gamePanel.setMaximumSize(dimension);

        cells = new JButton[width][height];
        state = new int[width][height];

        for (int i = 0;i < width;i++) {
            for (int j = 0;j < height;j++) {
                JButton button = createButton(i, j);

                gamePanel.add(button);
                cells[i][j] = button;
            }
        }

        add(gamePanel);
    }

    private JButton createButton(int row, int col) {
        JButton button = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Insets insets = this.getInsets();
                int width = this.getWidth() - insets.left - insets.right;
                int height = this.getHeight() - insets.top - insets.bottom;

                Color color = g.getColor();
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, this.getWidth(), this.getHeight());

                g.setColor(Color.BLACK);
                g.drawOval(insets.left, insets.top, width, height);

                if (state[row][col] == 0) {
                    g.setColor(DARK_BLUE);
                } else if (state[row][col] == 1) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.GREEN);
                }

                g.fillOval(0, 0, this.getWidth(), this.getHeight());

                g.setColor(color);
            }
        };
        button.setSize(10, 10);
        button.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(10, 10, 10, 10)));

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (state[row][col] != 0 || isWin()) {
                    return;
                }

                int idx = row;
                while (++idx < state.length && state[idx][col] == 0);

                state[--idx][col] = currentTurn;
                repaint();

                if (isWin()) {
                    messageLabel.setText(String.format("Player %d wins!", currentTurn));
                } else if (isDraw()) {
                    messageLabel.setText("Draw!");
                } else {
                    nextTurn();
                }

            }
        });

        return button;
    }

    public void nextTurn() {
        currentTurn = currentTurn == 1 ? 2 : 1;

        messageLabel.setText(String.format("Player %d's Turn", currentTurn));
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
    public BufferedImage getMinigameIcon() {
        return AssetManager.getBufferedSprite("Minigame\\Thumbnails\\ConnectFour.jfif");
    }

    @Override
    public String getMinigameName() {
        return "Connect 4";
    }

    @Override
    public void showMinigame() {

    }

    @Override
    public void hideMinigame() {

    }

    @Override
    public void resetMinigame() {
        state = new int[width][height];
        repaint();

        messageLabel.setText("");
    }
}
