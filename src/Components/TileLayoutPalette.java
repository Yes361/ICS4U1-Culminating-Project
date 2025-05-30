package Components;

import Core.GameSystem.JGameObject;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class TileLayoutPalette extends JGameObject implements MouseListener, MouseMotionListener, MouseWheelListener {
    private TileMap tileMap = new TileMap();
    private int currentTileIndex = 0;
    private String currentTileName = "";
    private int tileWidth = 0;
    private int tileHeight = 0;
    private String[][] tileLayout;
    private TileLayoutRenderer tileLayoutRenderer = new TileLayoutRenderer();
    private Point mousePosition;

    public TileLayoutPalette(TileMap tileMap, int tileWidth, int tileHeight) {
        this.tileMap = tileMap;
        currentTileIndex = 0;
        currentTileName = tileMap.getIdentifier(currentTileIndex);

        tileLayoutRenderer.setTileMap(tileMap);

        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;

        tileLayout = new String[500 / tileWidth][500 / tileHeight];

        tileLayoutRenderer.setTileLayout(tileLayout);

        addMouseListener(this);
        addMouseWheelListener(this);
        addMouseMotionListener(this);

        add(tileLayoutRenderer);
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
        tileLayout[e.getX() / tileWidth][e.getY() / tileHeight] = currentTileName;
        tileLayoutRenderer.setTileLayout(tileLayout);

        tileLayoutRenderer.setBounds(0, 0, getWidth(), getHeight());
        tileLayoutRenderer.repaint();
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
}
