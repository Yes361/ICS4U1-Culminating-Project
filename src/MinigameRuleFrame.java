import Components.Minigame;
import Core.GameSystem.AssetManager;
import Core.GameSystem.JGameObjectInterface;
import Utility.JSwingUtilities;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

public class MinigameRuleFrame {
    private static JFrame popup;

    public static void showPopup(String message) {
        popup = new JFrame("Popup");

        popup.setSize(300, 200);
        popup.setLocationRelativeTo(null);
        popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        popup.setIconImage(new ImageIcon(AssetManager.getResourceDirectory("Sprites\\Icons\\AlchemyAcademyIcon.jpeg")).getImage());
        popup.setTitle("Alchemy Academy");


        JTextPane label = new JTextPane();
        label.setEditable(false);
        label.setText(message);

        popup.add(label);

        popup.setVisible(true);
    }

    public static void hidePopup() {
        popup.setVisible(false);
    }
}


