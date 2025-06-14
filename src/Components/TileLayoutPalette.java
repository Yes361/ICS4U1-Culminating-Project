/*
*
*  */

package Components;

import Core.GameSystem.AssetManager;
import Core.GameSystem.JGameObject;
import Core.Input.Input;
import Utility.Console;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Arrays;

public class TileLayoutPalette extends JGameObject implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {
    private TileMap tileMap = new TileMap();
    private int currentTileIndex = 0;
    private String currentTileName = "";
    private int tileWidth = 0;
    private int tileHeight = 0;
    private TileLayoutRenderer tileLayoutRenderer = new TileLayoutRenderer();
    private Point mousePosition;
    private int currentLayer = 0;
    private File file;

    public TileLayoutPalette(TileMap tileMap, int tileWidth, int tileHeight) {
        this.tileMap = tileMap;
        currentTileIndex = 0;
        currentTileName = tileMap.getIdentifier(currentTileIndex);

        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;

        tileLayoutRenderer.setTileMap(tileMap);

        file = new File(AssetManager.getResourceDirectory("Layouts\\dorm.txt"));
        tileLayoutRenderer.createLayoutFromFile(file);

        addMouseListener(this);
        addMouseWheelListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);

        tileLayoutRenderer.repaint();


        InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
//        get(inputMap)
    }

    public void setFile(File file) {
        this.file = file;
    }

    private void placeTile(int x, int y) {
        tileLayoutRenderer.getTileLayout(currentLayer).getTileLayout().get(x).set(y, currentTileName);

        tileLayoutRenderer.setBounds(0, 0, getWidth(), getHeight());
        tileLayoutRenderer.repaint();
    }


    private void deleteTile(int x, int y) {
        tileLayoutRenderer.getTileLayout(currentLayer).getTileLayout().get(x).set(y, null);

        tileLayoutRenderer.setBounds(0, 0, getWidth(), getHeight());
        tileLayoutRenderer.repaint();
    }

    public TileLayoutRenderer getTileLayoutRenderer() {
        return tileLayoutRenderer;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            deleteTile(e.getX() / tileWidth, e.getY() / tileHeight);
        } else {
            placeTile(e.getX() / tileWidth, e.getY() / tileHeight);
        }
//        System.out.println(tileLayoutRenderer.getTileLayout().get(e.getX() / tileWidth).get(e.getY() / tileHeight));
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        currentTileIndex = (currentTileIndex + e.getWheelRotation() + tileMap.getTileCount()) % tileMap.getTileCount();
        currentTileName = tileMap.getIdentifier(currentTileIndex);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mousePosition = e.getPoint();
        repaint();

        if (SwingUtilities.isRightMouseButton(e)) {
            deleteTile(e.getX() / tileWidth, e.getY() / tileHeight);
        } else {
            placeTile(e.getX() / tileWidth, e.getY() / tileHeight);
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePosition = e.getPoint();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        tileLayoutRenderer.paintComponent(g);

        if (mousePosition != null) {
            Image img = tileMap.getTile(currentTileName).getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            g.drawImage(img, mousePosition.x - img.getWidth(this) / 2, mousePosition.y - img.getHeight(this) / 2, this);

            Font font = g.getFont();
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.drawString(String.format("%d %d", mousePosition.x, mousePosition.y), mousePosition.x, mousePosition.y);
            g.setFont(font);
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void increment() {
        currentLayer = (currentLayer + 1) % tileLayoutRenderer.getTileLayoutCount();
    }

    public void saveLayouts() {
        tileLayoutRenderer.saveLayouts(file);
    }

    public void loadLastSave() {
        tileLayoutRenderer.createLayoutFromFile(file);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            increment();
        } else if (e.getKeyCode() == KeyEvent.VK_P) {
            saveLayouts();
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            loadLastSave();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
