package main.java.GUIObjects;

import javax.swing.*;
import java.awt.*;

public class GenomeDisplay extends JFrame {

    private static final Color[] POSSIBLE_COLOURS = {Color.GREEN, Color.RED, Color.CYAN, Color.ORANGE};
    private static final String[] POSSIBLE_CHOICES = {"Hit", "Stay", "Double Down", "Split"};
    private static final String[] DEALER_CHOICES = {"Ace", "Two", "Three", "Four", "Five",
            "Six", "Seven", "Eight", "Nine", "Ten"};
    private static final String[] PLAYER_CHOICES = {"Five", "Six", "Seven", "Eight", "Nine",
            "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen+" +
            "",
            "Soft Thirteen", "Soft Fourteen", "Soft Fifteen", "Soft Sixteen", "Soft Seventeen", "Soft Eighteen",
            "Soft Nineteen", "Soft Twenty", "Pair Ace", "Pair Two", "Pair Three", "Pair Four", "Pair Five",
            "Pair Six", "Pair Seven", "Pair Eight", "Pair Nine", "Pair Ten"};


    public GenomeDisplay(int[] genome) {
        super();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1800, 900);
        Font font = new Font("Courier", Font.BOLD,12);
        JPanel panel = new JPanel();
        JLabel emptyLabel = new JLabel();
        panel.add(emptyLabel);
        panel.setLayout(new GridLayout(PLAYER_CHOICES.length + 1, DEALER_CHOICES.length + 1));
        for (int i = 0; i < DEALER_CHOICES.length; i++) {
            JLabel label = new JLabel();
            label.setText(DEALER_CHOICES[i]);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(label);
        }
        for (int i = 0; i < PLAYER_CHOICES.length; i++) {
            JLabel sideLabel = new JLabel();
            sideLabel.setText(PLAYER_CHOICES[i]);
            sideLabel.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(sideLabel);
            for (int j = 0; j < DEALER_CHOICES.length; j++) {
                JLabel label = new JLabel();
                label.setOpaque(true);
                boolean found = false;
                for (int k = 0; k < (POSSIBLE_COLOURS.length) && !found; k++) {
                    if (genome[(i*10)+ j] == (k + 1)) {
                        label.setBackground(POSSIBLE_COLOURS[k]);
                        label.setFont(font);
                        label.setText(POSSIBLE_CHOICES[k]);
                        found = true;
                    }
                }
                label.setHorizontalAlignment(SwingConstants.CENTER);
                panel.add(label);
            }
        }
        panel.setVisible(true);
        this.getContentPane().add(panel);
        this.setVisible(true);
    }

}
