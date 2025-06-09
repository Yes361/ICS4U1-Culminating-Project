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

public class Game extends JFrame implements ComponentListener {
    JGameObject root;
    WorldScreen worldScreen;
    EditorScreen editorScreen;
    MenuScreen menuScreen;
    MinigameScreen minigameScreen;

    public Game() {
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setBounds(0, 0, 500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        root = new JGameObject();
        root.setBounds(0, 0, getWidth(), getHeight());

        worldScreen = new WorldScreen();
        worldScreen.setVisible(false);
        root.addChild(worldScreen);

        menuScreen = new MenuScreen();
        menuScreen.setBounds(0, 0, getWidth(), getHeight());
        root.add(menuScreen);

        editorScreen = new EditorScreen();
        editorScreen.setBounds(0, 0, getWidth(), getHeight());
        editorScreen.setVisible(false);
        root.addChild(editorScreen);

        minigameScreen = new MinigameScreen();
        minigameScreen.setVisible(false);
        root.add(minigameScreen);

        add(root);
    }

    public MinigameScreen getMinigameScreen() {
        return minigameScreen;
    }

    public EditorScreen getEditorScreen() {
        return editorScreen;
    }

    public WorldScreen getWorldScreen() {
        return worldScreen;
    }

    public void initializeAssets() {
        AssetManager.loadFont("LibreBaskerville", new File(AssetManager.getResourceDirectory("Fonts\\LibreBaskerville-Regular.ttf")));
    }

    public void initializePanels() {

    }

    public void UpdateHandler() {
        double pastTimeMillis = System.nanoTime() / 1e6;
        while (true) {
            double currentTimeMillis = System.nanoTime() / 1e6;
            float delta = (float) (currentTimeMillis - pastTimeMillis);
            root.UpdateHandler(delta);
            pastTimeMillis = currentTimeMillis;
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {
        int W = 4;
        int H = 3;
        Rectangle b = e.getComponent().getBounds();
        e.getComponent().setBounds(b.x, b.y, b.width, b.width*H/W);
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}
