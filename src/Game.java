import Components.Player;
import Components.TypewriterLabel;

import javax.swing.*;
import java.awt.*;

public class Game extends JFrame {
    public Game() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Player player = new Player();
        player.setFocusable(true);

        add(player);
    }

    public void update(Container parent, float delta) {
        for (Component child : parent.getComponents()) {
            if (child instanceof Container) {
                update((Container) child, delta);
            }

            // Temporary
            if (child instanceof Player) {
                ((Player) child).update(delta);
            }

            if (child instanceof TypewriterLabel) {
                ((TypewriterLabel) child).update(delta);
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
