package Components;

import Core.GameSystem.AssetManager;
import Core.GameSystem.JGameObjectInterface;
import Utility.Console;
import Utility.EventEmitter;
import Utility.EventListener;
import Utility.FileUtilities;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TextDialogDisplay extends JPanel implements JGameObjectInterface, KeyListener {
    private TextTypewriterEngine textTypewriterEngine;
    private ArrayList<CharacterDialog> characterDialogList = new ArrayList<>();
    private int currentDialogIndex = 0;
    private EventEmitter eventEmitter = new EventEmitter();
    private JTextArea label;

    public TextDialogDisplay() {
        setBounds(0, 0, 480, 100);

        setFocusable(true);
        grabFocus();

        setBackground(Color.BLACK);
        setBorder(new CompoundBorder(new LineBorder(Color.WHITE, 10, false), new EmptyBorder(10, 10, 10, 10)));

        addKeyListener(this);

        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), "space");
        getActionMap().put("space", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textTypewriterEngine.isTextPlaying()) {
                    textTypewriterEngine.skipTextAnimation();
                } else {
                    if (++currentDialogIndex >= characterDialogList.size()) {
                        eventEmitter.emit("onScriptFinish");
                    } else {
                        eventEmitter.emit("onNextDialogue", currentDialogIndex);
                        textTypewriterEngine.playTypewriterEffect((JTextArea) getComponent(0), characterDialogList.get(currentDialogIndex).dialogText(), 2000);
                    }
                }
            }
        });

        textTypewriterEngine = new TextTypewriterEngine() {
            @Override
            public void onUpdate(float value) {

            }

            @Override
            public void onIteration(int iteration) {

            }
        };

        label = new JTextArea("");
        label.setLineWrap(true);
        label.setBounds(0, 400, 400, getHeight());
        label.setForeground(Color.WHITE);
        label.setBackground(Color.BLACK);
        add(label);
    }

    public void play() {
        textTypewriterEngine.playTypewriterEffect(label, characterDialogList.getFirst().dialogText(), 500);
    }

    public void complete() {
        eventEmitter.emit("onScriptFinish");
        textTypewriterEngine.skipTextAnimation();
    }

    public void parseScriptFromTextFile(File file) {
        try {
            Scanner sc = new Scanner(file);
            ArrayList<CharacterDialog> newCharacterDialogs = new ArrayList<>();

            while (sc.hasNextLine()) {
                String line = sc.nextLine();

                if (line.contains("[")) {
                    String text = sc.nextLine();
                    CharacterDialog currentCharacterDialog = new CharacterDialog(text, line);

                    newCharacterDialogs.add(currentCharacterDialog);
                }
            }

            characterDialogList = newCharacterDialogs;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

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
                eventEmitter.emit("onNextDialogue", currentDialogIndex);
                textTypewriterEngine.playTypewriterEffect((JTextArea) getComponent(0), characterDialogList.get(currentDialogIndex).dialogText(), 2000);
            }
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
