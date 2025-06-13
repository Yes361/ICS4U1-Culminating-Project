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

public class TicTacToeMinigame extends Minigame {
    private JPanel gamePanel;
    private JButton[][] cells;
    private JLabel messageLabel;
    private int[][] state;
    private boolean currentTurn;
    private int length = 3;
    private final int borderThickness = 30;

    public TicTacToeMinigame() {
        setBounds(0, 0, 900, 600);

        createMinigame();
        resetMinigame();
    }

    public void createMinigame() {
        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        setLayout(boxLayout);

        JButton minigameScreenButton = new JButton("Go Back!");
        minigameScreenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.game.minigameScreen.SwitchToScreen();
            }
        });

        setMaximumSize(new Dimension(2 * getWidth() / 3, getHeight()));

        JLabel titleLabel = new JLabel("TicTacToe Minigame!");
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        JSwingUtilities.resizeFont(titleLabel, 24);

        add(titleLabel);
        add(Box.createVerticalStrut(10));

        createBoard();
        add(Box.createVerticalStrut(10));

        messageLabel = new JLabel();
        add(messageLabel);

        JButton resetButton = new JButton("Reset!");
        resetButton.setAlignmentX(CENTER_ALIGNMENT);

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
        cells = new JButton[length][length];

        gamePanel = new JPanel();

        Dimension preferredSize = new Dimension(300, 300);
        gamePanel.setPreferredSize(preferredSize);
        gamePanel.setMaximumSize(preferredSize);
        gamePanel.setMinimumSize(preferredSize);
        gamePanel.setSize(300, 300);

        GridLayout gridLayout = new GridLayout(length, length);
        gamePanel.setLayout(gridLayout);

        gamePanel.setAlignmentX(CENTER_ALIGNMENT);

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                JButton button = createButton(borderThickness, i, j);
                gamePanel.add(button);
                cells[i][j] = button;
            }
        }

        add(gamePanel);
    }

    private JButton createButton(int borderThickness, int i, int j) {
        JButton button = new JButton();
        button.setSize(100, 100);
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(borderThickness, borderThickness, borderThickness, borderThickness)));

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (state[i][j] != 0 || isWin()) {
                    return;
                }

                Image image;
                if (currentTurn) {
                    state[i][j] = 2;
                    image = AssetManager.getBufferedSpriteLocked("Minigame\\TicTacToe\\TicTacToeAnion.png", (int) button.getBounds().getWidth() - 2 * borderThickness);
                } else {
                    state[i][j] = 1;
                    image =  AssetManager.getBufferedSpriteLocked("Minigame\\TicTacToe\\TicTacToeCation.png", (int) button.getBounds().getWidth() - 2 * borderThickness);
                }
                button.setIcon(new ImageIcon(image));

                if (isWin()) {
                    messageLabel.setText(String.format("Player %d Wins!", getCurrentTurn()));
                    for (int i = 0;i < cells.length;i++) {
                        for (int j = 0;j < cells[i].length;j++) {
//                            cells[i][j].setEnabled(false);
                        }
                    }


                } else if (isDraw()) {
                    messageLabel.setText("Draw !");
                } else {
                    currentTurn = !currentTurn;
                }

            }
        });

        return button;
    }

    private int getCurrentTurn() {
        return currentTurn ? 2 : 1;
    }

    private void setSelectedCell(int x, int y) {
        cells[x][y].setBorder(new CompoundBorder(new LineBorder(Color.GREEN, 3), new EmptyBorder(borderThickness, borderThickness, borderThickness, borderThickness)));
    }

    private boolean rowWin(int x) {
        for (int i = 0; i < length; i++) {
            if (state[x][i] != getCurrentTurn()) {
                return false;
            }
        }

        for (int i = 0; i < length; i++) {
            setSelectedCell(x, i);
        }

        return true;
    }

    private boolean colWin(int y) {
        for (int i = 0; i < length; i++) {
            if (state[i][y] != getCurrentTurn()) {
                return false;
            }
        }

        for (int i = 0; i < length; i++) {
            setSelectedCell(i, y);
        }

        return true;
    }

    private boolean leftDiagonalWin() {
        for (int i = 0; i < length; i++) {
            if (state[i][i] != getCurrentTurn()) {
                return false;
            }
        }

        for (int i = 0; i < length; i++) {
            setSelectedCell(i, i);
        }

        return true;
    }

    private boolean rightDiagonalWin() {
        for (int i = 0; i < length; i++) {
            if (state[i][length - i - 1] != getCurrentTurn()) {
                return false;
            }
        }

        for (int i = 0; i < length; i++) {
            setSelectedCell(i, length - i - 1);
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
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Image img = JSwingUtilities.resizeImageAspectLockedWithMinDimensions(AssetManager.getBufferedSprite("Minigame\\Backgrounds\\BGTicTacToe.jpeg"), getWidth(), getHeight());
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public BufferedImage getMinigameIcon() {
        return AssetManager.getBufferedSprite("Minigame\\Thumbnails\\TicTacToe.jfif");
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
                cells[i][j].setIcon(null);
                cells[i][j].setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(borderThickness, borderThickness, borderThickness, borderThickness)));

            }
        }

        messageLabel.setText("");
    }
}
