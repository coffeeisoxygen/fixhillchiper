package com.coffeecode.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.coffeecode.viewmodel.HillCipherViewModel;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

public class InputUI extends JPanel {
    private HillCipherViewModel viewModel;
    private JSpinner blockSizeSpinner;
    private JPanel keyMatrixPanel;
    private JTextField[][] keyMatrixFields;
    private JTextField plainTextField;
    private JComboBox<String> operationComboBox;
    private JButton generateButton;
    private JToggleButton themeToggleButton;

    public InputUI(HillCipherViewModel viewModel) {
        this.viewModel = viewModel;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Tambah jarak antar elemen

        // Theme toggle button
        themeToggleButton = new JToggleButton("Switch to Dark Mode");
        themeToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (themeToggleButton.isSelected()) {
                    FlatDarkLaf.setup();
                    themeToggleButton.setText("Switch to Light Mode");
                } else {
                    FlatLightLaf.setup();
                    themeToggleButton.setText("Switch to Dark Mode");
                }
                SwingUtilities.updateComponentTreeUI(SwingUtilities.getWindowAncestor(InputUI.this));
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        add(themeToggleButton, gbc);

        // Block size input
        JLabel blockSizeLabel = new JLabel("Block Size:");
        blockSizeLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Ganti font supaya lebih modern
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(blockSizeLabel, gbc);

        blockSizeSpinner = new JSpinner(new SpinnerNumberModel(2, 2, 4, 1));
        blockSizeSpinner.setPreferredSize(new Dimension(50, 25)); // Gedein spinner
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(blockSizeSpinner, gbc);

        blockSizeSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                updateKeyMatrixFields();
            }
        });

        // Key matrix input panel
        keyMatrixPanel = new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(keyMatrixPanel, gbc);
        gbc.gridwidth = 1;

        // Plain text input
        JLabel plainTextLabel = new JLabel("Plain Text:");
        plainTextLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Ganti font
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        add(plainTextLabel, gbc);

        plainTextField = new JTextField(20);
        plainTextField.setFont(new Font("Arial", Font.PLAIN, 14)); // Ganti font
        plainTextField.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Border textbox
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(plainTextField, gbc);

        // Operation combobox
        JLabel operationLabel = new JLabel("Operation:");
        operationLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Ganti font
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        add(operationLabel, gbc);

        operationComboBox = new JComboBox<>(new String[] { "Encrypt", "Decrypt" });
        operationComboBox.setFont(new Font("Arial", Font.PLAIN, 14)); // Ganti font
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(operationComboBox, gbc);

        // Generate button
        generateButton = new JButton("Generate");
        generateButton.setFont(new Font("Arial", Font.BOLD, 14)); // Ganti font
        generateButton.setBackground(new Color(66, 133, 244)); // Warna button
        generateButton.setForeground(Color.WHITE); // Warna text button
        generateButton.setFocusPainted(false);
        generateButton.setPreferredSize(new Dimension(120, 40)); // Ukuran button lebih besar
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        add(generateButton, gbc);

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onGenerateButtonClicked();
            }
        });

        updateKeyMatrixFields(); // Initialize key matrix fields
    }

    private void updateKeyMatrixFields() {
        keyMatrixPanel.removeAll();
        int blockSize = (int) blockSizeSpinner.getValue();
        keyMatrixFields = new JTextField[blockSize][blockSize];
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        for (int i = 0; i < blockSize; i++) {
            for (int j = 0; j < blockSize; j++) {
                keyMatrixFields[i][j] = new JTextField(5);
                keyMatrixFields[i][j].setFont(new Font("Arial", Font.PLAIN, 14)); // Ganti font
                keyMatrixFields[i][j].setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Border textbox
                gbc.gridx = j;
                gbc.gridy = i;
                keyMatrixPanel.add(keyMatrixFields[i][j], gbc);
            }
        }
        keyMatrixPanel.revalidate();
        keyMatrixPanel.repaint();
    }

    private void onGenerateButtonClicked() {
        try {
            int blockSize = (int) blockSizeSpinner.getValue();
            viewModel.setBlockSize(blockSize);

            int[][] keyMatrix = new int[blockSize][blockSize];
            for (int i = 0; i < blockSize; i++) {
                for (int j = 0; j < blockSize; j++) {
                    keyMatrix[i][j] = Integer.parseInt(keyMatrixFields[i][j].getText());
                }
            }
            viewModel.setKeyMatrix(keyMatrix);

            String plainText = plainTextField.getText();
            viewModel.setPlainText(plainText);

            String operation = (String) operationComboBox.getSelectedItem();
            String result;
            if ("Encrypt".equals(operation)) {
                result = viewModel.encryptText();
            } else {
                result = viewModel.decryptText();
            }

            JOptionPane.showMessageDialog(this, "Result: " + result);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
