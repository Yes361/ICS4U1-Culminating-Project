import Components.MemoryCardMinigame;
import Components.Minigame;
import Core.GameSystem.JGameObject;
import Core.GameSystem.JGameObjectInterface;
import Utility.Console;
import Utility.SwingUtilities;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;

public class MinigameScreen extends JPanel implements JGameObjectInterface {
    JGameObject root;
    Minigame[] minigames;

    public MinigameScreen() {
        setBounds(0, 0, 900, 600);
        setLayout(null);
        setOpaque(true);
        setBackground(Color.BLACK);

        root = new JGameObject();
        root.setOpaque(true);
        root.setLayout(null);
        root.setBounds(0, 0, getWidth(), getHeight());

        minigames = new Minigame[]{
            new MemoryCardMinigame(),
            new HangManMinigame(),
            new TicTacToeMinigame(),
            new WordleMinigame(),
            new SuperTicTacToeMinigame(),
            new Connect4Minigame(),
            new MinesweeperMinigame(),
            new OthelloMinigame()
        };

        GridLayout gridLayout = new GridLayout(4, 2);
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

        for (int i = 0;i < minigames.length;i++) {
            Minigame minigame = minigames[i];
            minigame.setFocusable(false);
            minigame.setVisible(false);

            JButton button = new JButton();
//            button.setText(minigame.getMinigameName());

            minigameCatalogue.add(button);
            root.add(minigames[i]);

            button.addComponentListener(new ComponentListener() {
                @Override
                public void componentResized(ComponentEvent e) {
                    if (minigame.getMinigameIcon() != null) {
                        BufferedImage bufferedImage = minigame.getMinigameIcon();
                        Insets insets = button.getBorder().getBorderInsets(button);
                        button.setIcon(new ImageIcon(SwingUtilities.resizeImageAspectLockedWithMinDimensions(bufferedImage, button.getWidth() - insets.left - insets.right, button.getHeight() - insets.top - insets.bottom)));
                    }
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
            });

            int index = i;

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    root.setVisible(true);
                    minigameCatalogue.setVisible(false);

                    minigame.setVisible(true);
                    minigame.showMinigame();
                    minigame.resetMinigame();
                }
            });
        }

        JScrollPane scrollCatalogue = new JScrollPane(minigameCatalogue, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollCatalogue.setBounds(0, 0, getWidth(), getHeight());
        scrollCatalogue.setOpaque(false);
        add(scrollCatalogue);

        root.setVisible(false);
        add(root);

        repaint();
    }

    public void SwitchGame() {

    }

    @Override
    public void update(float delta) {
        root.UpdateHandler(delta);
    }
}
