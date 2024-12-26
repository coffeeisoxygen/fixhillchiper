package com.coffeecode.view;

import com.coffeecode.viewmodel.HillCipherViewModel;
import com.coffeecode.viewmodel.ValidationViewModel;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class InputUI extends JPanel {
    private HillCipherViewModel viewModel;
    private ValidationViewModel validationViewModel;
    private JSpinner blockSizeSpinner;
    private JPanel keyMatrixPanel;
    private JTextField[][] keyMatrixFields;
    private JTextField plainTextField;
    private JComboBox<String> operationComboBox;
    private JButton generateButton;
    private JToggleButton themeToggleButton;
    private JCheckBox sizeCheckBox;
    private JCheckBox determinantCheckBox;
    private JCheckBox relativelyPrimeCheckBox;
    private JCheckBox invertibleCheckBox;

    public InputUI(HillCipherViewModel viewModel) {
        this.viewModel = viewModel;
        this.validationViewModel = new ValidationViewModel(2); // Default block size
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

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
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(blockSizeLabel, gbc);

        blockSizeSpinner = new JSpinner(new SpinnerNumberModel(2, 2, 4, 1));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(blockSizeSpinner, gbc);

        blockSizeSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int blockSize = (int) blockSizeSpinner.getValue();
                validationViewModel.setBlockSize(blockSize);
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

        // Key specification checkboxes
        sizeCheckBox = new JCheckBox("Jumlah kunci = blocksize");
        sizeCheckBox.setEnabled(false);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(sizeCheckBox, gbc);

        determinantCheckBox = new JCheckBox("det(K) tidak 0 dan = 1");
        determinantCheckBox.setEnabled(false);
        gbc.gridy = 4;
        add(determinantCheckBox, gbc);

        relativelyPrimeCheckBox = new JCheckBox("det(K) relatif prima dengan Mod 26");
        relativelyPrimeCheckBox.setEnabled(false);
        gbc.gridy = 5;
        add(relativelyPrimeCheckBox, gbc);

        invertibleCheckBox = new JCheckBox("K dapat di Inverse");
        invertibleCheckBox.setEnabled(false);
        gbc.gridy = 6;
        add(invertibleCheckBox, gbc);

        // Plain text input
        JLabel plainTextLabel = new JLabel("Plain Text:");
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(plainTextLabel, gbc);

        plainTextField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(plainTextField, gbc);

        // Operation combobox
        JLabel operationLabel = new JLabel("Operation:");
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.WEST;
        add(operationLabel, gbc);

        operationComboBox = new JComboBox<>(new String[]{"Encrypt", "Decrypt"});
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(operationComboBox, gbc);

        // Generate button
        generateButton = new JButton("Generate");
        gbc.gridx = 1;
        gbc.gridy = 9;
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

        // Add left curly brace
        JLabel leftBrace = new JLabel("{");
        leftBrace.setFont(new Font("Serif", Font.PLAIN, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = blockSize;
        gbc.anchor = GridBagConstraints.CENTER;
        keyMatrixPanel.add(leftBrace, gbc);

        // Add key matrix fields
        for (int i = 0; i < blockSize; i++) {
            for (int j = 0; j < blockSize; j++) {
                keyMatrixFields[i][j] = new JTextField(5);
                keyMatrixFields[i][j].getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        validateKeyMatrix();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        validateKeyMatrix();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        validateKeyMatrix();
                    }
                });
                gbc.gridx = j + 1;
                gbc.gridy = i;
                gbc.gridheight = 1;
                keyMatrixPanel.add(keyMatrixFields[i][j], gbc);
            }
        }

        // Add right curly brace
        JLabel rightBrace = new JLabel("}");
        rightBrace.setFont(new Font("Serif", Font.PLAIN, 24));
        gbc.gridx = blockSize + 1;
        gbc.gridy = 0;
        gbc.gridheight = blockSize;
        gbc.anchor = GridBagConstraints.CENTER;
        keyMatrixPanel.add(rightBrace, gbc);

        keyMatrixPanel.revalidate();
        keyMatrixPanel.repaint();
    }

    private void validateKeyMatrix() {
        try {
            int blockSize = (int) blockSizeSpinner.getValue();
            int[][] keyMatrix = new int[blockSize][blockSize];
            for (int i = 0; i < blockSize; i++) {
                for (int j = 0; j < blockSize; j++) {
                    keyMatrix[i][j] = Integer.parseInt(keyMatrixFields[i][j].getText());
                }
            }
            validationViewModel.setKeyMatrix(keyMatrix);

            // Update key specification checkboxes
            sizeCheckBox.setSelected(validationViewModel.isSizeValid());
            determinantCheckBox.setSelected(validationViewModel.isDeterminantNonZero());
            relativelyPrimeCheckBox.setSelected(validationViewModel.isDeterminantRelativelyPrime());
            invertibleCheckBox.setSelected(validationViewModel.isInvertible());
        } catch (NumberFormatException e) {
            // Handle invalid input
            sizeCheckBox.setSelected(false);
            determinantCheckBox.setSelected(false);
            relativelyPrimeCheckBox.setSelected(false);
            invertibleCheckBox.setSelected(false);
        }
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
            validationViewModel.setKeyMatrix(keyMatrix);

            // Update key specification checkboxes
            sizeCheckBox.setSelected(validationViewModel.isSizeValid());
            determinantCheckBox.setSelected(validationViewModel.isDeterminantNonZero());
            relativelyPrimeCheckBox.setSelected(validationViewModel.isDeterminantRelativelyPrime());
            invertibleCheckBox.setSelected(validationViewModel.isInvertible());

            if (validationViewModel.isSizeValid() && validationViewModel.isDeterminantNonZero() &&
                validationViewModel.isDeterminantRelativelyPrime() && validationViewModel.isInvertible()) {
                viewModel.setKeyMatrix(keyMatrix);
            } else {
                throw new IllegalArgumentException("Key matrix tidak valid");
            }

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
