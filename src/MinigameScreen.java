import Components.MemoryCardMinigame;
import Components.Minigame;
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

public class MinigameScreen extends JPanel implements JGameObjectInterface {
    JGameObject root;
    Minigame[] minigames;

    public MinigameScreen() {
        setBounds(0, 0, 900, 600);
        setLayout(null);
        setOpaque(true);
        setBackground(Color.WHITE);

        root = new JGameObject();
        root.setOpaque(true);
        root.setLayout(null);
        root.setBounds(0, 0, getWidth(), getHeight());

        minigames = new Minigame[]{
            new PushItMinigame(),
            new MemoryCardMinigame(),
            new HangManMinigame(),
            new TicTacToeMinigame(),
            new WordleMinigame(),
            new SuperTicTacToeMinigame(),
            new Connect4Minigame(),
            new MinesweeperMinigame(),
            new OthelloMinigame(),
            new StableSpellMinigame(),
        };

        GridLayout gridLayout = new GridLayout(minigames.length / 2 + 1, 2);
        gridLayout.setVgap(10);
        gridLayout.setHgap(40);

        JPanel minigameCatalogue = new JPanel();
//        BoxLayout boxLayout = new BoxLayout(minigameCatalogue, BoxLayout.PAGE_AXIS);
//        minigameCatalogue.setLayout(boxLayout);

        minigameCatalogue.setBorder(new EmptyBorder(10, 10, 10, 10));
        minigameCatalogue.setBounds(0, 0, getWidth(), getHeight());
        minigameCatalogue.setLayout(gridLayout);
        minigameCatalogue.setOpaque(false);

//        scrollCatalogue.add(minigameCatalogue);

        minigameCatalogue.setVisible(true);
//        add(minigameCatalogue);


        JPanel panel = new JPanel();
        panel.setBounds(0, 0, getWidth(), getHeight());
        BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.PAGE_AXIS);
        panel.setBorder(new EmptyBorder(20, 0, 50, 0));
        panel.setLayout(boxLayout);
        panel.setOpaque(false);

        JLabel titleLabel = new JLabel("Minigame Catalogue!");
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        panel.add(titleLabel);

        JScrollPane scrollCatalogue = new JScrollPane(panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollCatalogue.setBounds(0, 0, getWidth(), getHeight());
        scrollCatalogue.setOpaque(false);

        add(scrollCatalogue);

        for (int i = 0;i < minigames.length;i++) {
            Minigame minigame = minigames[i];
            minigame.setFocusable(false);
            minigame.setVisible(false);

            JButton button = new JButton();
            button.setBackground(Color.BLACK);

            LineBorder roundedLineBorder = new LineBorder(Color.WHITE, 3, true);
            CompoundBorder compoundBorder = new CompoundBorder(new EmptyBorder(5, 5, 5, 5), roundedLineBorder);
            TitledBorder titledBorder = new TitledBorder(compoundBorder, minigame.getMinigameName(), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.PLAIN, 16), Color.WHITE);
            button.setBorder(new CompoundBorder(titledBorder, new EmptyBorder(10, 10, 10, 10)));

            minigameCatalogue.add(button);
            root.add(minigames[i]);

            button.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    if (minigame.getMinigameIcon() != null) {
                        BufferedImage bufferedImage = minigame.getMinigameIcon();
                        Insets insets = button.getBorder().getBorderInsets(button);
                        button.setIcon(new ImageIcon(JSwingUtilities.resizeImageAspectLockedWithMinDimensions(bufferedImage, button.getWidth() - insets.left - insets.right, button.getHeight() - insets.top - insets.bottom)));
                    }
                }
            });

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    root.setVisible(true);
                    scrollCatalogue.setVisible(false);

                    minigame.setVisible(true);
                    minigame.showMinigame();
                    minigame.resetMinigame();
                }
            });

            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);
                    button.setBackground(new Color(100, 0, 0));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    super.mouseExited(e);
                    button.setBackground(Color.BLACK);
                }
            });
        }

        panel.add(Box.createVerticalStrut(10));
        panel.add(minigameCatalogue);

        root.setVisible(false);
        add(root);
    }

    public void SwitchToScreen() {
        for (Minigame minigame : minigames) {
            minigame.hideMinigame();
        }
    }

    @Override
    public void update(float delta) {
        root.UpdateHandler(delta);
    }
}
