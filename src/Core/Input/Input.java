package Core.Input;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

// TODO: Add a static global variable containing the keybinds and whatnot
public class Input extends MultiKeyListener implements MouseMotionListener, MouseListener {
    private boolean isMousePressed = false;
    private boolean isHover = false;

    public boolean isMousePressed() {
        return isMousePressed;
    }

    public boolean isHover() {
        return isHover;
    }

    @Override
    public void MultiKeyTyped(KeyEvent[] keyEvents) {

    }

    @Override
    public void MultiKeyPressed(KeyEvent[] keyEvents) {

    }

    @Override
    public void KeyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        isMousePressed = true;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        isMousePressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        isMousePressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        isHover = true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        isHover = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
