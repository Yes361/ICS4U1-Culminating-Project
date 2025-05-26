public class Main {
    public static void main(String[] args) {
        Game game = new Game();

        game.setBounds(0, 0, 500, 500);
        game.setVisible(true);

        game.UpdateHandler();
    }
}