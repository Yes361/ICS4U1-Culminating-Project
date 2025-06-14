/*
* Authors: Raiyan Islam and Ahnaf Masud
*
* Description:
* The Game class contains the root Frame
*
*  */

import Components.*;
import Core.GameSystem.AssetManager;
import Core.GameSystem.JGameObject;
import Utility.Console;
import Utility.EventListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;

public class Game extends JFrame {
    // Field Declarations
    JGameObject root;

    // Screen Declarations
    WorldScreen worldScreen;
    EditorScreen editorScreen;
    MenuScreen menuScreen;
    MinigameScreen minigameScreen;
    SettingScreen settingScreen;

    public Game() {
        // Frame Settings
        setSize(900, 600); // Dimensions
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Default Behavior on Close
        setResizable(false); // Prevents window from sizing

        // Setting Icon Image
        setIconImage(new ImageIcon(AssetManager.getResourceDirectory("Sprites\\Icons\\AlchemyAcademyIcon.jpeg")).getImage());

        // Root JGameObject
        root = new JGameObject();

        // Setting root properties
        root.setOpaque(true);
        root.setLayout(null);
        root.setBounds(0, 0, getWidth(), getHeight());

        /* Adding Screens */

        // Adding the world screen
        worldScreen = new WorldScreen();
        worldScreen.setVisible(false);
        root.add(worldScreen);

        // Adding the menu Screen
        menuScreen = new MenuScreen();
        menuScreen.setBounds(0, 0, getWidth(), getHeight());
        root.add(menuScreen);

        // Adding the Editor Screen
        editorScreen = new EditorScreen();
        editorScreen.setBounds(0, 0, getWidth(), getHeight());
        editorScreen.setVisible(false);
        root.add(editorScreen);

        // Adding the Minigame Screen
        minigameScreen = new MinigameScreen();
        minigameScreen.setVisible(false);
        root.add(minigameScreen);

        // TODO: Implementing the Setting Screen
        settingScreen = new SettingScreen();
        settingScreen.setVisible(false);
//        root.add(settingScreen);

        add(root);
        setVisible(true);
    }

    public MinigameScreen getMinigameScreen() {
        return minigameScreen;
    }

    public EditorScreen getEditorScreen() {
        return editorScreen;
    }

    /**
     * Root UpdateHandler, which processes the difference between calls,
     * and propagates the update signal accordingly
     */
    public void UpdateHandler() {
        // Get current time
        double pastTimeMillis = System.nanoTime() / 1e6;

        while (true) {
            double currentTimeMillis = System.nanoTime() / 1e6;

            // Find difference in time
            float delta = (float) (currentTimeMillis - pastTimeMillis);

            // Propogate Signal
            root.UpdateHandler(delta);
            pastTimeMillis = currentTimeMillis;
        }
    }
}
