package com.coffeecode.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.coffeecode.model.AlphabetModel;
import com.coffeecode.viewmodel.AlphabetViewModel;

public class AlphabetView {
    private AlphabetViewModel viewModel;
    private JPanel mainPanel;
    private JPanel alphabetGridPanel;
    private JPanel buttonPanel;

    public AlphabetView(AlphabetViewModel viewModel) {
        this.viewModel = viewModel;
        mainPanel = new JPanel(new BorderLayout());
        alphabetGridPanel = new JPanel();
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        // Set background color
        mainPanel.setBackground(Color.decode("#f0f0f0"));
        alphabetGridPanel.setBackground(Color.white);
        buttonPanel.setBackground(Color.decode("#f0f0f0"));

        // Initialize panels
        updateAlphabetGridPanel();
        initializeButtonPanel();

        // Add borders
        alphabetGridPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ""));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainPanel.add(alphabetGridPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void updateAlphabetGridPanel() {
        alphabetGridPanel.removeAll();
        AlphabetModel charDecimalMapper = viewModel.getCharDecimalMapper();
        alphabetGridPanel.setLayout(new GridLayout(charDecimalMapper.getRows(), charDecimalMapper.getColumns(), 1, 1));

        // Add labels with hover effect
        for (char c : charDecimalMapper.getAlphabetTable()) {
            String labelText = (c == ' ') ? "Space = " + charDecimalMapper.getCharToDecimalMap().get(c)
                    : c + " = " + charDecimalMapper.getCharToDecimalMap().get(c);
            JLabel label = new JLabel(labelText);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 12));
            label.setOpaque(true);
            label.setBackground(Color.white);
            label.setBorder(BorderFactory.createLineBorder(Color.black, 1));
            label.setPreferredSize(new Dimension(100, 50));
            label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding inside label

            // Add mouse listener for hover effect
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    label.setBackground(Color.decode("#d0d0d0")); // Change color on hover
                    label.setFont(new Font("Arial", Font.BOLD, 13)); // Increase font size
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    label.setBackground(Color.white); // Reset color
                    label.setFont(new Font("Arial", Font.BOLD, 12)); // Reset font size
                }
            });

            alphabetGridPanel.add(label);
        }

        alphabetGridPanel.revalidate();
        alphabetGridPanel.repaint();
    }

    private void initializeButtonPanel() {
        JButton increaseButton = new JButton("+");
        increaseButton.addActionListener(e -> {
            viewModel.increaseRows();
            updateAlphabetGridPanel();
        });

        JButton decreaseButton = new JButton("-");
        decreaseButton.addActionListener(e -> {
            viewModel.decreaseRows();
            updateAlphabetGridPanel();
        });

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            viewModel.resetToDefault();
            updateAlphabetGridPanel();
        });

        buttonPanel.add(decreaseButton);
        buttonPanel.add(increaseButton);
        buttonPanel.add(resetButton);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}