import Components.Camera2D;
import Components.TileLayoutPalette;
import Components.TileMap;
import Core.GameSystem.AssetManager;
import Core.GameSystem.JGameObject;
import Core.Input.Input;
import Utility.Console;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;

public class EditorScreen extends JGameObject {
    private TileLayoutPalette tileLayoutPalette;
    private TileMap tileMap = new TileMap();
    private Camera2D camera2D;
    private Input input = new Input();
    boolean left = false;
    boolean right = false;
    boolean up = false;
    boolean down = false;

    JFrame frame;
    JFileChooser fileChooser = new JFileChooser();

    public EditorScreen() {
        setBounds(0, 0, 900, 600);

        tileMap.recursiveAddFromDirectory(AssetManager.getSpriteResourcePath("TileMap"));

        tileLayoutPalette = new TileLayoutPalette(tileMap, 32, 32);
        tileLayoutPalette.setBounds(0, 0, getWidth(), getHeight());
        addKeyListener(tileLayoutPalette);

        camera2D = new Camera2D();
        camera2D.setBounds(0, 0, getWidth(), getHeight());
        camera2D.setCenter(50, 50);

        InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke("pressed UP"), "pressed UP");
        inputMap.put(KeyStroke.getKeyStroke("pressed DOWN"), "pressed DOWN");
        inputMap.put(KeyStroke.getKeyStroke("pressed LEFT"), "pressed LEFT");
        inputMap.put(KeyStroke.getKeyStroke("pressed RIGHT"), "pressed RIGHT");
        inputMap.put(KeyStroke.getKeyStroke("released UP"), "released UP");
        inputMap.put(KeyStroke.getKeyStroke("released DOWN"), "released DOWN");
        inputMap.put(KeyStroke.getKeyStroke("released LEFT"), "released LEFT");
        inputMap.put(KeyStroke.getKeyStroke("released RIGHT"), "released RIGHT");

        getActionMap().put("pressed UP", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                up = true;
            }
        });
        getActionMap().put("released UP", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                up = false;
            }
        });
        getActionMap().put("pressed DOWN", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                down = true;
            }
        });
        getActionMap().put("released DOWN", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                down = false;
            }
        });
        getActionMap().put("pressed LEFT", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                left = true;
            }
        });
        getActionMap().put("released LEFT", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                left = false;
            }
        });
        getActionMap().put("pressed RIGHT", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                right = true;
            }
        });
        getActionMap().put("released RIGHT", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                right = false;
            }
        });

        addChild(tileLayoutPalette);

        JPanel panel = new JPanel();
        int yVal = 500;
        panel.setBounds(0, yVal, getWidth(), getHeight() - yVal);
        panel.setBorder(new LineBorder(Color.BLACK, 3));

        frame = new JFrame();
        frame.setBounds(0, 0, 500, 500);

        JButton OpenFileLevel = new JButton("Open Level");
        OpenFileLevel.setBounds(500, 300, 100, 30);

        OpenFileLevel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.setCurrentDirectory(new File(AssetManager.getResourceDirectory("Layouts")));
                int FileChooserReturn = fileChooser.showOpenDialog(frame);

                switch (FileChooserReturn) {
                    case JFileChooser.APPROVE_OPTION -> {
                        File file = fileChooser.getSelectedFile();
                        tileLayoutPalette.setFile(file);
                        tileLayoutPalette.getTileLayoutRenderer().createLayoutFromFile(file);
                        requestFocus();
                    }
                    case JFileChooser.CANCEL_OPTION -> {

                    }
                }
            }
        });

//        JLabel label = new JLabel();

        panel.add(OpenFileLevel);
        panel.setComponentZOrder(OpenFileLevel, 0);

//        add(panel);
//        setComponentZOrder(panel, 0);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }
}
