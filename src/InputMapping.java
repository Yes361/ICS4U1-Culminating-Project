import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

public class InputMapping extends MultiKeyListener {
    private final ArrayList<Keybind> Keybinds = new ArrayList<>();

    @Override
    public void MultiKeyTyped(KeyEvent[] keyEvents) {

    }

    @Override
    public void MultiKeyPressed(KeyEvent[] keyEvents) {

    }

    @Override
    public void KeyReleased(KeyEvent e) {

    }

    public boolean isActionPressed() {
//        for ()
        return false;
    }


    public void addKeybind(Keybind keybind) {
        Keybinds.add(keybind);
    }
}
