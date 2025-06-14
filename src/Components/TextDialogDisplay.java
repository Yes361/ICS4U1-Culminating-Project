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
    private JLabel imageIcon;

    public TextDialogDisplay() {
        setBounds(0, 0, 900, 600);
        setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(0, 500, getWidth(), 100);

        setFocusable(true);
        setOpaque(false);
        grabFocus();

        setBackground(Color.WHITE);
        setBorder(new CompoundBorder(new LineBorder(Color.WHITE, 10, false), new EmptyBorder(0, 0, 0, 0)));

        addKeyListener(this);

        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), "space");
        getActionMap().put("space", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            if (textTypewriterEngine.isTextPlaying()) {
                textTypewriterEngine.skipTextAnimation();
            } else if (++currentDialogIndex >= characterDialogList.size()) {
                eventEmitter.emit("onScriptFinish");
            } else {
                Console.println(AssetManager.getResourceDirectory(characterDialogList.get(currentDialogIndex).chrPath()));
                imageIcon.setIcon(new ImageIcon(AssetManager.getResourceDirectory(characterDialogList.get(currentDialogIndex).chrPath())));
                eventEmitter.emit("onNextDialogue", currentDialogIndex);
                textTypewriterEngine.playTypewriterEffect(label, characterDialogList.get(currentDialogIndex).dialogText(), 2000);
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
        label.setForeground(Color.BLACK);
        label.setOpaque(false);
        label.setEditable(false);
        panel.add(label);

        add(panel);

        imageIcon = new JLabel();
        imageIcon.setBounds(0, 100, getWidth(), 400);
        add(imageIcon);
    }

    public void play() {
        textTypewriterEngine.playTypewriterEffect(label, characterDialogList.getFirst().dialogText(), 500);
        imageIcon.setIcon(new ImageIcon(AssetManager.getResourceDirectory(characterDialogList.getFirst().chrPath())));
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
                    String name = line.substring(1, line.length() - 1);
                    String path = sc.nextLine();
                    String chrPath = path.substring(1, path.length() - 1);

                    String text = sc.nextLine();
                    CharacterDialog currentCharacterDialog = new CharacterDialog(text, name, chrPath);

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
