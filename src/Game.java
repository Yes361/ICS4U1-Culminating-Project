import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Game extends JFrame {
    public Game() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Player player = new Player();
        player.setFocusable(true);

        this.add(player);
    }

    public void update(Container parent, float delta) {
        for (Component child : parent.getComponents()) {
            if (child instanceof Container) {
                update((Container) child, delta);
            }

            // Temporary
            if (child instanceof IGameObject) {
                ((Player) child).update(delta);
            }
        }
    }

    public void UpdateHandler() {
        double pastTimeMillis = System.currentTimeMillis();
        while (true) {
            double currentTimeMillis = System.currentTimeMillis();
            update(this, (float) (currentTimeMillis - pastTimeMillis));
            pastTimeMillis = currentTimeMillis;
        }
    }
}
