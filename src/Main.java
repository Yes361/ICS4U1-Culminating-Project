public class Main {
    public static Game game;
    public static void main(String[] args) {
        game = new Game();

        game.setBounds(0, 0, 500, 500);
        game.setVisible(true);

        game.UpdateHandler();
    }
}