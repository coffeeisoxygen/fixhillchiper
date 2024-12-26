package com.coffeecode.view;

import com.coffeecode.viewmodel.HillCipherViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class InputUI extends JPanel {
    private HillCipherViewModel viewModel;
    private JSpinner blockSizeSpinner;
    private JPanel keyMatrixPanel;
    private JTextField[][] keyMatrixFields;
    private JTextField plainTextField;
    private JComboBox<String> operationComboBox;
    private JButton generateButton;

    public InputUI(HillCipherViewModel viewModel) {
        this.viewModel = viewModel;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Block size input
        JLabel blockSizeLabel = new JLabel("Block Size:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(blockSizeLabel, gbc);

        blockSizeSpinner = new JSpinner(new SpinnerNumberModel(2, 2, 4, 1));
        gbc.gridx = 1;
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
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(keyMatrixPanel, gbc);
        gbc.gridwidth = 1;

        // Plain text input
        JLabel plainTextLabel = new JLabel("Plain Text:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(plainTextLabel, gbc);

        plainTextField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        add(plainTextField, gbc);
        gbc.gridwidth = 1;

        // Operation combobox
        JLabel operationLabel = new JLabel("Operation:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(operationLabel, gbc);

        operationComboBox = new JComboBox<>(new String[]{"Encrypt", "Decrypt"});
        gbc.gridx = 1;
        add(operationComboBox, gbc);

        // Generate button
        generateButton = new JButton("Generate");
        gbc.gridx = 1;
        gbc.gridy = 4;
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
