import Components.Minigame;
import Core.GameSystem.JGameObjectInterface;
import Utility.Console;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class StableSpellMinigame extends Minigame implements JGameObjectInterface {
    private JLabel[] labels;
    private int[] values;

    private JPanel panel;

    private int i = 0;
    private int j = 0;

    private Timer timer;

    private boolean isSorting = false;

    public StableSpellMinigame() {
        setBounds(0, 0, 900, 600);

        panel = new JPanel();
        panel.setBounds(0, 0, getWidth(), getHeight());

        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));

        values = new int[10];
        for (int k = 0; k < values.length; k++) {
            values[k] = (int) (Math.random() * 100);
        }

        labels = new JLabel[values.length];
        for (int k = 0; k < values.length; k++) {
            JLabel label = new JLabel(values[k] + "");
            label.setOpaque(true);
            label.setBackground(Color.LIGHT_GRAY);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

            label.setPreferredSize(new Dimension(30, 100));

            label.setVerticalAlignment(SwingConstants.BOTTOM);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(new Font("Monospaced", Font.BOLD, 16));

            panel.add(Box.createHorizontalStrut(5)); 
            panel.add(label);
        }

        add(panel);
    }

    @Override
    public BufferedImage getMinigameIcon() {
        return null;
    }

    @Override
    public String getMinigameName() {
        return "Bubble Sort Visualizer";
    }

    @Override
    public void showMinigame() {
        if (isSorting) return;
        Console.println("wat");

        isSorting = true;

        timer = new Timer(500, e -> bubbleSortStep()); 
        timer.start();
    }

    @Override
    public void hideMinigame() {
        
    }

    @Override
    public void resetMinigame() {
        isSorting = false;

        for (int k = 0; k < values.length; k++) {
            values[k] = (int) (Math.random() * 100);
            if (labels[k] == null) continue;

            labels[k].setText(values[k] + "");
            labels[k].setBackground(Color.LIGHT_GRAY);
        }

        i = 0;
        j = 0;

        if (timer != null) {
            timer.stop();
        }
    }

    @Override
    public void update(float delta) {
        
    }
    private void bubbleSortStep() {
        int n = values.length;

        
        if (i < n - 1) {
            if (j < n - i - 1) {
                
                highlightLabels(j, j + 1, Color.YELLOW);

                
                if (values[j] > values[j + 1]) {
                    int temp = values[j];
                    values[j] = values[j + 1];
                    values[j + 1] = temp;

                    
                    JLabel tempLabel = labels[j];
                    labels[j] = labels[j + 1];
                    labels[j + 1] = tempLabel;

                    
                    panel.removeAll();
                    for (JLabel label : labels) {
                        panel.add(Box.createHorizontalStrut(5)); 
                        panel.add(label);
                    }
                    panel.revalidate();
                    panel.repaint();
                }

                j++;
            } else {
                
                j = 0;
                i++;
            }
        } else {
            
            isSorting = false;
            timer.stop();

            
            for (JLabel label : labels) {
                label.setBackground(Color.LIGHT_GRAY);
            }
        }
    }

    private void highlightLabels(int a, int b, Color color) {
        for (JLabel label : labels) {
            label.setBackground(Color.LIGHT_GRAY);
        }
        if (a < labels.length) {
            labels[a].setBackground(color);
        }
        if (b < labels.length) {
            labels[b].setBackground(color);
        }
        panel.repaint();
    }
}
