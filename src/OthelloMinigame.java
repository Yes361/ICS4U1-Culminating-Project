import Components.Minigame;
import Core.GameSystem.JGameObject;

import javax.swing.*;

public class OthelloMinigame extends Minigame {
    private int[][] grid;
    private JButton[][] cells;
    private int gridRow = 5;
    private int gridCol = 5;

    public OthelloMinigame() {
        grid = new int[gridRow][gridCol];

        cells = new JButton[gridRow][gridCol];
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
