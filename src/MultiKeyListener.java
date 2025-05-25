import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.HashSet;

abstract public class MultiKeyListener implements KeyListener {
    protected final HashMap<Integer, KeyEvent> keys = new HashMap<>();

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys.put(e.getKeyCode(), e);
        MultiKeyPressed(keys.values().toArray(new KeyEvent[0]));
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys.remove(e.getKeyCode());
        KeyReleased(e);
    }

    public boolean isKeyPressed(int keyCode) {
        return keys.containsKey(keyCode);
    }

    abstract public void MultiKeyTyped(KeyEvent[] keyEvents);

    abstract public void MultiKeyPressed(KeyEvent[] keyEvents);

    abstract public void KeyReleased(KeyEvent e);
}
