/*
* Push It Minigame!
*
* Description:
* This is one of the more visual and involved games relying heavily on Swing
*
*  */

import Components.Minigame;
import Core.GameSystem.AssetManager;
import Core.GameSystem.JGameObjectInterface;
import Utility.Clock;
import Utility.JSwingUtilities;
import Utility.MathUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Random;

public class PushItMinigame extends Minigame implements JGameObjectInterface {
    // Current Targets
    private int targetMin = 0;
    private int targetMax = 15;

    // Score Values
    private double current = 0;
    private double score = 0;

    // JLabels
    private JLabel debugLabel;
    private JLabel scoreLabel;
    private JSlider slider;
    private JLabel clockLabel;

    private Clock clock = new Clock();

    public PushItMinigame() {
        // Setting Minigame JPanel properties
        setBounds(0, 0, 900, 600);

        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        setLayout(boxLayout);

        add(Box.createVerticalStrut(10));
        setAlignmentX(Component.CENTER_ALIGNMENT);

        debugLabel = new JLabel();
        debugLabel.setText(String.format("%d, %d", targetMin, targetMax));
//        add(debugLabel);

        // Creating a score label
        scoreLabel = new JLabel(String.valueOf(score));
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JSwingUtilities.resizeFont(scoreLabel, 24); // Resizing the scoreLabel's font
        scoreLabel.setForeground(Color.WHITE); // Setting the scoreLabel's color
        add(scoreLabel); // adding the score label

        add(Box.createVerticalStrut(10));

        // the current clock label
        clockLabel = new JLabel();
        clockLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JSwingUtilities.resizeFont(clockLabel, 24); // resizing the clockLabel's font
        clockLabel.setForeground(Color.WHITE);
        add(clockLabel); // adding the clock label

        add(Box.createVerticalStrut(10));

        // Creating the slider for the game
        slider = new JSlider(SwingConstants.VERTICAL, 0, 100, 0) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D graphics2D = (Graphics2D) g;
                Stroke stroke = graphics2D.getStroke(); // Preserving the stroke

                graphics2D.setStroke(new BasicStroke(5));

                // Drawing the lines for the targetMin and targetMax on the slider
                g.drawLine(0, (int) ((1 - targetMin / 100f) * this.getHeight()), this.getWidth(), (int) ((1 - targetMin / 100f) * this.getHeight()));
                g.drawLine(0, (int) ((1 - targetMax / 100f) * this.getHeight()), this.getWidth(), (int) ((1 - targetMax / 100f) * this.getHeight()));

                graphics2D.setStroke(stroke); // Resetting the stroke
            }
        };

        // Dimension of the slider
        Dimension dimension = new Dimension(50, 300);
        slider.setPreferredSize(dimension);
        slider.setMaximumSize(dimension);
        slider.setMinimumSize(dimension);

        slider.setAlignmentX(Component.CENTER_ALIGNMENT);
        slider.setEnabled(false);
        slider.repaint();
        add(slider);

        // InputMap to perform actions based on input
        InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke("SPACE"), "ACTION");

        getActionMap().put("ACTION", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSliderValue(current - 2);
            }
        });

        // Starting the clock
        clock.startWatch("normal");

        // Creating a timer to change the targetMin and targetMax every specified delay (5sec)
        Random random = new Random();
        Timer timer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof Timer timerObj) {
                    targetMin = random.nextInt(0, 80);
                    targetMax = targetMin + random.nextInt(10, 20);

                    debugLabel.setText(String.format("%d, %d", targetMin, targetMax));

                    slider.repaint();
                }
            }
        });

        timer.start();

        JButton rulesButton = new JButton("Rules");
        rulesButton.setAlignmentX(CENTER_ALIGNMENT);
        rulesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MinigameRuleFrame.showPopup("""
                        Push It!
                        
                        Rules:
                        
                        There is a bar that will slide from left to right, your goal is to tap the space bar continuously to keep your bar on the target for a certain length of time. Achieve this, and you win the game!
                        """);
            }
        });

        add(rulesButton);
    }

    /**
     * setSliderValue sets the value of the slider whilst constraining it
     *
     * @param value the value to set the slider to
     */
    private void setSliderValue(double value) {
        current = MathUtilities.constrain(slider.getMinimum(), slider.getMaximum(), value);
        slider.setValue((int) current);
    }

    @Override
    public void update(float delta) {
        // Every tick increase the slider's value
        setSliderValue(current + 0.01 * delta);

        // If the slider is within the range increase the score
        if (slider.getValue() > targetMin && slider.getValue() < targetMax) {
            score += 1 * delta;
        } else {
            // When the slider is outside the range, decrease the score
            score -= 0.5 * delta;
        }

        // Constrain the score
        if (score < 0) {
            score = 0;
        }

        // Setting labels
        scoreLabel.setText(String.format("Score: %.2f", score));
        clockLabel.setText(clock.formatTime("normal"));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Setting the background of the minigame
        Image img = JSwingUtilities.resizeImageAspectLockedWithMinDimensions(AssetManager.getBufferedSprite("Minigame\\Backgrounds\\BGPushIt.png"), getWidth(), getHeight());
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public BufferedImage getMinigameIcon() {
        return AssetManager.getBufferedSprite("Minigame\\Thumbnails\\Push It!.jfif");
    }

    @Override
    public String getMinigameName() {
        return "Push It!";
    }

    @Override
    public void showMinigame() {

    }

    @Override
    public void hideMinigame() {

    }

    @Override
    public void resetMinigame() {

    }
}
