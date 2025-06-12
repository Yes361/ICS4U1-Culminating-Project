import Animation.Tween.AnimationTween;
import Animation.Tween.AnimationTweenBuilder;
import Animation.Tween.TweenListener;
import Core.GameSystem.AssetManager;
import Core.GameSystem.JGameObjectInterface;
import Utility.Console;
import Utility.GraphicUtilies;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuScreen extends JPanel implements JGameObjectInterface {
    private JPanel container;
    private AnimationTween imgXProgress;
    private AnimationTween backgroundFade;

    private List<AnimationTween> buttonAnimations = new ArrayList<>();
    private List<JButton> buttons = new ArrayList<>();

    private int currentBackgroundImage = 0;
    private String[] backgroundImagePaths;

    Font font;

    public MenuScreen() {
//        backgroundImagePaths = new File(AssetManager.getSpriteResourcePath("Icons\\SucroseTitleCard")).list();
        backgroundImagePaths = new String[]{ AssetManager.getSpriteResourcePath("Icons\\academia-background.png") };


        createMenuScreen();
    }

    public void createMenuScreen() {
        // Use vertical BoxLayout and center container using glue
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBounds(0, 0, 900, 600);
        setOpaque(true);
        setBackground(Color.BLACK);

        font = AssetManager.loadFontFromDirectory("Fonts\\Mostical-Demo.ttf");

        container = new JPanel() {
            @Override
            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);
                GraphicUtilies.applyLinearGraient(graphics, Color.BLACK, getWidth(), getHeight());
            }
        };

        container.setOpaque(false);
        container.setPreferredSize(new Dimension(300, getHeight()));
        container.setMaximumSize(new Dimension(300, getHeight()));
        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
        container.setAlignmentX(Component.LEFT_ALIGNMENT); // Center container in parent

        // Add centered icon
        BufferedImage AlchemyBufferedImage = AssetManager.getBufferedSprite("Icons\\AlchemyAcademy.png");
        float aspectRatio = (float) AlchemyBufferedImage.getHeight() / AlchemyBufferedImage.getWidth();
        int AlchemyAcademyLogoWidth = 280;
        ImageIcon AlchemyImageIcon = new ImageIcon(AlchemyBufferedImage.getScaledInstance(AlchemyAcademyLogoWidth, (int) (aspectRatio * AlchemyAcademyLogoWidth), Image.SCALE_SMOOTH));
        JLabel AlchemyIcon = new JLabel(AlchemyImageIcon);
        AlchemyIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

        container.add(Box.createVerticalStrut(20));
        container.add(AlchemyIcon);
        container.add(Box.createVerticalStrut(20));

        // Add buttons
        createNewGameButton();
        createEditorButton();
        createMinigamesButton();

        container.add(createMenuButton("Save Files", null));
        container.add(Box.createVerticalStrut(20));
        container.add(createMenuButton("Settings", null));
        container.add(Box.createVerticalStrut(20));

        // Center container vertically in MenuScreen
        add(Box.createVerticalGlue());
        add(container);
        add(Box.createVerticalGlue());

        imgXProgress = new AnimationTweenBuilder(AnimationTween.Tween.LINEAR, 0, 50)
                .setLoop(true)
                .setDuration(5000)
                .setDirection(AnimationTween.AnimationProperties.REVERSED_DIRECTION)
                .setCallback(new TweenListener() {
                    @Override
                    public void onUpdate(float value) {}

                    @Override
                    public void onIteration(int iteration) {
                        currentBackgroundImage = (currentBackgroundImage + 1) % backgroundImagePaths.length;
                    }
                })
                .build();
    }

    private void createNewGameButton() {
        container.add(createMenuButton("New Game", new MouseListener() {
            public void mousePressed(MouseEvent e) {
                setVisible(false);
                Main.game.worldScreen.setVisible(true);
            }

            public void mouseClicked(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        }));
        container.add(Box.createVerticalStrut(20));
    }

    private void createEditorButton() {
        container.add(createMenuButton("Editor", new MouseListener() {
            public void mousePressed(MouseEvent e) {
                setVisible(false);
                EditorScreen editorScreen = Main.game.getEditorScreen();
                editorScreen.setVisible(true);
                editorScreen.setFocusable(true);
                editorScreen.requestFocus();
            }

            public void mouseClicked(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        }));
        container.add(Box.createVerticalStrut(20));
    }

    private void createMinigamesButton() {
        container.add(createMenuButton("Minigames", new MouseListener() {
            public void mousePressed(MouseEvent e) {
                setVisible(false);
                MinigameScreen minigameScreen = Main.game.getMinigameScreen();
                minigameScreen.setVisible(true);
                minigameScreen.setFocusable(true);
                minigameScreen.requestFocus();
            }

            public void mouseClicked(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        }));
        container.add(Box.createVerticalStrut(20));
    }

    public JButton createMenuButton(String label, MouseListener buttonMouseListener) {
        JButton button = new JButton(label);
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // Center button

        button.setBackground(Color.WHITE);
        button.setOpaque(false);
        button.setContentAreaFilled(false);

        button.setBorderPainted(false);
        button.setBorder(new CompoundBorder(BorderFactory.createLineBorder(Color.BLACK), new EmptyBorder(10, 10, 10, 10)));

        float baselineFontSize = 20f;
        int idx = buttons.size();

        button.setFont(font.deriveFont(baselineFontSize));
        button.setForeground(Color.WHITE);
        button.addMouseListener(new MouseListener() {
            public void mousePressed(MouseEvent e) {
                if (buttonMouseListener != null) {
                    buttonMouseListener.mousePressed(e);
                }
            }

            public void mouseClicked(MouseEvent e) {
                if (buttonMouseListener != null) {
                    buttonMouseListener.mouseClicked(e);
                }
            }

            public void mouseReleased(MouseEvent e) {}

            public void mouseEntered(MouseEvent e) {
                button.setBackground(Color.green);

                button.setFont(font.deriveFont(28f));
//                buttonAnimations.set(idx, new AnimationTweenBuilder(AnimationTween.Tween.LINEAR, baselineFontSize, 24f).setLoop(false).build());
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.white);

                button.setFont(font.deriveFont(baselineFontSize));
                buttonAnimations.set(idx, null);
            }
        });

        buttons.add(button);
        buttonAnimations.add(null);

        return button;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundFade != null) {
            Graphics2D graphics2D = (Graphics2D) g;
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, backgroundFade.getLerpValue()));
        }

//        g.drawImage(AssetManager.getBufferedSprite(String.format("Icons\\SucroseTitleCard\\%s", backgroundImagePaths[currentBackgroundImage]), getWidth(), getHeight()),
//                (int) imgXProgress.getLerpValue(), 0, this);
        g.drawImage(AssetManager.getBufferedSprite(String.format("Icons\\%s", "academia-background.png"), getWidth(), getHeight()),
                (int) imgXProgress.getLerpValue(), 0, this);

        for (int i = 0;i < buttonAnimations.size();i++) {
            if (buttonAnimations.get(i) != null) {
                buttons.get(i).setFont(font.deriveFont(buttonAnimations.get(i).getLerpValue()));
            }
        }
    }

    @Override
    public void update(float delta) {
        if (imgXProgress != null) {
            imgXProgress.update(delta);
        }

        if (backgroundFade != null) {
            backgroundFade.update(delta);
        }

        for (int i = 0;i < buttonAnimations.size();i++) {
            if (buttonAnimations.get(i) != null) {
                buttonAnimations.get(i).update(delta);
            }
        }

        repaint();
    }
}