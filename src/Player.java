import javax.swing.*;
import java.awt.event.KeyEvent;

public class Player extends JLabel implements IGameObject {
    private final MultiKeyListener KeyListener = new MultiKeyListener() {
        @Override
        public void MultiKeyTyped(KeyEvent[] keyEvents) {

        }

        @Override
        public void MultiKeyPressed(KeyEvent[] keyEvents) {

        }

        @Override
        public void KeyReleased(KeyEvent e) {

        }
    };

    public Player() {
        super("I am a player!");

        addKeyListener(KeyListener);
    }

    public void update(float delta) {
        int x = getX();
        int y = getY();
        float speed = 2;

        if (KeyListener.isKeyPressed(KeyEvent.VK_UP)) {
            y -= (int) (speed * delta);
        }

        if (KeyListener.isKeyPressed(KeyEvent.VK_DOWN)) {
            y += (int) (speed * delta);
        }

        if (KeyListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            x -= (int) (speed * delta);
        }

        if (KeyListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            x += (int) (speed * delta);
        }

        setLocation(x, y);
    }
}
