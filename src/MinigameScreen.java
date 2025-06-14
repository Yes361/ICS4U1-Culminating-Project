/*
* Authors: Raiyan Islam and Ahnaf Masud
*
* Minigame Screen
*
*  */

import Components.Minigame;
import Components.Screen;
import Core.GameSystem.JGameObject;
import Core.GameSystem.JGameObjectInterface;
import Utility.JSwingUtilities;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class MinigameScreen extends Screen implements JGameObjectInterface {
    JGameObject root;
    Minigame[] minigames;

    private JScrollPane scrollCatalogue;

    public MinigameScreen() {
        // Setting Minigame Screen Properties
        setBounds(0, 0, 900, 600);
        setLayout(null);
        setOpaque(true);
        setBackground(Color.WHITE);

        // Creating a root JGameObject for this panel
        root = new JGameObject();
        root.setBounds(0, 0, getWidth(), getHeight());
        root.setOpaque(true);
        root.setLayout(null);

        // List of Minigames
        minigames = new Minigame[]{
            new PushItMinigame(),
            new MemoryCardMinigame(),
            new HangManMinigame(),
            new TicTacToeMinigame(),
            // TODO: More fleshed out implementation wordle
            new WordleMinigame(),
            new SuperTicTacToeMinigame(),
            new Connect4Minigame(),
            new MinesweeperMinigame(),
            new OthelloMinigame(),
            new StableSpellMinigame(),
        };

        // GridLayout manager for placing the minigame thumbnails in their correct positions
        GridLayout gridLayout = new GridLayout(minigames.length / 2 + 1, 2);

        // Setting layout manager properties
        gridLayout.setVgap(10);
        gridLayout.setHgap(40);

        // Minigame Catalogue panel
        JPanel minigameCatalogue = new JPanel();

        // Setting catalogue properties
        minigameCatalogue.setBorder(new EmptyBorder(10, 10, 10, 10));
        minigameCatalogue.setBounds(0, 0, getWidth(), getHeight());
        minigameCatalogue.setLayout(gridLayout);
        minigameCatalogue.setOpaque(false);
        minigameCatalogue.setVisible(true);

        // Setting Panel properties
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, getWidth(), getHeight());
        BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.PAGE_AXIS);
        panel.setBorder(new EmptyBorder(20, 0, 50, 0));
        panel.setLayout(boxLayout);
        panel.setOpaque(false);

        // Creating the title
        JLabel titleLabel = new JLabel("Minigame Catalogue!");
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        panel.add(titleLabel);

        // Creating a scroll pane to display the minigames in a scrollable panel
        scrollCatalogue = new JScrollPane(panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollCatalogue.setBounds(0, 0, getWidth(), getHeight());
        scrollCatalogue.setOpaque(false);

        add(scrollCatalogue);

        // Iterating over the minigames and adding them to this screen
        // as well as create their thumbnails
        for (int i = 0;i < minigames.length;i++) {
            Minigame minigame = minigames[i];
            minigame.setFocusable(false);
            minigame.setVisible(false);

            // Creating a button for users to press to play the current minigame
            JButton button = new JButton();
            button.setBackground(Color.BLACK);

            // creating a border
            LineBorder roundedLineBorder = new LineBorder(Color.WHITE, 3, true);
            CompoundBorder compoundBorder = new CompoundBorder(
                    new EmptyBorder(5, 5, 5, 5),
                    roundedLineBorder
            );
            TitledBorder titledBorder = new TitledBorder(
                    compoundBorder,
                    minigame.getMinigameName(),
                    TitledBorder.DEFAULT_JUSTIFICATION,
                    TitledBorder.DEFAULT_POSITION,
                    new Font("Arial", Font.PLAIN, 16),
                    Color.WHITE
            );
            button.setBorder(new CompoundBorder(
                    titledBorder,
                    new EmptyBorder(10, 10, 10, 10)
            ));

            // Adding the minigame to this screen for UI control
            // Adding to the Game Engine life cycle
            minigameCatalogue.add(button);
            root.add(minigames[i]);

            // Listening for changes in size, and resizing icons appropriately
            button.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    // Check if the icon is null or not
                    if (minigame.getMinigameIcon() != null) {

                        // Get the icon and resize it to the button accounting for the border insets
                        BufferedImage bufferedImage = minigame.getMinigameIcon();
                        Insets insets = button.getBorder().getBorderInsets(button);
                        button.setIcon(new ImageIcon(
                            JSwingUtilities.resizeImageAspectLockedWithMinDimensions(
                                bufferedImage,
                                button.getWidth() - insets.left - insets.right,
                                button.getHeight() - insets.top - insets.bottom
                            )
                        ));
                    }
                }
            });

            // Creating an ActionListener to listen for when the button is pressed
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    root.setVisible(true);

                    // Setting the scroll pane invisible to view the minigame
                    scrollCatalogue.setVisible(false);

                    // Displaying the minigame
                    minigame.setVisible(true);
                    minigame.showMinigame();
                    minigame.resetMinigame();
                }
            });

            // Listening to Mouse events for hover styling
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);

                    // When mouse hovers set the background to red
                    button.setBackground(new Color(100, 0, 0));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    super.mouseExited(e);

                    // When mouse leaves set background to baseline color
                    button.setBackground(Color.BLACK);
                }
            });
        }

        panel.add(Box.createVerticalStrut(10));
        panel.add(minigameCatalogue);

        root.setVisible(false);
        add(root);
    }

    /**
     * SwitchToScreen switches to the screen from a minigame
     */
    public void SwitchToScreen() {
        for (Minigame minigame : minigames) {
            minigame.hideMinigame();
        }

        scrollCatalogue.setVisible(true);
    }

    @Override
    public void update(float delta) {
        root.UpdateHandler(delta);
    }

    @Override
    public void showScreen() {

    }

    @Override
    public void hideScreen() {

    }
}
