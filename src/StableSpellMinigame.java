/*
* StableSpell is a minigame that visualizes 2 sorting algorithms
*
* Future Improvements:
* Implementation of the selection sort algorithm
* and the recursive sorting algorithms
*  */

import Components.Minigame;
import Core.GameSystem.AssetManager;
import Core.GameSystem.JGameObjectInterface;
import Utility.Console;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class StableSpellMinigame extends Minigame implements JGameObjectInterface {
    private JLabel[] labels;
    private int[] values;

    private JPanel panel;

    // Iteration variables
    private int i = 0;
    private int j = 0;

    private Timer timer;

    private boolean isSorting = false;

    private JComboBox<String> comboBox;

    public StableSpellMinigame() {
        setBounds(0, 0, 900, 600);

        // Combobox for selecting a sorting algorithm
        comboBox = new JComboBox<>();
        comboBox.addItem("Insertion Sort");
        comboBox.addItem("Bubble Sort");
        add(comboBox);

        // Panel for displaying the objects
        panel = new JPanel();
        panel.setBounds(0, 0, getWidth(), getHeight());
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));

        // Randomizing values
        values = new int[10];
        for (int k = 0; k < values.length; k++) {
            values[k] = (int) (Math.random() * 100);
        }

        // Creating labels
        labels = new JLabel[values.length];
        for (int k = 0; k < values.length; k++) {
            JLabel label = new JLabel(values[k] + "");
            label.setOpaque(true);
            label.setBackground(Color.LIGHT_GRAY);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

            label.setPreferredSize(new Dimension(30, 100));

            label.setVerticalAlignment(SwingConstants.BOTTOM);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 16));

            labels[k] = label;

            panel.add(Box.createHorizontalStrut(5)); 
            panel.add(label);
        }

        // Reseting the button
        JButton resetButton = new JButton("Reset !");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetMinigame();
            }
        });

        add(panel);
        add(resetButton);

        // Reseting timer
        JButton startButton = new JButton("Start Again!");
        startButton.setFocusPainted(false);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timer != null) {
                    timer.stop();
                }

                createTimer();
            }
        });

        add(startButton);

        JButton rulesButton = new JButton("Rules");
        rulesButton.setAlignmentX(CENTER_ALIGNMENT);
        rulesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MinigameRuleFrame.showPopup("""
                Stable Spell
                
                Rules:
                
                You have a randomised set of spell incantations, and you have the option to sort the incantations in various ways, such as bubble sort, selection sort, insertion sort, and more! Pick your potion!\s
                
                """);
            }
        });

        add(rulesButton);
    }

    @Override
    public BufferedImage getMinigameIcon() {
        return AssetManager.getBufferedSprite("Minigame\\Thumbnails\\Stable Spells.jpeg");
    }

    @Override
    public String getMinigameName() {
        return "Stable Spell";
    }

    @Override
    public void showMinigame() {
        if (isSorting) return;

        isSorting = true;
        comboBox.setEditable(false);

        // Set the starting numbers based on the current sorting algorithm
        switch ((String) comboBox.getSelectedItem()) {
            case "Bubble Sort":
                i = 0;
                j = 0;
                break;
            case "Insertion Sort":
                i = 1;
                j = 1;
                break;
            case "Selection Sort":
                break;
        }

        createTimer();
    }

    public void createTimer() {
        // Create timer that performs different actions based on which
        // sorting algorithm is selected
        timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch ((String) comboBox.getSelectedItem()) {
                    case "Bubble Sort":
                        bubbleSortStep();
                        break;
                    case "Insertion Sort":
                        insertionSortStep();
                        break;
                    case "Selection Sort":
                        break;
                }
            }
        });
        timer.setRepeats(true);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                timer.start();
            }
        });
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

        switch ((String) comboBox.getSelectedItem()) {
            case "Bubble Sort":
                i = 0;
                j = 0;
                break;
            case "Insertion Sort":
                i = 1;
                j = 1;
                break;
            case "Selection Sort":
                break;
        }

        if (timer != null) {
            timer.stop();
        }
    }

    @Override
    public void update(float delta) {
        
    }

    // Implementation of the bubble sort algorithms using iterator variables
    private void bubbleSortStep() {
        int n = values.length;
        
        if (i < n) {
            if (j < n - i ) {
                
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
            comboBox.setEditable(true);

            for (JLabel label : labels) {
                label.setBackground(Color.LIGHT_GRAY);
            }
        }
    }

    public void insertionSortStep() {
        Console.println(i, j);
        if (j < values.length) {
            if (j > 0) {
                highlightLabels(j, j - 1, Color.YELLOW);

                if (values[j] < values[j - 1]) {
                    int temp = values[j];
                    values[j] = values[j - 1];
                    values[j - 1] = temp;

                    JLabel tempLabel = labels[j];
                    labels[j] = labels[j - 1];
                    labels[j - 1] = tempLabel;

                    panel.removeAll();
                    for (JLabel label : labels) {
                        panel.add(Box.createHorizontalStrut(5));
                        panel.add(label);
                    }

                    panel.revalidate();
                    panel.repaint();

                    j--;
                } else {
                    i++;
                    j = i - 1;
                }
            } else {
                i++;
                j = i - 1;
            }
        } else {
            isSorting = false;
            timer.stop();
            comboBox.setEditable(false);

            for (JLabel label : labels) {
                label.setBackground(Color.LIGHT_GRAY);
            }
        }
    }

    // Highlights labels that are key points of interest
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
