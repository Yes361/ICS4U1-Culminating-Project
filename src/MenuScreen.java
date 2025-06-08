import Core.GameSystem.AssetManager;
import Core.GameSystem.JGameObjectInterface;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class MenuScreen extends JPanel implements JGameObjectInterface {
    public MenuScreen() {
        createMenuScreen();
    }

    public void createMenuScreen() {
        BoxLayout layoutManagerMenu = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        setLayout(layoutManagerMenu);

//        set

        Container container = new Container();
        LayoutManager layoutManager = new BoxLayout(container, BoxLayout.PAGE_AXIS);
        container.setLayout(layoutManager);

        setBounds(0, 0, 500, 500);
        setBackground(Color.WHITE);

//        ButtonGroup menuButtonGroup = new ButtonGroup();

        container.add(createMenuButton("New Game", new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                setVisible(false);
                add(new WorldScreen());
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
        }));
        container.add(Box.createVerticalStrut(20));

        container.add(createMenuButton("Save Files", null));
        container.add(Box.createVerticalStrut(20));

        container.add(createMenuButton("Settings", null));
        container.add(Box.createVerticalStrut(20));

//        BufferedImage
        ImageIcon AlchemyImageIcon = new ImageIcon(AssetManager.getBufferedSprite("Icons\\Academy.png", 256,  256));
        JLabel AlchemyIcon = new JLabel(AlchemyImageIcon);
        add(AlchemyIcon);

        add(container);

//        ImageIcon academiaBackgroundIcon = new ImageIcon(AssetManager.getBufferedSprite("Icons\\academia-background.png", 256,  256));
//        JLabel academiaBackground = new JLabel(academiaBackgroundIcon);
//        academiaBackground.setBounds(0, 0, getWidth(), getHeight());
//
//        add(academiaBackground);
//        setComponentZOrder(academiaBackground, 0);
    }

    public JButton createMenuButton(String label, MouseListener buttonMouseListener) {
        JButton button = new JButton(label);
        button.setBackground(Color.white);
        button.setBorder(new CompoundBorder(BorderFactory.createLineBorder(Color.BLACK), new EmptyBorder(10,10,10,10)));
//        button.setBorder(new CompoundBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED), new EmptyBorder(10,10,10,10)));
//        button.setMargin(new Insets(1, 1, 1, 1));
//        button.set
//        button.setFont(new Font("Arial", ))

//      TODO: Add in the rest
        button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                buttonMouseListener.mouseClicked(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                buttonMouseListener.mousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(Color.green);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.white);
            }
        });

        return button;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(AssetManager.getBufferedSprite("Icons\\academia-background.png", getWidth(),  getHeight()), 0, 0, this);
    }

    @Override
    public void update(float delta) {

    }
}
