package Components;

import Core.GameSystem.AssetManager;
import Core.GameSystem.JGameObject;

import java.awt.*;
import java.awt.event.*;
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

    public TileLayoutPalette(TileMap tileMap, int tileWidth, int tileHeight) {
        this.tileMap = tileMap;
        currentTileIndex = 0;
        currentTileName = tileMap.getIdentifier(currentTileIndex);

        TileLayout tileLayout1 = new TileLayout();
        TileLayout tileLayout2 = new TileLayout();
        TileLayout tileLayout3 = new TileLayout();

        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;

        tileLayout1.setTileLayout(new String[tileWidth][tileHeight]);
        tileLayout2.setTileLayout(new String[tileWidth][tileHeight]);
        tileLayout3.setTileLayout(new String[tileWidth][tileHeight]);

        tileLayoutRenderer.addTileLayout(tileLayout1);
        tileLayoutRenderer.addTileLayout(tileLayout2);
        tileLayoutRenderer.addTileLayout(tileLayout3);

        tileLayoutRenderer.setTileMap(tileMap);

        addMouseListener(this);
        addMouseWheelListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);

        add(tileLayoutRenderer);
    }

    private void placeTile(int x, int y) {
        tileLayoutRenderer.getTileLayout(currentLayer).getTileLayout().get(x).set(y, currentTileName);

        tileLayoutRenderer.setBounds(0, 0, getWidth(), getHeight());
        tileLayoutRenderer.repaint();
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
        placeTile(e.getX() / tileWidth, e.getY() / tileHeight);
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

        placeTile(e.getX() / tileWidth, e.getY() / tileHeight);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePosition = e.getPoint();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (mousePosition != null) {
            Image img = tileMap.getTile(currentTileName).getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            g.drawImage(img, mousePosition.x - img.getWidth(this) / 2, mousePosition.y - img.getHeight(this) / 2, this);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            currentLayer = (currentLayer + 1) % tileLayoutRenderer.getTileLayoutCount();
        } else if (e.getKeyCode() == KeyEvent.VK_P) {
            tileLayoutRenderer.saveLayouts(AssetManager.getResourceDirectory("Layouts\\layout.txt"));
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            tileLayoutRenderer.createLayoutFromFile(AssetManager.getResourceDirectory("Layouts\\layout.txt"));
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
