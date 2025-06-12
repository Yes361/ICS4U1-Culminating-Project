package Components;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

abstract public class Minigame extends JPanel {
    abstract public BufferedImage getMinigameIcon();
    abstract public String getMinigameName();
    abstract public void showMinigame();
    abstract public void hideMinigame();
    abstract public void resetMinigame();
}
