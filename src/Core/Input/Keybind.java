package Core.Input;

import javax.swing.*;
import java.util.*;

public class Keybind {
    private String actionName;
    private ArrayList<KeyStroke> keyStroke;
    
    public Keybind(String actionName, KeyStroke ...keyStrokes) {
        this.actionName = actionName;
        this.keyStroke = new ArrayList<>(List.of(keyStrokes));
    }
}
