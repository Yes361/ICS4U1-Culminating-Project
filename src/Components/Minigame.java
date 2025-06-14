/*
* Minigame
*
* Description:
* Base class for minigames
*
*  */

package Components;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

abstract public class Minigame extends JPanel {
    // Retrieves Minigame Thumbnail
    abstract public BufferedImage getMinigameIcon();
    // Retrieves Minigame Name
    abstract public String getMinigameName();
    // Method called when minigame is visible
    abstract public void showMinigame();
    // Method called when minigame is set to hidden
    abstract public void hideMinigame();
    // Method called when minigame is resetted
    abstract public void resetMinigame();
}
