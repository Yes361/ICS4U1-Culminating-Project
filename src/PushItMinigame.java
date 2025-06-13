import Components.Minigame;
import Core.GameSystem.AssetManager;
import Core.GameSystem.JGameObjectInterface;
import Core.Input.Input;
import Utility.Clock;
import Utility.Console;
import Utility.JSwingUtilities;
import Utility.MathUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

public class PushItMinigame extends Minigame implements JGameObjectInterface {
    private int targetMin = 0;
    private int targetMax = 15;
    private double current = 0;
    private double score = 0;
    private JLabel debugLabel;
    private JLabel scoreLabel;
    private JSlider slider;
    private JLabel cleck;
    private Clock clock = new Clock();
    private Timer timer;

    public PushItMinigame() {
        setBounds(0, 0, 900, 600);

        debugLabel = new JLabel();
        debugLabel.setText(String.format("%d, %d", targetMin, targetMax));
        add(debugLabel);

        scoreLabel = new JLabel(String.valueOf(score));
        add(scoreLabel);

        cleck = new JLabel();
        add(cleck);

        slider = new JSlider(SwingConstants.VERTICAL, 0, 100, 0) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D graphics2D = (Graphics2D) g;

                Stroke stroke = graphics2D.getStroke();

                graphics2D.setStroke(new BasicStroke(5));
                g.drawLine(0, (int) ((1 - targetMin / 100f) * this.getHeight()), this.getWidth(), (int) ((1 - targetMin / 100f) * this.getHeight()));
                g.drawLine(0, (int) ((1 - targetMax / 100f) * this.getHeight()), this.getWidth(), (int) ((1 - targetMax / 100f) * this.getHeight()));

                graphics2D.setStroke(stroke);
            }
        };
        slider.repaint();

        slider.setEnabled(false);

        InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke("SPACE"), "ACTION");

        getActionMap().put("ACTION", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSliderValue(current - 2);
            }
        });

        clock.startWatch("normal");

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

        add(slider);
    }

    private void setSliderValue(double value) {
        current = MathUtilities.constrain(slider.getMinimum(), slider.getMaximum(), value);
        slider.setValue((int) current);
    }

    @Override
    public void update(float delta) {
        setSliderValue(current + 0.01 * delta);

//        Console.println(targetMin, slider.getValue(), targetMax);

        if (slider.getValue() > targetMin && slider.getValue() < targetMax) {
            score += 1 * delta;
        } else {
            score -= 0.5 * delta;
        }

        if (score < 0) {
            score = 0;
        }

        scoreLabel.setText(String.format("%.2f", score));

        cleck.setText(clock.formatTime("normal"));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

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
