import java.awt.event.KeyEvent;

public interface IMultiKeyListener {
    void MultiKeyTyped(KeyEvent[] keyEvents);
    void MultiKeyPressed(KeyEvent[] keyEvents);
    void KeyReleased(KeyEvent e);
}
