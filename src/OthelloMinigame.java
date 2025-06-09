import Core.GameSystem.JGameObject;

public class OthelloMinigame extends JGameObject {
    private int[][] grid;
    private int gridRow = 5;
    private int gridCol = 5;

    public OthelloMinigame() {
        grid = new int[gridRow][gridCol];
    }
}
