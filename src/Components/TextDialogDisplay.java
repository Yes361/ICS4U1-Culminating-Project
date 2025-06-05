package Components;

import Core.GameSystem.JGameObjectInterface;
import Utility.Console;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class TextDialogDisplay extends JPanel implements JGameObjectInterface, KeyListener {
    private TextTypewriterEngine textTypewriterEngine;
    private ArrayList<CharacterDialog> characterDialogList = new ArrayList<>() {{
        add(new CharacterDialog("Raiyan who am I", "someone.png"));
        add(new CharacterDialog("Raiyan who am I 1", "someone.png"));
        add(new CharacterDialog("Raiyan who am I 2", "someone.png"));
    }};
    private int currentDialogIndex = 0;
//    private

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
            currentDialogIndex++;
            Console.println(characterDialogList.get(currentDialogIndex).dialogText());
            textTypewriterEngine.playTypewriterEffect((JLabel) getComponent(0), characterDialogList.get(currentDialogIndex).dialogText(), 2000);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
