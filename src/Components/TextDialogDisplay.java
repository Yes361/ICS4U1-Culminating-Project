package Components;

import Core.GameSystem.AssetManager;
import Core.GameSystem.JGameObjectInterface;
import Utility.EventEmitter;
import Utility.EventListener;
import Utility.FileUtilities;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;

public class TextDialogDisplay extends JPanel implements JGameObjectInterface, KeyListener {
    private TextTypewriterEngine textTypewriterEngine;
    private ArrayList<CharacterDialog> characterDialogList = new ArrayList<>();
    private int currentDialogIndex = 0;
    private EventEmitter eventEmitter = new EventEmitter();

    public TextDialogDisplay() {
        setBounds(0, 0, 480, 100);

        setFocusable(true);
        grabFocus();

        setBackground(Color.BLACK);
        setBorder(new CompoundBorder(new LineBorder(Color.WHITE, 10, false), new EmptyBorder(10, 10, 10, 10)));

        addKeyListener(this);

        textTypewriterEngine = new TextTypewriterEngine() {
            @Override
            public void onUpdate(float value) {

            }

            @Override
            public void onIteration(int iteration) {

            }
        };

        JLabel label = new JLabel("");
        label.setBounds(0, 400, 500, 100);
        label.setForeground(Color.WHITE);
//        label.setFont(AssetManager.getFont("LibreBaskerville"));
        add(label);

        textTypewriterEngine.playTypewriterEffect(label, "Who is the skibidi-est of them all", 500);
    }

    public void loadScript(ArrayList<CharacterDialog> characterDialogs) {
        characterDialogList = characterDialogs;
    }

    public void saveScriptToFile(File file) {
        FileUtilities.saveToFile(characterDialogList, file);
    }

    public void loadScriptFromFile(File file) {
        characterDialogList = (ArrayList<CharacterDialog>) FileUtilities.loadFromFile(file);
    }

    public void update(float delta) {
        textTypewriterEngine.update(delta);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (textTypewriterEngine.isTextPlaying()) {
            textTypewriterEngine.skipTextAnimation();
        } else {
            if (++currentDialogIndex >= characterDialogList.size()) {
                eventEmitter.emit("onScriptFinish");
            } else {
                eventEmitter.emit("onNextDialogue");
            }

            textTypewriterEngine.playTypewriterEffect((JLabel) getComponent(0), characterDialogList.get(currentDialogIndex).dialogText(), 2000);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void onNextDialogue(EventListener eventListener) {
        eventEmitter.subscribe("onNextDialogue", eventListener);
    }

    public void onScriptFinish(EventListener eventListener) {
        eventEmitter.subscribe("onScriptFinish", eventListener);
    }
}
