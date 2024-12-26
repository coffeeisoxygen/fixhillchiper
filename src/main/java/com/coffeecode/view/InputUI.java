package com.coffeecode.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
    private JCheckBox keyValidCheckBox;
    private JButton validateKeyButton;

    public InputUI(HillCipherViewModel viewModel) {
        this.viewModel = viewModel;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400, 400)); // Set panel size

        // Header panel with theme toggle button
        JPanel headerPanel = new JPanel(new GridLayout(1, 2));
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
        headerPanel.add(themeToggleButton);

        // Add header panel to the top
        add(headerPanel, BorderLayout.NORTH);

        // Main panel for the content
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding

        // Block size spinner
        JLabel blockSizeLabel = new JLabel("Block Size:");
        blockSizeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(blockSizeLabel, gbc);

        blockSizeSpinner = new JSpinner(new SpinnerNumberModel(2, 2, 4, 1));
        blockSizeSpinner.setPreferredSize(new Dimension(50, 25));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(blockSizeSpinner, gbc);

        // Key matrix panel
        keyMatrixPanel = new JPanel(new GridLayout(1, 1));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(keyMatrixPanel, gbc);
        gbc.gridwidth = 1;

        blockSizeSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                updateKeyMatrixFields();
            }
        });

        // Validate key button and checkbox
        validateKeyButton = new JButton("Validate Key");
        validateKeyButton.setPreferredSize(new Dimension(120, 40));
        validateKeyButton.setFont(new Font("Arial", Font.BOLD, 14));
        validateKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onValidateKey();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(validateKeyButton, gbc);

        keyValidCheckBox = new JCheckBox("Valid Key");
        keyValidCheckBox.setEnabled(false); // Disabled to prevent modification
        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(keyValidCheckBox, gbc);

        // Plain text input
        JLabel plainTextLabel = new JLabel("Plain Text:");
        plainTextLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(plainTextLabel, gbc);

        plainTextField = new JTextField(20);
        plainTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        plainTextField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(plainTextField, gbc);

        // Operation combobox
        JLabel operationLabel = new JLabel("Operation:");
        operationLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(operationLabel, gbc);

        operationComboBox = new JComboBox<>(new String[] { "Encrypt", "Decrypt" });
        operationComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(operationComboBox, gbc);

        // Generate button
        generateButton = new JButton("Generate");
        generateButton.setFont(new Font("Arial", Font.BOLD, 14));
        generateButton.setBackground(new Color(66, 133, 244));
        generateButton.setForeground(Color.WHITE);
        generateButton.setFocusPainted(false);
        generateButton.setPreferredSize(new Dimension(120, 40));
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(generateButton, gbc);

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onGenerateButtonClicked();
            }
        });

        add(mainPanel, BorderLayout.CENTER);
        updateKeyMatrixFields(); // Initialize key matrix fields
    }

    private void updateKeyMatrixFields() {
        keyMatrixPanel.removeAll();
        int blockSize = (int) blockSizeSpinner.getValue();
        keyMatrixFields = new JTextField[blockSize][blockSize];
        GridLayout gridLayout = new GridLayout(blockSize, blockSize);
        keyMatrixPanel.setLayout(gridLayout);
        for (int i = 0; i < blockSize; i++) {
            for (int j = 0; j < blockSize; j++) {
                keyMatrixFields[i][j] = new JTextField(5);
                keyMatrixFields[i][j].setFont(new Font("Arial", Font.PLAIN, 14));
                keyMatrixFields[i][j].setBorder(BorderFactory.createLineBorder(Color.GRAY));
                keyMatrixPanel.add(keyMatrixFields[i][j]);
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

    private void onValidateKey() {
        // Logic to validate key matrix
        keyValidCheckBox.setSelected(true); // Assume valid for this example
    }
}
